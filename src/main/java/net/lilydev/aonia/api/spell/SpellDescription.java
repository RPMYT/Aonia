package net.lilydev.aonia.api.spell;

import net.lilydev.aonia.Aonia;
import net.lilydev.aonia.util.LockableArrayList;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtString;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class SpellDescription {
    private final Identifier id;
    private final Identifier shape;
    private final LockableArrayList<SpellPiece> pieces = new LockableArrayList<>();

    public SpellDescription(Identifier shape, Identifier id) {
        this.id = id;
        this.shape = shape;
    }

    public void addPiece(SpellPiece piece) {
        this.pieces.add(piece);
    }

    public Identifier shape() {
        return this.shape;
    }

    public Identifier id() {
        return this.id;
    }

    public void markFinished() {
        pieces.lock();
    }

    public void execute(ServerPlayerEntity caster) {
        if (!pieces.isLocked()) {
            Aonia.LOGGER.error("Tried to execute an unfinished spell '" + this.id() + "'!!");
            return;
        }

        ArrayList<Identifier> modifiers = new ArrayList<>();

        AtomicBoolean found = new AtomicBoolean(false);
        AtomicBoolean accepts = new AtomicBoolean(false);
        AtomicReference<ArrayList<Identifier>> accepted = new AtomicReference<>();

        AtomicReference<Entity> currentTargetEntity = new AtomicReference<>();
        AtomicReference<Pair<BlockPos, BlockState>> currentTargetBlock = new AtomicReference<>();

        pieces.forEach(piece -> {
            if (piece.isModifier() && (accepts.get() || !found.get())) {
                if (!found.get()) {
                    pieces.listIterator(pieces.indexOf(piece)).forEachRemaining(other -> {
                        if (!other.isModifier()) {
                            if (piece.acceptsModifiers()) {
                                accepts.set(true);
                                accepted.set(other.acceptedModifiers());
                            }

                            found.set(true);
                        }
                    });
                }

                if (accepted.get().contains(piece.id()) && accepts.get()) {
                    modifiers.add(piece.id());
                }
            } else {
                piece.setTargetEntity(currentTargetEntity.get());
                piece.setTargetBlock(currentTargetBlock.get().getLeft(), currentTargetBlock.get().getRight());
                piece.execute(caster, modifiers);
                currentTargetEntity.set(piece.targetedEntity());
                currentTargetBlock.set(piece.targetedBlock());
            }
        });
    }

    public final NbtCompound serialize() {
        NbtList pieces = new NbtList();
        this.pieces.forEach(piece -> {
            pieces.add(this.pieces.indexOf(piece), NbtString.of(piece.id().toString()));
        });
        NbtCompound compound = new NbtCompound();
        compound.put("SpellComponents", pieces);
        compound.putString("SpellIdentifier", this.id.toString());
        compound.putString("SpellShape", this.shape.toString());
        return compound;
    }

    public static SpellDescription deserialize(NbtCompound compound) {
        SpellDescription description = new SpellDescription(Identifier.tryParse(compound.getString("SpellIdentifier")), Identifier.tryParse(compound.getString("SpellShape")));

        NbtList pieces = (NbtList) compound.get("SpellDescription");
        if (pieces != null) {
            pieces.forEach(identifier -> {
                description.addPiece(SpellPiece.Registry.get(Identifier.tryParse(identifier.asString())));
            });
        }
        description.markFinished();

        return description;
    }
}