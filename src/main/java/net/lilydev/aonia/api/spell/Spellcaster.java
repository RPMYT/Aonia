package net.lilydev.aonia.api.spell;

import net.lilydev.aonia.impl.spell.MagicMissileSpell;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;

public interface Spellcaster {
    /**
     * @return The item's maximum charge capacity.
     * @apiNote A negative value should be treated as "effectively infinite" - standard practice is to use -1 for this.
     */
    int getCapacity(ItemStack stack);

    /**
     * @return The item's currently stored charge amount.
     * @apiNote A negative value should be treated as "effectively infinite" - again, use -1.
     */
    int getStoredCharge(ItemStack stack);

    /**
     * Updates the item's stored charge value.
     * @param delta How much to change the charge by - negative values reduce charge
     * @return The previously-stored charge amount.
     * @apiNote Implementations should return -1 if {@link Spellcaster#getCapacity(ItemStack)} also returns a negative value.
     */
    int updateStoredCharge(int delta,ItemStack stack);
    
    default Spell getEquippedSpell() {
        return MagicMissileSpell.INSTANCE;
    }

    /**
     * Casts a spell.
     * @param caster The player casting this spell
     * @return If the cast was successful
     */
    boolean cast(Entity caster);
}
