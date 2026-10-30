/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonParser
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 */
package net.anubis.client.account;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Map;
import java.util.stream.Collectors;
import net.anubis.client.account.AccountException;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(value=EnvType.CLIENT)
final class AuthHttp {
    private static final HttpClient CLIENT = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(8L)).followRedirects(HttpClient.Redirect.NEVER).build();
    private static final Duration TIMEOUT = Duration.ofSeconds(30L);
    private static final String USER_AGENT = "AnubisClient";

    private AuthHttp() {
    }

    static Reply postForm(String url, Map<String, String> form) throws InterruptedException {
        String body = form.entrySet().stream().map(e -> URLEncoder.encode((String)e.getKey(), StandardCharsets.UTF_8) + "=" + URLEncoder.encode((String)e.getValue(), StandardCharsets.UTF_8)).collect(Collectors.joining("&"));
        return AuthHttp.send(url, HttpRequest.newBuilder(URI.create(url)).header("Content-Type", "application/x-www-form-urlencoded").POST(HttpRequest.BodyPublishers.ofString(body)));
    }

    static Reply postJson(String url, String body, Map<String, String> headers) throws InterruptedException {
        HttpRequest.Builder request = HttpRequest.newBuilder(URI.create(url)).header("Content-Type", "application/json").POST(HttpRequest.BodyPublishers.ofString(body, StandardCharsets.UTF_8));
        headers.forEach(request::header);
        return AuthHttp.send(url, request);
    }

    static Reply get(String url, String bearerToken) throws InterruptedException {
        return AuthHttp.send(url, HttpRequest.newBuilder(URI.create(url)).header("Authorization", "Bearer " + bearerToken).GET());
    }

    static String text(JsonObject object, String key) {
        JsonElement value = object == null ? null : object.get(key);
        return value != null && value.isJsonPrimitive() ? value.getAsString() : null;
    }

    static JsonObject object(JsonObject object, String key) {
        JsonElement value = object == null ? null : object.get(key);
        return value != null && value.isJsonObject() ? value.getAsJsonObject() : null;
    }

    private static Reply send(String url, HttpRequest.Builder request) throws InterruptedException {
        try {
            HttpResponse<String> response = CLIENT.send(request.timeout(TIMEOUT).header("Accept", "application/json").header("User-Agent", USER_AGENT).build(), HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
            return new Reply(response.statusCode(), AuthHttp.parse(response.body()), response.headers());
        }
        catch (IOException e) {
            throw new AccountException("Couldn't reach " + URI.create(url).getHost() + " - check your connection", e);
        }
    }

    private static JsonObject parse(String body) {
        try {
            JsonElement root = JsonParser.parseString((String)body);
            return root.isJsonObject() ? root.getAsJsonObject() : new JsonObject();
        }
        catch (RuntimeException notJson) {
            return new JsonObject();
        }
    }

    @Environment(value=EnvType.CLIENT)
    record Reply(int status, JsonObject json, HttpHeaders headers) {
        boolean ok() {
            return this.status >= 200 && this.status < 300;
        }

        String text(String key) {
            return AuthHttp.text(this.json, key);
        }
    }
}

