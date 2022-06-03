package net.lilydev.aonia.api.item;

import eu.pb4.polymer.api.item.PolymerItem;
import net.lilydev.aonia.api.spell.Spell;
import net.lilydev.aonia.api.spell.SpellPiece;
import net.lilydev.aonia.api.spell.Spellcaster;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;

public abstract class AonianRangedSpellExecutorItem extends Item implements Spellcaster, PolymerItem {
    protected HitResult target;

    public AonianRangedSpellExecutorItem(Settings settings) {
        super(settings);
    }

    public HitResult getTarget() {
        return this.target;
    }

    public void setTarget(HitResult target) {
        this.target = target;
    }

    @Override
    public boolean cast(Entity caster) {
        Spell spell = this.getEquippedSpell();
        HitResult result = this.getTarget();

        if (result.getType() == HitResult.Type.MISS) { //TODO: angelic modifier
            return false;
        }

        for (SpellPiece piece : spell.getPieces()) {
            if (piece.targetedEntity() == null && piece.targetedBlock() == null) {
                if (result instanceof BlockHitResult bhr) {
                    piece.setTargetBlock(bhr.getBlockPos(), caster.world.getBlockState(bhr.getBlockPos()));
                }

                if (result instanceof EntityHitResult ehr) {
                    piece.setTargetEntity(ehr.getEntity());
                }
            }
            piece.execute(caster);
        }

        return true;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        return this.cast((ServerPlayerEntity) user) ? TypedActionResult.success(user.getStackInHand(hand), true) : TypedActionResult.fail(user.getStackInHand(hand));
    }
}
