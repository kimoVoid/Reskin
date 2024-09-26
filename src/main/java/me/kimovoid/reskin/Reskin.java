package me.kimovoid.reskin;

import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import me.kimovoid.reskin.config.Config;
import me.kimovoid.reskin.listener.KeyBindingEvents;
import me.kimovoid.reskin.skinfix.SkinService;
import net.minecraft.client.Minecraft;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(modid = Reskin.MODID, version = Reskin.VERSION, guiFactory = "me.kimovoid.reskin.config.GuiConfigFactory")
public class Reskin {

    public static Logger LOGGER = LogManager.getLogger();
    public static final String MODID = "reskin";
    public static final String VERSION = "@VERSION@";
    public static Config CONFIG;

    @EventHandler
    public void init(FMLPreInitializationEvent event) {
        CONFIG = new Config(event.getSuggestedConfigurationFile());
        FMLCommonHandler.instance().bus().register(this);
        FMLCommonHandler.instance().bus().register(new KeyBindingEvents());
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        SkinService.INSTANCE.initUuid(Minecraft.getMinecraft().getSession().getUsername());
    }

    @SubscribeEvent
    public void onConfigChangedEvent(ConfigChangedEvent.OnConfigChangedEvent event) {
        if (MODID.equals(event.modID)) {
            CONFIG.sync(false);
        }
    }
}