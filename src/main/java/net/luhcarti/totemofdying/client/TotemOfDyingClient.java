package net.luhcarti.totemofdying.client;

import net.fabricmc.api.ClientModInitializer;
import net.luhcarti.totemofdying.network.ModNetwork;

public class TotemOfDyingClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        // This entrypoint is suitable for setting up client-specific logic, such as rendering.
        ModNetwork.registerS2CPackets();
    }
}
