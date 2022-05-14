package net.lilydev.aonia.api.component;

import dev.onyxstudios.cca.api.v3.component.ComponentProvider;
import dev.onyxstudios.cca.api.v3.item.ItemComponent;
import net.lilydev.aonia.Aonia;
import net.minecraft.item.ItemStack;

public class WandDataComponent extends ItemComponent {
    public WandDataComponent(ItemStack stack) {
        super(stack);
    }

    @SuppressWarnings({"removal", "UnstableApiUsage"})
    public static WandDataComponent of(ItemStack stack) {
        return Aonia.WAND_DATA.get(ComponentProvider.fromItemStack(stack));
    }
}
