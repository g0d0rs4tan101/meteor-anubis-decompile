/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.netty.channel.Channel
 *  io.netty.channel.ChannelHandler
 *  io.netty.channel.ChannelInitializer
 *  io.netty.handler.proxy.ProxyHandler
 *  io.netty.handler.proxy.Socks4ProxyHandler
 *  io.netty.handler.proxy.Socks5ProxyHandler
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 */
package net.anubis.client.proxy;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.handler.proxy.ProxyHandler;
import io.netty.handler.proxy.Socks4ProxyHandler;
import io.netty.handler.proxy.Socks5ProxyHandler;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import net.anubis.client.proxy.Proxies;
import net.anubis.client.proxy.Proxy;
import net.anubis.client.proxy.ProxyType;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(value=EnvType.CLIENT)
public final class ProxyRouting {
    private static final String HANDLER_NAME = "anubis_proxy";

    private ProxyRouting() {
    }

    public static ChannelHandler route(InetSocketAddress target, final ChannelHandler initializer) {
        if (target == null || ProxyRouting.isLocal(target)) {
            return initializer;
        }
        Proxy proxy = Proxies.get().enabledCopy();
        if (proxy == null || !proxy.isValid()) {
            return initializer;
        }
        final ProxyHandler handler = ProxyRouting.handler(proxy);
        return new ChannelInitializer<Channel>(){

            protected void initChannel(Channel channel) {
                channel.pipeline().addFirst(ProxyRouting.HANDLER_NAME, (ChannelHandler)handler);
                channel.pipeline().addLast(new ChannelHandler[]{initializer});
            }
        };
    }

    static ProxyHandler handler(Proxy proxy) {
        InetSocketAddress server = new InetSocketAddress(proxy.address.trim(), proxy.port);
        String username = proxy.username == null || proxy.username.isEmpty() ? null : proxy.username;
        String password = proxy.password == null || proxy.password.isEmpty() ? null : proxy.password;
        return switch (proxy.type) {
            default -> throw new MatchException(null, null);
            case ProxyType.SOCKS4 -> new Socks4ProxyHandler((SocketAddress)server, username);
            case ProxyType.SOCKS5 -> new Socks5ProxyHandler((SocketAddress)server, username, password);
        };
    }

    private static boolean isLocal(InetSocketAddress target) {
        InetAddress ip = target.getAddress();
        if (ip == null) {
            return "localhost".equalsIgnoreCase(target.getHostString());
        }
        if (ip.isLoopbackAddress() || ip.isAnyLocalAddress() || ip.isLinkLocalAddress() || ip.isSiteLocalAddress()) {
            return true;
        }
        byte[] raw = ip.getAddress();
        return raw.length == 16 && (raw[0] & 0xFE) == 252;
    }
}

