package net.luhcarti.totemofdying.network;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.luhcarti.totemofdying.item.ItemInit;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;

public class TotemActivationPacket {
    //Displays totem activation by networking. Some guy on reddit did this and changed my life so I cant really explain this code a lot but changed it a bit
    public static void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler,
                               PacketByteBuf buf, PacketSender responseSender) {
        ItemStack totem = new ItemStack(ItemInit.THE_MAD_TOTEM);
        MinecraftClient.getInstance().gameRenderer.showFloatingItem(totem);
    }
}
