package com.carpet_shadow;

import carpet.CarpetExtension;
import carpet.CarpetServer;
import com.carpet_shadow.interfaces.InventoryItem;
import com.carpet_shadow.interfaces.ShadowItem;
import com.carpet_shadow.newAPI.ShadowNBTData;
import com.carpet_shadow.utility.RandomString;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Pair;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CarpetShadow implements CarpetExtension, ModInitializer {
//    public static final Cache<String, Pair<ItemStack, List<Pair<Inventory, Integer>>>> shadowMap = CacheBuilder.newBuilder().weakValues().build();
    public static final Map<String, Pair<ItemStack, List<Pair<Inventory, Integer>>>> shadowMap = new HashMap<>();
    public static final Logger LOGGER = LogManager.getLogger("carpet-shadow");
    public static RandomString shadow_id_generator = new RandomString(CarpetShadowSettings.shadowItemIdSize);



    @Override
    public void onGameStarted() {
        CarpetShadow.LOGGER.info("Carpet Shadow Loaded!");
        CarpetServer.settingsManager.parseSettingsClass(CarpetShadowSettings.class);
        shadow_id_generator = new RandomString(CarpetShadowSettings.shadowItemIdSize);
    }

    @Override
    public void onInitialize() {
        CarpetServer.manageExtension(new CarpetShadow());
//        Identifier.of("carpet-shadow", "shadow");
        new ShadowNBTData();
        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
            Globals.updateInventory(handler.player.getInventory());
        });;
        ServerPlayConnectionEvents.DISCONNECT.register((handler, server) -> {
            Globals.removeInventory(handler.player.getInventory());
        });
        CarpetShadow.LOGGER.info("Carpet Shadow Loading!");
    }

    @Override
    public void onServerLoaded(MinecraftServer server) {
//        shadowMap.invalidateAll();
    }

    @Override
    public void onTick(MinecraftServer server) {
//        if (server.getTicks() % 1000 == 0)
//            CarpetShadow.shadowMap.cleanUp();
    }

    @Override
    public Map<String, String> canHasTranslations(String lang) {
        InputStream langFile = getClass().getClassLoader().getResourceAsStream("assets/carpet-shadow/lang/%s.json".formatted(lang));
        if (langFile == null) {
            return Collections.emptyMap();
        }
        String jsonData;
        try {
            jsonData = IOUtils.toString(langFile, StandardCharsets.UTF_8);
        } catch (IOException e) {
            return Collections.emptyMap();
        }
        Gson gson = new GsonBuilder().setLenient().create();
        return gson.fromJson(jsonData, new TypeToken<Map<String, String>>() {}.getType());
    }
}
