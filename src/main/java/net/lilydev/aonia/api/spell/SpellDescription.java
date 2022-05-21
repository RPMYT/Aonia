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

public final class SpellDescription implements Spell {
    public final Identifier id;
    public final Identifier shape;
    private final int requiredCharge;
    private final SpellPiece[] pieces;

    SpellDescription(Identifier id, Identifier shape, int requiredCharge, SpellPiece[] pieces) {
        this.id = id;
        this.shape = shape;
        this.pieces = pieces;
        this.requiredCharge = requiredCharge;
    }

    public void execute(ServerPlayerEntity caster) {
        Entity currentTargetEntity = null;
        Pair<BlockPos, BlockState> currentTargetBlock = new Pair<>(BlockPos.ORIGIN, Blocks.AIR.getDefaultState());
        NbtCompound data = null;

        for (SpellPiece piece : pieces) {
            piece.setTargetEntity(currentTargetEntity);
            piece.setTargetBlock(currentTargetBlock.getLeft(), currentTargetBlock.getRight());
            piece.data = data;
            piece.execute(caster);
            currentTargetEntity = piece.targetedEntity();
            currentTargetBlock = piece.targetedBlock();
            data = piece.data;
        }
    }

    @Override
    public int getRequiredCharge() {
        return this.requiredCharge;
    }

    public NbtCompound serialize() {
        NbtList pieces = new NbtList();
        for (SpellPiece piece : this.pieces) {
            pieces.add(NbtString.of(piece.id.toString()));
        }

        NbtCompound compound = new NbtCompound();
        compound.put("SpellComponents", pieces);
        compound.putString("SpellIdentifier", this.id.toString());
        compound.putString("SpellShape", this.shape.toString());
        compound.putInt("RequiredCharge", this.getRequiredCharge());
        return compound;
    }

    public static SpellDescription deserialize(NbtCompound compound) {
        ArrayList<SpellPiece> pieces = new ArrayList<>();

        NbtList serializedPieces = (NbtList) compound.get("SpellDescription");
        if (serializedPieces != null) {
            serializedPieces.forEach(identifier -> pieces.add(SpellPiece.Registry.get(Identifier.tryParse(identifier.asString()))));
        }

        return new SpellDescription.Builder(Identifier.tryParse(compound.getString("SpellIdentifier")), Identifier.tryParse(compound.getString("SpellShape")), pieces, compound.getInt("RequiredCharge")).build();
    }

    public static final class Builder {
        public final Identifier id;
        public final Identifier shape;
        public final int requiredCharge;
        final ArrayList<SpellPiece> pieces;

        public Builder(Identifier shape, Identifier id, Identifier[] shapeModifiers, int requiredCharge) {
            this.id = id;
            this.shape = shape;
            this.requiredCharge = requiredCharge;

            this.pieces = new ArrayList<>();

            for (Identifier modifier : shapeModifiers) {
                this.pieces.add(SpellPiece.Registry.get(modifier));
            }

            this.pieces.add(SpellPiece.Registry.get(shape));
        }

        public Builder(Identifier shape, Identifier id, ArrayList<SpellPiece> pieces, int requiredCharge) {
            this.id = id;
            this.shape = shape;
            this.pieces = pieces;
            this.requiredCharge = requiredCharge;
        }

        public void addPiece(SpellPiece piece) {
            this.pieces.add(piece);
        }

        public SpellDescription build() {
            SpellPiece[] pieces = new SpellPiece[this.pieces.size()];
            this.pieces.toArray(pieces);
            return new SpellDescription(this.id, this.shape, this.requiredCharge, pieces);
        }
    }
}