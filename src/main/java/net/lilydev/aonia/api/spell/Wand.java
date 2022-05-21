package net.lilydev.aonia.api.spell;

import net.lilydev.aonia.impl.spell.MagicMissileSpell;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.TranslatableText;

public interface Wand {
    /**
     * @return The attached wand's maximum charge capacity.
     */
    default int getCapacity(ItemStack stack) {
        return stack.getOrCreateSubNbt("WandData").getInt("Capacity");
    }

    /**
     * @return The attached wand's currently stored charge amount.
     */
    default int getStoredCharge(ItemStack stack) {
        return stack.getOrCreateSubNbt("WandData").getInt("StoredCharge");
    }

    /**
     * Updates the attached wand's stored charge value.
     * @param delta How much to change the charge by - negative values reduce charge
     * @return The previously-stored charge amount.
     */
    default int updateStoredCharge(int delta,ItemStack stack) {
        int previous = this.getStoredCharge(stack);
        int after = previous + delta;
        stack.getOrCreateSubNbt("WandData").putInt("StoredCharge", after);
        return previous;
    }
    
    default Spell getEquippedSpell() {
        return MagicMissileSpell.INSTANCE;
    }

    default boolean cast(ServerPlayerEntity caster) {
        if (getStoredCharge(caster.getActiveItem()) >= getEquippedSpell().getRequiredCharge()) {
            getEquippedSpell().execute(caster);
            return true;
        } else {
            caster.sendMessage(new TranslatableText("aonia.not_enough_charge"), true);
            return false;
        }
    }
}
