package net.luhcarti.totemofdying.event;

import net.luhcarti.totemofdying.TotemOfDying;
import net.luhcarti.totemofdying.item.ItemInit;
import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.Pillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = TotemOfDying.MODID)
public class ModEventSubscriber {

    @SubscribeEvent
    public static void onLivingDeath(LivingDeathEvent event) {
        if (event.getEntity() instanceof Player player) {
            ItemStack totem = new ItemStack(ItemInit.THE_MAD_TOTEM.get());
            if (player.getMainHandItem().is(totem.getItem()) || player.getOffhandItem().is(totem.getItem())) { // Check if the player has the totem in their main hand or off hand
                event.setCanceled(true);


                //Normal totem things, gives the effects
                player.setHealth(1.0F);
                player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 900, 1));
                player.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 100, 1));
                player.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 800, 0));

                //plays the totem sound on pop
                Level level = player.level();
                level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.TOTEM_USE, player.getSoundSource(), 1.0F, 1.0F);

                //This is the actual animation, it can only be server side becuause otherwise game crash and mod is used on server
                if (!level.isClientSide) {
                    Minecraft.getInstance().gameRenderer.displayItemActivation(totem);
                    //This just checks if they have the totem in their main or off hand
                    if (player.getMainHandItem().is(totem.getItem())) {
                        player.setItemInHand(InteractionHand.MAIN_HAND, ItemStack.EMPTY);
                    } else if (player.getOffhandItem().is(totem.getItem())) {
                        player.setItemInHand(InteractionHand.OFF_HAND, ItemStack.EMPTY);
                    }

                    //Ok so this whole part just randomly selects the mobs and spawns them, not the cleanest code at all sorry
                    int numMobs = 13 + level.random.nextInt(6);
                    EntityType<?>[] mobTypes = {EntityType.PILLAGER, EntityType.EVOKER, EntityType.RAVAGER};

                    for (int i = 0; i < numMobs; i++) {
                        EntityType<?> mobType = mobTypes[level.random.nextInt(mobTypes.length)];
                        Mob mob = (Mob) mobType.create(level);
                        mob.setPos(player.getX() + level.random.nextDouble() * 10 - 5, player.getY(), player.getZ() + level.random.nextDouble() * 10 - 5);
                        mob.setTarget(player);
                        level.addFreshEntity(mob);
                    }
                    for (int i = 0; i < 30; ++i) {
                        double d0 = level.random.nextGaussian() * 0.02D;
                        double d1 = level.random.nextGaussian() * 0.02D;
                        double d2 = level.random.nextGaussian() * 0.02D;
                        level.addParticle(ParticleTypes.TOTEM_OF_UNDYING, player.getX() + (double) (level.random.nextFloat() * player.getBbWidth() * 2.0F) - (double) player.getBbWidth(), player.getY() + 0.5D + (double) (level.random.nextFloat() * player.getBbHeight()), player.getZ() + (double) (level.random.nextFloat() * player.getBbWidth() * 2.0F) - (double) player.getBbWidth(), d0, d1, d2);
                    }
                }
            }
        }
    }
}
