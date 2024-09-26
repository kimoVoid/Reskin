package me.kimovoid.reskin.skinfix;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import me.kimovoid.reskin.Reskin;
import me.kimovoid.reskin.mixin.access.GameProfileAccessor;
import me.kimovoid.reskin.model.ReskinRenderPlayer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.locks.ReentrantLock;

public class SkinService {

    public static final SkinService INSTANCE = new SkinService();

    public static final ResourceLocation STEVE_TEXTURE = new ResourceLocation(Reskin.MODID, "textures/entity/steve.png");
    public static final ResourceLocation ALEX_TEXTURE = new ResourceLocation(Reskin.MODID, "textures/entity/alex.png");
    public static final ReskinRenderPlayer STEVE_MODEL = new ReskinRenderPlayer(RenderManager.instance, false);
    public static final ReskinRenderPlayer ALEX_MODEL = new ReskinRenderPlayer(RenderManager.instance, true);

    private final Map<String, PlayerInfo> players = new HashMap<>();
    private final ConcurrentMap<String, ReentrantLock> locks = new ConcurrentHashMap<>();

    public void updatePlayer(AbstractClientPlayer player) {
        if (this.players.containsKey(player.getCommandSenderName())) {
            PlayerInfo info = this.players.get(player.getCommandSenderName());
            ((GameProfileAccessor)player.getGameProfile()).setUuid(info.getId());

            Minecraft.getMinecraft().func_152347_ac().fillProfileProperties(player.getGameProfile(), true);
            Minecraft.getMinecraft().func_152342_ad().func_152790_a(player.getGameProfile(), player, true);
        }
    }

    public void init(AbstractClientPlayer player) {
        (new Thread(() -> {
            if (!this.players.containsKey(player.getCommandSenderName())) {
                this.initUuid(player.getCommandSenderName());
            }
            this.updatePlayer(player);
        })).start();
    }

    public void initUuid(String name) {
        if (this.players.containsKey(name)) return;

        ReentrantLock lock;
        if (locks.containsKey(name)) {
            lock = locks.get(name);
        } else {
            locks.put(name, lock = new ReentrantLock());
        }

        lock.lock();

        try {
            UUID id = new UUIDProvider().getProfile(name).get();
            this.players.put(name, new PlayerInfo(id, null));
        } catch (Exception e) {
            Reskin.LOGGER.warn("Lookup for profile {} failed!", name);
        } finally {
            lock.unlock();
        }
    }

    public void initModelType(String player, MinecraftProfileTexture texture) {
        if (!this.players.containsKey(player)) {
            return;
        }
        String modelType = texture.getMetadata("model");
        this.players.get(player).setModelType(modelType == null ? "normal" : modelType);
    }

    public boolean isSlim(GameProfile profile) {
        if (this.players.containsKey(profile.getName()) && this.players.get(profile.getName()).getModelType() != null) {
            return this.players.get(profile.getName()).getModelType().equalsIgnoreCase("slim");
        }

        int hash = profile.hashCode();
        return hash % 2 == 1;
    }
}