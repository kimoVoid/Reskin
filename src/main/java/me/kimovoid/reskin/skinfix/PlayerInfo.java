package me.kimovoid.reskin.skinfix;

import com.mojang.authlib.GameProfile;

import java.util.UUID;

public class PlayerInfo {

    private final UUID id;
    private GameProfile cachedProfile;
    private String modelType;

    public PlayerInfo(UUID id, String modelType) {
        this.id = id;
        this.modelType = modelType;
    }

    public UUID getId() {
        return this.id;
    }

    public String getModelType() {
        return this.modelType;
    }

    public void setModelType(String modelType) {
        this.modelType = modelType;
    }

    public GameProfile getCachedProfile() {
        return this.cachedProfile;
    }

    public void setProfile(GameProfile profile) {
        this.cachedProfile = profile;
    }
}