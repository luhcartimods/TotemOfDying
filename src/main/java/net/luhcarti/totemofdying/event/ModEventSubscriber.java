package net.luhcarti.totemofdying.event;

import net.luhcarti.totemofdying.TotemOfDying;
import net.luhcarti.totemofdying.item.ItemInit;
import net.luhcarti.totemofdying.network.ModNetwork;
import net.luhcarti.totemofdying.network.TotemActivationPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.monster.Pillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.PacketDistributor;

@Mod.EventBusSubscriber(modid = TotemOfDying.MODID)
public class ModEventSubscriber {

    @SubscribeEvent
    public static void onLivingDeath(LivingDeathEvent event) {
        if (event.getEntity() instanceof Player player) {
            ItemStack totem = new ItemStack(ItemInit.THE_MAD_TOTEM.get());
            //This checks if they have the totem, if they dont have the totem it wont save them
            if (getActiveTotemOfUndying(player) != null && !player.getCooldowns().isOnCooldown(totem.getItem())) {
                event.setCanceled(true);

                //Gives the standard totem effects and applies cooldown
                player.setHealth(1.0F);
                player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 900, 1));
                player.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 100, 1));
                player.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 800, 0));
                player.getCooldowns().addCooldown(totem.getItem(), 600);

                //Plays the totem sound
                Level level = player.level();
                level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.TOTEM_USE, player.getSoundSource(), 1.0F, 1.0F);

                if (!level.isClientSide) {
                    //This is for the animation, just sends the TotemActivationPacket to all connected clients
                    ModNetwork.INSTANCE.send(PacketDistributor.ALL.noArg(), new TotemActivationPacket(player.getId()));

                    //This is where I mean janky code, to explain it has these mobs and it spawns them on random so between 13-19 and randowm of the mobs, then just spawns
                    //In intervals like a raid, very bad code sorry
                    int numMobs = 13 + level.random.nextInt(6);
                    EntityType<?>[] mobTypes = {EntityType.PILLAGER, EntityType.EVOKER, EntityType.RAVAGER};

                    new Thread(() -> {
                        try {
                            for (int i = 0; i < numMobs; i++) {
                                Thread.sleep(1000);
                                EntityType<?> mobType = mobTypes[level.random.nextInt(mobTypes.length)];
                                Mob mob = (Mob) mobType.create(level);
                                mob.setPos(player.getX() + level.random.nextDouble() * 10 - 5, player.getY(), player.getZ() + level.random.nextDouble() * 10 - 5);
                                mob.setTarget(player.getAbilities().instabuild ? null : player);
                                if(mob instanceof Pillager pillager) {
                                    pillager.finalizeSpawn((ServerLevelAccessor) level, level.getCurrentDifficultyAt(player.getOnPos()), MobSpawnType.TRIGGERED, null, null);
                                }
                                level.addFreshEntity(mob);
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }).start();
                }
            }
        }
    }

    private static ItemStack getActiveTotemOfUndying(Player player) {
        for (InteractionHand hand : InteractionHand.values()) {
            ItemStack itemStack = player.getItemInHand(hand);
            if (!itemStack.is(ItemInit.THE_MAD_TOTEM.get())) continue;
            return itemStack;
        }
        return null;
    }
}
