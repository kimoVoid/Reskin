package me.kimovoid.reskin.mixin;

import com.mojang.authlib.GameProfile;
import me.kimovoid.reskin.skinfix.SkinService;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.resources.SkinManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractClientPlayer.class)
abstract class AbstractClientPlayerMixin extends EntityPlayer {

    @Shadow private ResourceLocation locationSkin;

    public AbstractClientPlayerMixin(World p_i45324_1_, GameProfile p_i45324_2_) {
        super(p_i45324_1_, p_i45324_2_);
    }

    @Redirect(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/resources/SkinManager;func_152790_a(Lcom/mojang/authlib/GameProfile;Lnet/minecraft/client/resources/SkinManager$SkinAvailableCallback;Z)V"))
    private void disableVanillaSkinLoading(SkinManager instance, GameProfile profile, SkinManager.SkinAvailableCallback skinAvailableCallback, boolean p_152790_3_) {
        SkinService.INSTANCE.init((AbstractClientPlayer) (Object) this);
    }

    @Inject(method = "getLocationSkin()Lnet/minecraft/util/ResourceLocation;", at = @At("HEAD"), cancellable = true)
    private void setSteveTexture(CallbackInfoReturnable<ResourceLocation> cir) {
        cir.setReturnValue(this.locationSkin == null ? (SkinService.INSTANCE.isSlim(this.getGameProfile()) ? SkinService.ALEX_TEXTURE : SkinService.STEVE_TEXTURE) : this.locationSkin);
    }
}