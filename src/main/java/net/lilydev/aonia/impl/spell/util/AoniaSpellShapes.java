package net.lilydev.aonia.impl.spell.util;

import net.lilydev.aonia.api.spell.SpellPiece;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;

import java.util.ArrayList;

public class AoniaSpellShapes {
    public static final SpellPiece TOUCH = SpellPiece.Registry.add(new SpellPiece(SpellPiece.Type.SHAPE, new Identifier("aonia", "touch")) {
        @Override
        public void execute(ServerPlayerEntity caster, ArrayList<Identifier> modifiers) {
            super.execute(caster, modifiers);
            HitResult result = caster.raycast(1, 0, false);
            switch (result.getType()) {
                case BLOCK -> {
                    BlockHitResult bhr = (BlockHitResult) result;
                    this.setTargetBlock(bhr.getBlockPos(), caster.world.getBlockState(bhr.getBlockPos()));
                }
                case ENTITY -> {
                    EntityHitResult ehr = (EntityHitResult) result;
                    this.setTargetEntity(ehr.getEntity());
                }
            }
        }
    });

    public static final SpellPiece PROJECTILE = SpellPiece.Registry.add(new SpellPiece(SpellPiece.Type.SHAPE, new Identifier("aonia", "projectile")) {
        @Override
        public void execute(ServerPlayerEntity caster, ArrayList<Identifier> modifiers) {
            super.execute(caster, modifiers);
            HitResult result = caster.raycast(10, 0, false);
            if (result instanceof EntityHitResult ehr) {
                this.setTargetEntity(ehr.getEntity());
            }
        }
    });
//    ;
//    public final SpellPiece piece;
//
//    AoniaSpellShapes(String name) {
//        this.piece = new SpellPiece() {
//            @Override
//            public void execute(ServerPlayerEntity caster, ArrayList<Identifier> modifiers) {
//                super.execute(caster, modifiers);
//                switch (AoniaSpellShapes.this) {
//
//                    case PROJECTILE -> {
//                    }
//
//                    case LANDMINE -> {
//                        HitResult result = caster.raycast(4, 0, false);
//                        if (result instanceof BlockHitResult bhr) {
//                            BlockState state = caster.world.getBlockState(bhr.getBlockPos());
//                            if (!state.isAir()) {
//                                LandmineBlockEntity landmine = new LandmineBlockEntity(bhr.getBlockPos(), state);
//                                //TODO: get spell ID from currently equipped wand
//                                SpellDescription description = new SpellDescription(AoniaSpellShapes.TOUCH.piece.id(), null);
//                                caster.world.addBlockEntity(landmine);
//                            }
//                        }
//                    }
//                }
//            }
//        };
//    }
}
