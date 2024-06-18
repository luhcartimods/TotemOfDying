package net.luhcarti.totemofdying.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.luhcarti.totemofdying.TotemOfDying;
import net.luhcarti.totemofdying.item.custom.ArchEvokerStaff;
import net.minecraft.item.AxeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ToolMaterials;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ItemInit {
    public static final Item STAFF_OF_THE_ARCH_REVOKER = registerItem("staff_of_the_arch_revoker", new ArchEvokerStaff(new FabricItemSettings()));
    public static final Item THE_MAD_TOTEM = registerItem("the_mad_totem", new Item(new FabricItemSettings().maxCount(1)));

    public static final Item VINDICATOR_WARHAMMER = registerItem("vindicator_warhammer", new AxeItem(ToolMaterials.NETHERITE, 10.0F, -4.3F, new FabricItemSettings().maxCount(1)));

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(TotemOfDying.MOD_ID, name), item);
    }

    public static void registerModItems() {
        TotemOfDying.LOGGER.info("Registering Mod Items for " + TotemOfDying.MOD_ID);
    }
}