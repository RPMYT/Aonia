package net.lilydev.aonia.api.spell;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtString;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;

public class SpellDescription {
    public final Identifier id;
    public final Identifier shape;
    private final SpellPiece[] pieces;

    SpellDescription(Builder builder) {
        this.pieces = new SpellPiece[builder.pieces.size()];
        builder.pieces.toArray(this.pieces);
        builder.pieces.clear();
        this.id = builder.id;
        this.shape = builder.shape;
    }

    public void execute(ServerPlayerEntity caster) {
        ArrayList<Identifier> modifiers = new ArrayList<>();

        boolean found = false;
        AtomicReference<ArrayList<Identifier>> accepted = new AtomicReference<>();

        Entity currentTargetEntity = caster;
        Pair<BlockPos, BlockState> currentTargetBlock = new Pair<>(BlockPos.ORIGIN, Blocks.AIR.getDefaultState());

        for (SpellPiece piece : pieces) {
            piece.setTargetEntity(currentTargetEntity);
            piece.setTargetBlock(currentTargetBlock.getLeft(), currentTargetBlock.getRight());
            piece.execute(caster);
            currentTargetEntity = piece.targetedEntity();
            currentTargetBlock = piece.targetedBlock();
        }
    }

    public final NbtCompound serialize() {
        NbtList pieces = new NbtList();
        for (SpellPiece piece : this.pieces) {
            pieces.add(NbtString.of(piece.id.toString()));
        }

        NbtCompound compound = new NbtCompound();
        compound.put("SpellComponents", pieces);
        compound.putString("SpellIdentifier", this.id.toString());
        compound.putString("SpellShape", this.shape.toString());
        return compound;
    }

    public static SpellDescription deserialize(NbtCompound compound) {
        SpellDescription.Builder builder = new SpellDescription.Builder(Identifier.tryParse(compound.getString("SpellIdentifier")), Identifier.tryParse(compound.getString("SpellShape")));

        NbtList pieces = (NbtList) compound.get("SpellDescription");
        if (pieces != null) {
            pieces.forEach(identifier -> builder.addPiece(SpellPiece.Registry.get(Identifier.tryParse(identifier.asString()))));
        }

        return builder.build();
    }

    public static class Builder {
        public final Identifier id;
        public final Identifier shape;
        final ArrayList<SpellPiece> pieces = new ArrayList<>();

        public Builder(Identifier shape, Identifier id) {
            this.id = id;
            this.shape = shape;
        }

        public void addPiece(SpellPiece piece) {
            this.pieces.add(piece);
        }

        public SpellDescription build() {
            return new SpellDescription(this);
        }
    }
}