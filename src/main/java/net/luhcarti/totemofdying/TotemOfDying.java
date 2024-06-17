package net.luhcarti.totemofdying;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.luhcarti.totemofdying.event.OnDeathHandler;
import net.luhcarti.totemofdying.item.ItemInit;
import net.luhcarti.totemofdying.network.ModNetwork;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TotemOfDying implements ModInitializer {
    // This logger is used to write text to the console and the log file.
    // It is considered best practice to use your mod id as the logger's name.
    // That way, it's clear which mod wrote info, warnings, and errors.
    public static final String MOD_ID = "totemofdying";
    public static final Logger LOGGER = LoggerFactory.getLogger("totemofdying");

    @Override
    public void onInitialize() {
        // This code runs as soon as Minecraft is in a mod-load-ready state.
        // However, some things (like resources) may still be uninitialized.
        // Proceed with mild caution.
        ItemInit.registerModItems();
        ServerLivingEntityEvents.ALLOW_DEATH.register(new OnDeathHandler());
        ModNetwork.registerC2SPackets();
    }
}