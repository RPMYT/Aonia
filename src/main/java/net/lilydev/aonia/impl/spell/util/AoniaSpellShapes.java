package net.lilydev.aonia.impl.spell.util;

public class AoniaSpellShapes {
//    public static final SpellPiece TOUCH = SpellPiece.Registry.add(new SpellPiece(SpellPiece.Type.SHAPE, new Identifier("aonia", "touch")) {
//        @Override
//        public void execute(Entity caster) {
//            super.execute(caster);
//            BlockHitResult result = (BlockHitResult) caster.raycast(1, 0, false);
//            Vec3d camera = caster.getCameraPosVec(0);
//            Vec3d rotation = caster.getRotationVec(0);
//            Vec3d target = camera.add(rotation.x, rotation.y, rotation.z);
//            EntityHitResult ehr = ProjectileUtil.raycast(caster, camera, target, Box.from(target), entity -> true, 1);
//            if (ehr != null) {
//                this.setTargetEntity(ehr.getEntity());
//            } else {
//                this.setTargetBlock(result.getBlockPos(), caster.world.getBlockState(result.getBlockPos()));
//            }
//        }
//    });
//
//    public static final SpellPiece PROJECTILE = SpellPiece.Registry.add(new SpellPiece(SpellPiece.Type.SHAPE, new Identifier("aonia", "projectile")) {
//        @Override
//        public void execute(Entity caster) {
//            super.execute(caster);
//            ItemStack stack = caster.getActiveItem();
//            if (stack.getItem() instanceof Spellcaster holder) {
//                int range = 7;
//
//                BlockHitResult result = (BlockHitResult) caster.raycast(range, 0, false);
//                Vec3d camera = caster.getCameraPosVec(0);
//                Vec3d rotation = caster.getRotationVec(0);
//                Vec3d target = camera.add(rotation.x * range, rotation.y * range, rotation.z * range);
//                EntityHitResult ehr = ProjectileUtil.raycast(caster, camera, target, new Box(camera, target), entity -> true, range * 10);
//                if (ehr != null) {
//                    this.setTargetEntity(ehr.getEntity());
//                        double distance = caster.squaredDistanceTo(ehr.getEntity());
//                        BlockPos start = new BlockPos(caster.getX(), caster.getEyeY(), caster.getZ());
//                        for (int i = 1; i <= distance; i++) {
//                            BlockPos there = start.offset(caster.getHorizontalFacing(), i);
//                            ParticleUtil.spawnParticle(caster.world, there, caster.getHorizontalFacing(), ParticleTypes.END_ROD);
//                    }
//                } else if (result.getType() != HitResult.Type.MISS) {
//                    this.setTargetBlock(result.getBlockPos(), caster.world.getBlockState(result.getBlockPos()));
//                }
//            }
//        }
//    });
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
