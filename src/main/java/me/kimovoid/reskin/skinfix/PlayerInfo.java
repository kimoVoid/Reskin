package me.kimovoid.reskin.skinfix;

import java.util.UUID;

public class PlayerInfo {

    private final UUID id;
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
}