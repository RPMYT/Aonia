package net.lilydev.aonia.impl.item;

import net.lilydev.aonia.api.item.AonianFirearmItem;
import net.minecraft.item.ItemStack;

public final class RevolverItem extends AonianFirearmItem {
    @Override
    public float getDamage() {
        return 6.0F;
    }

    @Override
    public int getFireRate() {
        return 12;
    }

    @Override
    public boolean isLoaded(ItemStack stack) {
        return true;
    }
}
