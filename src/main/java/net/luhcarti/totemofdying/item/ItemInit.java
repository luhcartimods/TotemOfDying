package net.luhcarti.totemofdying.item;

import net.luhcarti.totemofdying.TotemOfDying;
import net.luhcarti.totemofdying.item.custom.ArchEvokerStaff;
import net.minecraft.world.item.*;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ItemInit {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, TotemOfDying.MODID);

    public static final RegistryObject<Item> STAFF_OF_THE_ARCH_REVOKER = ITEMS.register("staff_of_the_arch_revoker", ()-> new ArchEvokerStaff(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> THE_MAD_TOTEM = ITEMS.register("the_mad_totem", ()-> new Item(new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> VINDICATOR_WARHAMMER = ITEMS.register("vindicator_warhammer", ()-> new AxeItem(Tiers.NETHERITE, 10.0F, -1.4F, (new Item.Properties().stacksTo(1))));

    //Vindicator Hammer is just a hammer with 3d model like hammer in arcane (see recourses) and 15 damage and 2.6 attack speed



    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}