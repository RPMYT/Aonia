package net.lilydev.aonia.api.spell;

import net.lilydev.aonia.Aonia;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;
import net.minecraft.util.math.BlockPos;

import java.util.HashMap;

@SuppressWarnings("unused")
public abstract class SpellPiece {
    public final Type type;
    public final Identifier id;
    protected NbtCompound data = new NbtCompound();

    private Entity targetEntity;
    private final Pair<BlockPos, BlockState> targetBlock = new Pair<>(BlockPos.ORIGIN, Blocks.AIR.getDefaultState());

    public SpellPiece(Type type, Identifier id) {
        this.id = id;
        this.type = type;
    }

    public Entity targetedEntity() {
        return this.targetEntity;
    }

    public Pair<BlockPos, BlockState> targetedBlock() {
        return this.targetBlock;
    }

    public void setTargetEntity(Entity targetEntity) {
        this.targetEntity = targetEntity;
    }

    public void setTargetBlock(BlockPos pos, BlockState state) {
        this.targetBlock.setLeft(pos);
        this.targetBlock.setRight(state);
    }

    public void execute(Entity caster) {
        Aonia.LOGGER.debug("Executing spell piece: " + this.id + " (casted by: " + caster.getName() + "' (UUID: '" + caster.getUuidAsString() + "'");
    }

    public static class Registry {
        private static final HashMap<Identifier, SpellPiece> SPELL_PIECES = new HashMap<>();

        public static SpellPiece add(SpellPiece piece) {
            SPELL_PIECES.put(piece.id, piece);
            return piece;
        }

        public static SpellPiece get(Identifier id) {
            return SPELL_PIECES.get(id);
        }
    }

    public enum Type {
        SHAPE,
        ACTION
    }
/*
    enum Modifier implements SpellObject {
        WEAKEN,
        REPEAT,
        STRENGTHEN,
    }

    enum Action implements SpellObject {
        HEAL,
        FORK,
        PLACE,
        SUMMON,
        DAMAGE,
        DESTROY,
        TELEPORT,
        DISORIENT,
    }

    enum Target implements SpellObject {
        ENTITY_TYPE,
        ALL_PLAYERS,
        ALL_ENTITIES,
        ALL_HOSTILES,
        ALL_PASSIVES,
        SPECIFIC_PLAYER,
        COORDINATE_RELATIVE,
        COORDINATE_ABSOLUTE,
    }
*/
}
