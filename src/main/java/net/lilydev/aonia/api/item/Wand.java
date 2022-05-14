package net.lilydev.aonia.api.item;

import eu.pb4.polymer.api.item.PolymerItem;
import net.lilydev.aonia.api.spell.SpellDescription;
import net.lilydev.aonia.impl.spell.MagicMissileSpell;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.network.ServerPlayerEntity;
import org.jetbrains.annotations.Nullable;

import java.util.function.Predicate;

public interface Wand extends PolymerItem {
    static boolean isWand(Item item) {
        return item instanceof Wand;
    }

    default SpellDescription getEquippedSpell() {
        return MagicMissileSpell.INSTANCE;
    }

    default void cast(ServerPlayerEntity caster) {
        getEquippedSpell().execute(caster);
    }

    @Override
    default Item getPolymerItem(ItemStack itemStack, @Nullable ServerPlayerEntity player) {
        return Items.STICK;
    }
}
