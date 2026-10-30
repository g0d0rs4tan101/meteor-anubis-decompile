/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.net.InetAddresses
 *  io.netty.bootstrap.Bootstrap
 *  io.netty.channel.Channel
 *  io.netty.channel.ChannelFuture
 *  io.netty.channel.ChannelHandler
 *  io.netty.channel.ChannelHandlerContext
 *  io.netty.channel.ChannelInboundHandlerAdapter
 *  io.netty.channel.ChannelInitializer
 *  io.netty.channel.ChannelOption
 *  io.netty.channel.EventLoopGroup
 *  io.netty.channel.MultiThreadIoEventLoopGroup
 *  io.netty.channel.nio.NioIoHandler
 *  io.netty.channel.socket.nio.NioSocketChannel
 *  io.netty.handler.proxy.ProxyHandler
 *  io.netty.util.concurrent.DefaultThreadFactory
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 */
package net.anubis.client.proxy;

import com.google.common.net.InetAddresses;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.MultiThreadIoEventLoopGroup;
import io.netty.channel.nio.NioIoHandler;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.proxy.ProxyHandler;
import io.netty.util.concurrent.DefaultThreadFactory;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Deque;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicBoolean;
import net.anubis.client.proxy.Proxies;
import net.anubis.client.proxy.Proxy;
import net.anubis.client.proxy.ProxyRouting;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(value=EnvType.CLIENT)
public final class ProxyChecker {
    private static final InetSocketAddress TARGET = new InetSocketAddress(InetAddresses.forString((String)"1.1.1.1"), 443);
    private static final int TIMEOUT_MS = 8000;
    private static final int IN_FLIGHT = 32;
    private static final ProxyChecker INSTANCE = new ProxyChecker();
    private final Set<Proxy> checking = ConcurrentHashMap.newKeySet();
    private final Deque<Proxy> queue = new ArrayDeque<Proxy>();
    private int running;
    private EventLoopGroup group;

    private ProxyChecker() {
    }

    public static ProxyChecker get() {
        return INSTANCE;
    }

    public synchronized void check(Collection<Proxy> proxies) {
        for (Proxy proxy : proxies) {
            if (!this.checking.add(proxy)) continue;
            this.queue.add(proxy);
        }
        this.pump();
    }

    public boolean isChecking(Proxy proxy) {
        return this.checking.contains(proxy);
    }

    public int pending() {
        return this.checking.size();
    }

    private synchronized void pump() {
        if (this.group == null) {
            this.group = new MultiThreadIoEventLoopGroup(2, (ThreadFactory)new DefaultThreadFactory("Anubis proxy check", true), NioIoHandler.newFactory());
        }
        while (this.running < 32 && !this.queue.isEmpty()) {
            Proxy proxy = this.queue.poll();
            ++this.running;
            this.group.execute(() -> this.start(proxy));
        }
    }

    private void start(Proxy proxy) {
        ProxyHandler handler;
        AtomicBoolean done = new AtomicBoolean();
        if (!proxy.isValid()) {
            this.finish(proxy, -1, done);
            return;
        }
        try {
            handler = ProxyRouting.handler(proxy);
        }
        catch (RuntimeException unresolvable) {
            this.finish(proxy, -1, done);
            return;
        }
        handler.setConnectTimeoutMillis(8000L);
        long started = System.nanoTime();
        ChannelFuture connect = ((Bootstrap)((Bootstrap)((Bootstrap)((Bootstrap)new Bootstrap().group(this.group)).channel(NioSocketChannel.class)).option(ChannelOption.CONNECT_TIMEOUT_MILLIS, (Object)8000)).handler((ChannelHandler)new ChannelInitializer<Channel>(this){

            protected void initChannel(Channel channel) {
                channel.pipeline().addLast(new ChannelHandler[]{handler, new ChannelInboundHandlerAdapter(this){

                    public void exceptionCaught(ChannelHandlerContext context, Throwable cause) {
                        context.close();
                    }
                }});
            }
        })).connect((SocketAddress)TARGET);
        connect.addListener(future -> {
            if (!future.isSuccess()) {
                this.finish(proxy, -1, done);
            }
        });
        handler.connectFuture().addListener(future -> {
            int ms = (int)((System.nanoTime() - started) / 1000000L);
            this.finish(proxy, future.isSuccess() ? ms : -1, done);
            connect.channel().close();
        });
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void finish(Proxy proxy, int ping, AtomicBoolean done) {
        boolean last;
        if (!done.compareAndSet(false, true)) {
            return;
        }
        proxy.ping = ping;
        this.checking.remove(proxy);
        ProxyChecker proxyChecker = this;
        synchronized (proxyChecker) {
            --this.running;
            last = this.running == 0 && this.queue.isEmpty();
            this.pump();
        }
        if (last) {
            Proxies.get().checked();
        }
    }
}

