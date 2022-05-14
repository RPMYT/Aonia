package net.lilydev.aonia.impl.block;

import net.lilydev.aonia.Aonia;
import net.lilydev.aonia.api.spell.SpellDescription;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;

public class LandmineBlockEntity extends BlockEntity {
    private SpellDescription loadedSpell;

    public LandmineBlockEntity(BlockPos pos, BlockState state) {
        super(Aonia.LANDMINE_BLENT, pos, state);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        this.loadedSpell = SpellDescription.deserialize(nbt.getCompound("LoadedSpell"));
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        nbt.put("LoadedSpell", this.loadedSpell.serialize());
    }

    public SpellDescription getLoadedSpell() {
        return this.loadedSpell;
    }
}
