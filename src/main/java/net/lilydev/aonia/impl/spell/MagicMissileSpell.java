package net.lilydev.aonia.impl.spell;

import net.lilydev.aonia.api.spell.Spell;
import net.lilydev.aonia.api.spell.SpellDescription;
import net.lilydev.aonia.impl.spell.util.AoniaSpellPieces;
import net.lilydev.aonia.impl.spell.util.AoniaSpellShapes;
import net.minecraft.client.util.ParticleUtil;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;

public class MagicMissileSpell implements Spell {
    private final SpellDescription spell;
    public static final MagicMissileSpell INSTANCE = new MagicMissileSpell();

    private MagicMissileSpell() {
        SpellDescription.Builder builder = new SpellDescription.Builder(AoniaSpellShapes.PROJECTILE.id, new Identifier("aonia", "magic_missile"), 0);
        builder.addPiece(AoniaSpellPieces.HARM);
        this.spell = builder.build();
    }

    @Override
    public void execute(ServerPlayerEntity caster) {
        Vec3d camera = caster.getCameraPosVec(0);
        Vec3d rotation = caster.getRotationVec(0);
        Vec3d target = camera.add(rotation.x * 1, rotation.y * 1, rotation.z * 1);
        EntityHitResult ehr = ProjectileUtil.raycast(caster, camera, target, Box.from(target), entity -> true, 100);
        if (ehr != null) {
            double distance = caster.squaredDistanceTo(ehr.getEntity());
            BlockPos start = new BlockPos(caster.getX(), caster.getEyeY(), caster.getZ());
            for (int i = 1; i <= distance; i++) {
                BlockPos there = start.offset(caster.getHorizontalFacing(), i);
                ParticleUtil.spawnParticle(caster.world, there, caster.getHorizontalFacing(), ParticleTypes.END_ROD);
            }
        }
        this.spell.execute(caster);
    }

    @Override
    public int getRequiredCharge() {
        return 0;
    }
}
