package net.lilydev.aonia.impl.spell.util;

import net.lilydev.aonia.api.spell.SpellPiece;
import net.minecraft.client.util.ParticleUtil;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;

public class AoniaSpellShapes {
    public static final SpellPiece TOUCH = SpellPiece.Registry.add(new SpellPiece(SpellPiece.Type.SHAPE, new Identifier("aonia", "touch")) {
        @Override
        public void execute(ServerPlayerEntity caster) {
            super.execute(caster);
            BlockHitResult result = (BlockHitResult) caster.raycast(1, 0, false);
            Vec3d camera = caster.getCameraPosVec(0);
            Vec3d rotation = caster.getRotationVec(0);
            Vec3d target = camera.add(rotation.x * 1, rotation.y * 1, rotation.z * 1);
            EntityHitResult ehr = ProjectileUtil.raycast(caster, camera, target, Box.from(target), entity -> true, 1);
            if (ehr != null) {
                this.setTargetEntity(ehr.getEntity());
            } else {
                this.setTargetBlock(result.getBlockPos(), caster.world.getBlockState(result.getBlockPos()));
            }
        }
    });

    public static final SpellPiece PROJECTILE = SpellPiece.Registry.add(new SpellPiece(SpellPiece.Type.SHAPE, new Identifier("aonia", "projectile")) {
        @Override
        public void execute(ServerPlayerEntity caster) {
            super.execute(caster);
            BlockHitResult result = (BlockHitResult) caster.raycast(10, 0, false);
            Vec3d camera = caster.getCameraPosVec(0);
            Vec3d rotation = caster.getRotationVec(0);
            Vec3d target = camera.add(rotation.x * 10, rotation.y * 10, rotation.z * 10);
            EntityHitResult ehr = ProjectileUtil.raycast(caster, camera, target, new Box(camera, target), entity -> true, 100);
            if (ehr != null) {
                this.setTargetEntity(ehr.getEntity());
            } else {
                this.setTargetBlock(result.getBlockPos(), caster.world.getBlockState(result.getBlockPos()));
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
