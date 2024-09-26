package me.kimovoid.reskin.config;

import cpw.mods.fml.client.IModGuiFactory;
import cpw.mods.fml.client.config.GuiConfig;
import cpw.mods.fml.client.config.IConfigElement;
import me.kimovoid.reskin.Reskin;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class GuiConfigFactory implements IModGuiFactory {

    @Override
    public void initialize(final Minecraft instance) {
    }

    @Override
    public Class<? extends GuiScreen> mainConfigGuiClass() {
        return ReskinGuiConfig.class;
    }

    @Override
    public Set<RuntimeOptionCategoryElement> runtimeGuiCategories() {
        return null;
    }

    @Override
    public RuntimeOptionGuiHandler getHandlerFor(final RuntimeOptionCategoryElement element) {
        return null;
    }

    public static class ReskinGuiConfig extends GuiConfig {
        public ReskinGuiConfig(final GuiScreen parent) {
            super(parent, getConfigElements(Reskin.CONFIG.config), Reskin.MODID, false, false, "Reskin");
        }

        private static List<IConfigElement> getConfigElements(final Configuration configuration) {
            List<IConfigElement> elements = new ArrayList<>();

            elements.addAll((new ConfigElement(configuration.getCategory("general"))).getChildElements());
            return elements;
        }
    }
}
