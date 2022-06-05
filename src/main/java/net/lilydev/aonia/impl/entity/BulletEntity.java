package net.lilydev.aonia.impl.entity;

import eu.pb4.polymer.api.entity.PolymerEntity;
import net.lilydev.aonia.Aonia;
import net.lilydev.aonia.api.item.AonianFirearmItem;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BulletEntity extends ArrowEntity implements PolymerEntity {
    private final boolean enchanted;
    private final AonianFirearmItem firearm;

    public BulletEntity(World world) {
        super(Aonia.BULLET, world);
        this.firearm = null;
        this.enchanted = false;
    }

    public BulletEntity(World world, double x, double y, double z, LivingEntity owner, AonianFirearmItem firearm, boolean enchanted) {
        super(Aonia.BULLET, world);
        this.setPosition(x, y, z);
        this.setOwner(owner);
        this.firearm = firearm;
        this.enchanted = enchanted;
    }


    public boolean isEnchanted() {
        return this.enchanted;
    }

    @Override
    public EntityType<?> getPolymerEntityType() {
        return EntityType.EXPERIENCE_ORB;
    }

    @Override
    public double getDamage() {
        return this.firearm.getDamage();
    }

    @Override
    protected void onCollision(HitResult result) {
        super.onCollision(result);
        if (result instanceof EntityHitResult ehr) {
            if (ehr.getEntity() == this.getOwner()) {
                this.collidedSoftly = false;
                this.verticalCollision = false;
                this.horizontalCollision = false;
                return;
            }

            this.onEntityHit(ehr);
            this.discard();
        }

        if (result instanceof BlockHitResult bhr) {
            BlockPos pos = bhr.getBlockPos();
            BlockState state = this.getWorld().getBlockState(pos);
            if (state.isSolidBlock(this.getWorld(), pos)) {
                this.discard();
            }
        }
    }

    @Override
    protected void onEntityHit(EntityHitResult result) {
        if (this.isEnchanted()) {
            this.firearm.setTarget(result);
            if (this.getOwner() != null) { //TODO: dispenser integration w/ fake players
                this.firearm.cast((LivingEntity) this.getOwner());
            }
        }
        result.getEntity().damage(this.enchanted ? DamageSource.magic(this, this.getOwner()) : DamageSource.mobProjectile(this, (LivingEntity) this.getOwner()), (float) this.getDamage());
    }

    @Override
    public void tick() {
        super.tick();
        this.move(MovementType.SELF, this.getVelocity());
        if (this.age > 60 || this.onGround || this.inGround) {
            this.discard();
        }
    }
}
