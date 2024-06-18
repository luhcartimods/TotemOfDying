package net.luhcarti.totemofdying.item.custom;


import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterials;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ArchEvokerStaff extends SwordItem {
    public ArchEvokerStaff(Settings settings) {
        super(ToolMaterials.NETHERITE, 3, -3.2F, settings);
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
}