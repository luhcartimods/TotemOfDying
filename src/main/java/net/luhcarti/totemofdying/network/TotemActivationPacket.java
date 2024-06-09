package net.luhcarti.totemofdying.network;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.luhcarti.totemofdying.item.ItemInit;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;

public class TotemActivationPacket {
    //Displays totem activation by networking. Some guy on reddit did this and changed my life so I cant really explain this code a lot but changed it a bit
    public static void receive(MinecraftClient client, ClientPlayNetworkHandler handler,
                               PacketByteBuf buf, PacketSender responseSender) {
        ItemStack totem = new ItemStack(ItemInit.THE_MAD_TOTEM);
        client.gameRenderer.showFloatingItem(totem);
    }
}
