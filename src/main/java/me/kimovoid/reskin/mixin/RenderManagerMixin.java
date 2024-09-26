package me.kimovoid.reskin.mixin;

import me.kimovoid.reskin.skinfix.SkinService;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(RenderManager.class)
public abstract class RenderManagerMixin {

    @Inject(
            method = "getEntityRenderObject",
            at = @At("HEAD"),
            cancellable = true
    )
    private void getPlayerModel(Entity entity, CallbackInfoReturnable<Render> cir) {
        if (entity instanceof AbstractClientPlayer) {
            boolean slim = SkinService.INSTANCE.isSlim(((AbstractClientPlayer) entity).getGameProfile());
            cir.setReturnValue(slim ? SkinService.ALEX_MODEL : SkinService.STEVE_MODEL);
        }
    }
}