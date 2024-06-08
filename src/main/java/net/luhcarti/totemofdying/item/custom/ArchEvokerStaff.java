package net.luhcarti.totemofdying.item.custom;


import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ArchEvokerStaff extends Item {
    public ArchEvokerStaff(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (!world.isClient) {
            BlockPos pos = user.getBlockPos().up();

            Entity evokerFangs = EntityType.EVOKER_FANGS.create(world);
            evokerFangs.refreshPositionAndAngles(pos.getX(), pos.getY(), pos.getZ(), 0, 0);
            world.spawnEntity(evokerFangs);

            Entity vex = EntityType.VEX.create(world);
            vex.refreshPositionAndAngles(pos.getX(), pos.getY(), pos.getZ(), 0, 0);
            world.spawnEntity(vex);
        }
        return super.use(world, user, hand);
    }

    //This just spawns a Evoker Fang and vex when used
//    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
//        if (!pLevel.isClientSide) {
//            BlockPos pos = pPlayer.blockPosition().above();
//
//            Entity evokerFangs = EntityType.EVOKER_FANGS.create(pLevel);
//            evokerFangs.moveTo(pos.getX(), pos.getY(), pos.getZ(), 0, 0);
//            pLevel.addFreshEntity(evokerFangs);
//
//            Entity vex = EntityType.VEX.create(pLevel);
//            vex.moveTo(pos.getX(), pos.getY(), pos.getZ(), 0, 0);
//            pLevel.addFreshEntity(vex);
//        }
//
//        return super.use(pLevel, pPlayer, pUsedHand);
//    }
}
