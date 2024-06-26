package net.luhcarti.totemofdying.network;

import net.luhcarti.totemofdying.TotemOfDying;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class ModNetwork {
    private static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(TotemOfDying.MODID, "main"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );

    public static void init() {
        INSTANCE.registerMessage(0, TotemActivationPacket.class, TotemActivationPacket::encode, TotemActivationPacket::decode, TotemActivationPacket::handle);
    }
}

//Networking, not fun
