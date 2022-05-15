package net.lilydev.aonia.api.item;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;

public class WandDataWrapper {
    private final NbtCompound data;

    public WandDataWrapper(ItemStack stack) {
        this.data = stack.getOrCreateSubNbt("WandData");
    }

    /**
     * @return The attached wand's maximum charge capacity.
     */
    public int getCapacity() {
        return this.data.getInt("Capacity");
    }

    /**
     * @return The attached wand's currently stored charge amount.
     */
    public int getStoredCharge() {
        return this.data.getInt("StoredCharge");
    }

    /**
     * Updates the attached wand's stored charge value.
     * @param delta How much to change the charge by - negative values reduce charge
     * @return The previously-stored charge amount.
     */
    public int updateStoredCharge(int delta) {
        int previous = this.getStoredCharge();
        int after = previous + delta;
        this.data.putInt("StoredCharge", after);
        return previous;
    }
}
