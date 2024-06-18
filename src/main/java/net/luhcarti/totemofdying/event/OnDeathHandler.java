package net.luhcarti.totemofdying.event;

import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.luhcarti.totemofdying.item.ItemInit;
import net.luhcarti.totemofdying.network.ModNetwork;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.PillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;

public class OnDeathHandler implements ServerLivingEntityEvents.AllowDeath {

    @Override
    public boolean allowDeath(LivingEntity entity, DamageSource damageSource, float damageAmount) {
        if (damageSource.isIn(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
            return true;
        }
        if (entity instanceof PlayerEntity player) {
            ItemStack totem = new ItemStack(ItemInit.THE_MAD_TOTEM);
            //This checks if they have the totem, if they dont have the totem it wont save them
            if (getActiveTotemOfUndying(player) != null && !player.getItemCooldownManager().isCoolingDown(totem.getItem())) {

                //Gives the standard totem effects and applies cooldown
                player.setHealth(1.0F);
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 900, 1));
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.ABSORPTION, 100, 1));
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, 800, 0));
                player.getItemCooldownManager().set(getActiveTotemOfUndying(player).getItem(), 600);

                //Plays the totem sound
                World world = player.getWorld();
                world.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ITEM_TOTEM_USE, player.getSoundCategory(), 1.0F, 1.0F);

                //Checks if the player has the totem in their main or off hand, if they dont the totem doesnt totem
                if (!world.isClient) {
                    //This is for the animation, just sends the TotemActivationPacket to all connected clients
                    ServerPlayNetworking.send((ServerPlayerEntity) player, ModNetwork.ACTIVATE_TOTEM_ID, PacketByteBufs.create());

                    //This is where I mean janky code, to explain it has these mobs and it spawns them on random so between 13-19 and randowm of the mobs, then just spawns
                    //In intervals like a raid, very bad code sorry
                    int numMobs = 13 + world.random.nextInt(6);
                    EntityType<?>[] mobTypes = {EntityType.PILLAGER, EntityType.EVOKER, EntityType.RAVAGER};

                    for (int i = 0; i < numMobs; i++) {
                        EntityType<?> mobType = mobTypes[world.random.nextInt(mobTypes.length)];
                        ItemStack spawnEgg = new ItemStack(SpawnEggItem.forEntity(mobType));
                        player.giveItemStack(spawnEgg);
                    }
                }
                return false;
            }
        }
        return true;
    }

    private ItemStack getActiveTotemOfUndying(PlayerEntity player) {
        for (Hand hand : Hand.values()) {
            ItemStack itemStack = player.getStackInHand(hand);
            if (!itemStack.isOf(ItemInit.THE_MAD_TOTEM)) continue;
            return itemStack;
        }
        return null;
    }
}