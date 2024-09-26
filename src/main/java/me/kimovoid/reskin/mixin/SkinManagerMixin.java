package me.kimovoid.reskin.mixin;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import me.kimovoid.reskin.skinfix.SkinService;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.resources.SkinManager;
import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SkinManager.class)
public class SkinManagerMixin {

    @Unique private GameProfile reskin_currentPlayer; // this sucks but whatever

    @ModifyArg(
            method = "func_152789_a",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/renderer/ThreadDownloadImageData;<init>(Ljava/io/File;Ljava/lang/String;Lnet/minecraft/util/ResourceLocation;Lnet/minecraft/client/renderer/IImageBuffer;)V"
            ),
            index = 2
    )
    private ResourceLocation setDefaultSkin(ResourceLocation p_i1049_3_) {
        if (this.reskin_currentPlayer != null) {
            boolean isSlim = SkinService.INSTANCE.isSlim(this.reskin_currentPlayer);
            return isSlim ? SkinService.ALEX_TEXTURE : SkinService.STEVE_TEXTURE;
        }
        return SkinService.STEVE_TEXTURE;
    }

    @Inject(method = "func_152789_a", at = @At("HEAD"))
    private void loadModelType(MinecraftProfileTexture texture, MinecraftProfileTexture.Type type, SkinManager.SkinAvailableCallback callback, CallbackInfoReturnable<ResourceLocation> cir) {
        if (type.equals(MinecraftProfileTexture.Type.SKIN) && callback instanceof AbstractClientPlayer) {
            SkinService.INSTANCE.initModelType(((AbstractClientPlayer) callback).getCommandSenderName(), texture);
            this.reskin_currentPlayer = ((AbstractClientPlayer) callback).getGameProfile();
        }
    }
}