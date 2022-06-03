package net.lilydev.aonia.api.item;

import net.lilydev.aonia.api.spell.SpellDescription;
import net.lilydev.aonia.impl.entity.BulletEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public abstract class AonianFirearmItem extends AonianRangedSpellExecutorItem {
    public AonianFirearmItem() {
        super(new Item.Settings().maxCount(1));
    }

    public boolean isLoaded(ItemStack stack) {
        NbtCompound data = stack.getOrCreateSubNbt("FirearmData");
        return data.getInt("BulletsCurrent") > 0;
    }

    public int getBulletsLoaded(ItemStack stack) {
        NbtCompound data = stack.getOrCreateSubNbt("FirearmData");
        return data.getInt("BulletsCurrent");
    }

    public int getBulletCapacity(ItemStack stack) {
        NbtCompound data = stack.getOrCreateSubNbt("FirearmData");
        return data.getInt("BulletsMax");
    }

    public void loadBullet(ItemStack stack) {
        NbtCompound data = stack.getOrCreateSubNbt("FirearmData");
        int current = data.getInt("BulletsCurrent");
        data.putInt("BulletsCurrent", ++current);
    }

    public void unloadBullet(ItemStack stack) {
        NbtCompound data = stack.getOrCreateSubNbt("FirearmData");
        int current = data.getInt("BulletsCurrent");
        data.putInt("BulletsCurrent", --current);
    }

    public abstract float getDamage();

    // ticks per shot
    public abstract int getFireRate();

    @Override
    public int getCapacity(ItemStack stack) {
        return -1;
    }

    @Override
    public int getStoredCharge(ItemStack stack) {
        return -1;
    }

    @Override
    public int updateStoredCharge(int delta, ItemStack stack) {
        return -1;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (!this.isLoaded(user.getStackInHand(hand))) {
            return TypedActionResult.fail(user.getStackInHand(hand));
        }

        NbtCompound data = user.getStackInHand(hand).getOrCreateSubNbt("FirearmData");
        NbtCompound bullet = data.getCompound("LoadedBulletData");

        BulletEntity entity;

        if (bullet.getBoolean("HasSpellAttached")) {
            SpellDescription spell = SpellDescription.deserialize(bullet.getCompound("AttachedSpell"));
            entity = new BulletEntity(world, user.getX(), user.getY(), user.getZ(), user, spell, this);
        } else {
            entity = new BulletEntity(world, user.getX(), user.getY(), user.getZ(), user, this);
        }

        world.spawnEntity(entity);
        user.getItemCooldownManager().set(this, this.getFireRate());
        this.unloadBullet(user.getStackInHand(hand));
        return TypedActionResult.consume(user.getStackInHand(hand));
    }

    @Override
    public Item getPolymerItem(ItemStack itemStack, @Nullable ServerPlayerEntity player) {
        return Items.BOW;
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        super.inventoryTick(stack, world, entity, slot, selected);
        stack.getOrCreateNbt();

        if (this.getBulletCapacity(stack) == 0) {
            stack.getOrCreateSubNbt("FirearmData").putInt("BulletsMax", 6);
            stack.getOrCreateSubNbt("FirearmData").putInt("BulletsCurrent", 6);
        }

        if (this.getBulletsLoaded(stack) <= -1) {
            stack.getOrCreateSubNbt("FirearmData").putInt("BulletsCurrent", 0);
        }
    }
}
