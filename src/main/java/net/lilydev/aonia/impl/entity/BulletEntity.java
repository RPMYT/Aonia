package net.lilydev.aonia.impl.entity;

import eu.pb4.polymer.api.entity.PolymerEntity;
import net.lilydev.aonia.Aonia;
import net.lilydev.aonia.api.item.AonianFirearmItem;
import net.lilydev.aonia.api.spell.Spell;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class BulletEntity extends ArrowEntity implements PolymerEntity {
    private final Spell spell;
    private final boolean enchanted;
    private final AonianFirearmItem firearm;

    public BulletEntity(EntityType<BulletEntity> type, World world) {
        super(Aonia.BULLET, world);
        this.spell = null;
        this.firearm = null;
        this.enchanted = false;
        this.setNoGravity(true);
    }

    public BulletEntity(World world, double x, double y, double z, LivingEntity owner, AonianFirearmItem firearm) {
        super(world, x, y, z);
        this.setOwner(owner);
        this.spell = null;
        this.firearm = firearm;
        this.enchanted = false;
        this.setNoGravity(true);
    }

    public BulletEntity(World world, double x, double y, double z, LivingEntity owner, Spell spell, AonianFirearmItem firearm) {
        super(world, x, y, z);
        this.setOwner(owner);
        this.spell = spell;
        this.firearm = firearm;
        this.enchanted = true;
        this.setNoGravity(true);
    }


    public boolean isEnchanted() {
        return this.enchanted;
    }

    public Spell getAttachedSpell() {
        return this.spell;
    }

    @Override
    public EntityType<?> getPolymerEntityType() {
        return EntityType.SHULKER_BULLET;
    }

    @Override
    protected void onHit(LivingEntity target) {
        super.onHit(target);
        if (this.isEnchanted()) {
            this.firearm.setTarget(new EntityHitResult(target));
            this.firearm.cast(this.getOwner());
            target.damage(DamageSource.MAGIC, this.firearm.getDamage());
            return;
        }
        target.damage(DamageSource.mobProjectile(this, (LivingEntity) this.getOwner()), this.firearm.getDamage());
    }

    @Override
    public void tick() {
        super.tick();
        this.move(MovementType.SELF, Vec3d.fromPolar(this.getPitch(), this.getYaw()).multiply(4.5));
    }
}
