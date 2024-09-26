package me.kimovoid.reskin.mixin.access;

import com.mojang.authlib.GameProfile;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.UUID;

@Mixin(value = GameProfile.class, remap = false)
public interface GameProfileAccessor {

    @Accessor("id")
    void setUuid(UUID id);
}