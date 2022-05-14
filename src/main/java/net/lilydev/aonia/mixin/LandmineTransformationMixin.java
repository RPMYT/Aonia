package net.lilydev.aonia.mixin;

import net.lilydev.aonia.Aonia;
import net.lilydev.aonia.impl.block.LandmineBlockEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;

@Mixin(Block.class)
public class LandmineTransformationMixin {
    @Inject(method = "onSteppedOn", at = @At("HEAD"))
    public void detonate(World world, BlockPos pos, BlockState state, Entity entity, CallbackInfo ci) {
        Optional<LandmineBlockEntity> landmine = world.getBlockEntity(pos, Aonia.LANDMINE_BLENT);
        if (landmine.isPresent() && entity instanceof ServerPlayerEntity player) {
            landmine.get().getLoadedSpell().execute(player);
        }
    }
}
