package me.kimovoid.reskin.mixin;

import net.minecraft.client.renderer.IImageBuffer;
import net.minecraft.client.renderer.ImageBufferDownload;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

@Mixin(ImageBufferDownload.class)
public abstract class ImageBufferDownloadMixin implements IImageBuffer {

    @Shadow private int imageWidth;
    @Shadow private int imageHeight;
    @Shadow private int[] imageData;

    @Shadow protected abstract void setAreaOpaque(int p_78433_1_, int p_78433_2_, int p_78433_3_, int p_78433_4_);
    @Shadow protected abstract void setAreaTransparent(int p_78434_1_, int p_78434_2_, int p_78434_3_, int p_78434_4_);

    /**
     * @author kimoVoid
     * @reason handle modern skins
     */
    @Overwrite
    public BufferedImage parseUserSkin(BufferedImage image) {
        if (image == null) {
            return null;
        }
        this.imageWidth = 64;
        this.imageHeight = 64;
        BufferedImage bufferedImage = new BufferedImage(this.imageWidth, this.imageHeight, 2);
        Graphics graphics = bufferedImage.getGraphics();
        graphics.drawImage(image, 0, 0, null);
        if (image.getHeight() == 32) {
            graphics.drawImage(bufferedImage, 24, 48, 20, 52, 4, 16, 8, 20, null);
            graphics.drawImage(bufferedImage, 28, 48, 24, 52, 8, 16, 12, 20, null);
            graphics.drawImage(bufferedImage, 20, 52, 16, 64, 8, 20, 12, 32, null);
            graphics.drawImage(bufferedImage, 24, 52, 20, 64, 4, 20, 8, 32, null);
            graphics.drawImage(bufferedImage, 28, 52, 24, 64, 0, 20, 4, 32, null);
            graphics.drawImage(bufferedImage, 32, 52, 28, 64, 12, 20, 16, 32, null);
            graphics.drawImage(bufferedImage, 40, 48, 36, 52, 44, 16, 48, 20, null);
            graphics.drawImage(bufferedImage, 44, 48, 40, 52, 48, 16, 52, 20, null);
            graphics.drawImage(bufferedImage, 36, 52, 32, 64, 48, 20, 52, 32, null);
            graphics.drawImage(bufferedImage, 40, 52, 36, 64, 44, 20, 48, 32, null);
            graphics.drawImage(bufferedImage, 44, 52, 40, 64, 40, 20, 44, 32, null);
            graphics.drawImage(bufferedImage, 48, 52, 44, 64, 52, 20, 56, 32, null);
        }
        graphics.dispose();
        this.imageData = ((DataBufferInt)bufferedImage.getRaster().getDataBuffer()).getData();
        this.setAreaOpaque(0, 0, 32, 16);
        this.setAreaTransparent(32, 0, 64, 32);
        this.setAreaOpaque(0, 16, 64, 32);
        this.setAreaTransparent(0, 32, 16, 48);
        this.setAreaTransparent(16, 32, 40, 48);
        this.setAreaTransparent(40, 32, 56, 48);
        this.setAreaTransparent(0, 48, 16, 64);
        this.setAreaOpaque(16, 48, 48, 64);
        this.setAreaTransparent(48, 48, 64, 64);
        return bufferedImage;
    }
}