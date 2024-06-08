package net.luhcarti.totemofdying.network;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.luhcarti.totemofdying.TotemOfDying;
import net.minecraft.util.Identifier;

public class ModNetwork {
    public static final Identifier ACTIVATE_TOTEM_ID = new Identifier(TotemOfDying.MOD_ID, "activate_totem");

    public static void registerC2SPackets() {
        ServerPlayNetworking.registerGlobalReceiver(ACTIVATE_TOTEM_ID, TotemActivationPacket::receive);
    }

    public static void registerS2CPackets() {

    }
}
