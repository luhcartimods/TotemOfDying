package net.luhcarti.totemofdying.network;

import net.luhcarti.totemofdying.item.ItemInit;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkEvent;
import net.minecraft.network.FriendlyByteBuf;


import java.util.function.Supplier;

//So i needed all this becuase it has to work on servers
public class TotemActivationPacket {
    private final int playerId;

    public TotemActivationPacket(int playerId) {
        this.playerId = playerId;
    }

    public static void encode(TotemActivationPacket packet, FriendlyByteBuf buffer) {
        buffer.writeInt(packet.playerId);
    }

    public static TotemActivationPacket decode(FriendlyByteBuf buffer) {
        return new TotemActivationPacket(buffer.readInt());
    }

    //Displays totem activation by networking. Some guy on reddit did this and changed my life so I cant really explain this code a lot but changed it a bit
    public static void handle(TotemActivationPacket packet, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> {
            Player player = (Player) Minecraft.getInstance().level.getEntity(packet.playerId);
            if (player != null) {
                ItemStack totem = new ItemStack(ItemInit.THE_MAD_TOTEM.get());
                Minecraft.getInstance().gameRenderer.displayItemActivation(totem);
            }
        });
        context.setPacketHandled(true);
    }


    @OnlyIn(Dist.CLIENT)
    private static void displayTotemActivation(Player player) {
        ItemStack totem = new ItemStack(ItemInit.THE_MAD_TOTEM.get());
        Minecraft.getInstance().gameRenderer.displayItemActivation(totem);
    }
}
