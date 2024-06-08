package net.luhcarti.totemofdying.item.custom;


import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ArchEvokerStaff extends Item {
    public ArchEvokerStaff(Properties pProperties) {
        super(pProperties);
    }

    //This just spawns a Evoker Fang and vex when used
    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        if (!pLevel.isClientSide) {
            BlockPos pos = pPlayer.blockPosition().above();

            Entity evokerFangs = EntityType.EVOKER_FANGS.create(pLevel);
            evokerFangs.moveTo(pos.getX(), pos.getY(), pos.getZ(), 0, 0);
            pLevel.addFreshEntity(evokerFangs);

            Entity vex = EntityType.VEX.create(pLevel);
            vex.moveTo(pos.getX(), pos.getY(), pos.getZ(), 0, 0);
            pLevel.addFreshEntity(vex);
        }

        return super.use(pLevel, pPlayer, pUsedHand);
    }
}
