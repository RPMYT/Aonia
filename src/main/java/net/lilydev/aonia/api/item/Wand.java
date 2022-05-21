package net.lilydev.aonia.api.item;

import eu.pb4.polymer.api.item.PolymerItem;
import net.lilydev.aonia.api.spell.Spell;
import net.lilydev.aonia.impl.spell.MagicMissileSpell;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.TranslatableText;
import org.jetbrains.annotations.Nullable;

public interface Wand {
    default Spell getEquippedSpell() {
        return MagicMissileSpell.INSTANCE;
    }

    default boolean cast(ServerPlayerEntity caster) {
        WandDataWrapper wrapper =  new WandDataWrapper(caster.getActiveItem());

        if (wrapper.getStoredCharge() >= getEquippedSpell().getRequiredCharge()) {
            getEquippedSpell().execute(caster);
            return true;
        } else {
            caster.sendMessage(new TranslatableText("aonia.not_enough_charge"), true);
            return false;
        }
    }
}
