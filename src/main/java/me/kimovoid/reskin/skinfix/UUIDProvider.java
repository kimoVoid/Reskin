package me.kimovoid.reskin.skinfix;

import com.github.steveice10.mc.auth.exception.request.RequestException;
import com.github.steveice10.mc.auth.util.HTTP;
import com.mojang.util.UUIDTypeAdapter;

import java.net.Proxy;
import java.net.URI;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

public class UUIDProvider {

    public Future<UUID> getProfile(String username) {
        try {
            UUIDResponse uuidresponse = HTTP.makeRequest(Proxy.NO_PROXY, URI.create("https://api.minetools.eu/uuid/" + username), null, UUIDResponse.class);
            if (uuidresponse.id == null) { // failsafe, use mojang api
                uuidresponse = HTTP.makeRequest(Proxy.NO_PROXY, URI.create("https://api.mojang.com/users/profiles/minecraft/" + username), null, UUIDResponse.class);
            }

            return CompletableFuture.completedFuture(UUIDTypeAdapter.fromString(uuidresponse.id));
        } catch (RequestException e) {
            CompletableFuture<UUID> future = new CompletableFuture<>();
            future.completeExceptionally(e);
            return future;
        }
    }

    private static class UUIDResponse {
        private String id;
        private String name;
    }
}