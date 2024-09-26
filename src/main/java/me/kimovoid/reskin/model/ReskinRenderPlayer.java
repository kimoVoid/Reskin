package me.kimovoid.reskin.model;

import me.kimovoid.reskin.Reskin;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.player.EntityPlayer;
import org.lwjgl.opengl.GL11;

public class ReskinRenderPlayer extends RenderPlayer {

    public ReskinRenderPlayer(RenderManager manager, boolean slim) {
        super();
        setRenderManager(manager);
        mainModel = new ReskinPlayerModel(0.0F, slim);
        modelBipedMain = (ModelBiped) mainModel;
    }

    @Override
    public void doRender(AbstractClientPlayer p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_) {
        if (!Reskin.CONFIG.transparency) {
            super.doRender(p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
            return;
        }

        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        super.doRender(p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
        GL11.glDisable(GL11.GL_BLEND);
    }

    @Override
    public void renderFirstPersonArm(EntityPlayer p_82441_1_) {
        if (!Reskin.CONFIG.transparency) {
            super.renderFirstPersonArm(p_82441_1_);
            return;
        }

        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        super.renderFirstPersonArm(p_82441_1_);
        GL11.glDisable(GL11.GL_BLEND);
    }
}