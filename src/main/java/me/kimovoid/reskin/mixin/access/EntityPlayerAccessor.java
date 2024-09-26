package me.kimovoid.reskin.mixin.access;

import com.mojang.authlib.GameProfile;
import net.minecraft.entity.player.EntityPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(EntityPlayer.class)
public interface EntityPlayerAccessor {

    @Accessor("field_146106_i")
    void setProfile(GameProfile profile);
}