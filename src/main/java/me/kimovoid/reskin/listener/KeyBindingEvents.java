package me.kimovoid.reskin.listener;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent;
import me.kimovoid.reskin.skinfix.SkinService;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Keyboard;

public class KeyBindingEvents {

    @SubscribeEvent
    public void onKeyInputEvent(InputEvent.KeyInputEvent ev) {
        this.handleKeyBindings();
    }

    public void handleKeyBindings() {
        Minecraft mc = Minecraft.getMinecraft();
        int key = Keyboard.getEventKey();

        if (mc.thePlayer == null
                || mc.theWorld == null
                || mc.currentScreen != null
                || Keyboard.getEventKeyState()) {
            return;
        }

        if (key == Keyboard.KEY_R && Keyboard.isKeyDown(Keyboard.KEY_F3)) {
            SkinService.INSTANCE.resetPlayers();
            Minecraft.getMinecraft().ingameGUI.func_110326_a("Resetting cached player profiles...", false);
        }
    }
}