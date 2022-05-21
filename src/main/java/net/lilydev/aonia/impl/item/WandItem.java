package net.lilydev.aonia.impl.item;

import eu.pb4.polymer.api.item.PolymerItem;
import net.lilydev.aonia.api.item.Wand;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public final class WandItem extends Item implements Wand, PolymerItem {
    public WandItem() {
        super(new Item.Settings().maxCount(1));
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        return this.cast((ServerPlayerEntity) user) ? TypedActionResult.success(user.getStackInHand(hand), true) : TypedActionResult.fail(user.getStackInHand(hand));
    }

    @Override
    public Item getPolymerItem(ItemStack itemStack, @Nullable ServerPlayerEntity player) {
        return Items.STICK;
    }
}
