package net.lilydev.aonia;

import eu.pb4.polymer.api.entity.PolymerEntityUtils;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.lilydev.aonia.impl.block.LandmineBlockEntity;
import net.lilydev.aonia.impl.entity.BulletEntity;
import net.lilydev.aonia.util.AoniaItems;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.dimension.DimensionType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.OptionalLong;

public class Aonia implements ModInitializer {
    public static final Logger LOGGER = LogManager.getLogger("Aonia");

    public static BlockEntityType<LandmineBlockEntity> LANDMINE_BLENT;
    public static final EntityType<BulletEntity> BULLET = Registry.register(
            Registry.ENTITY_TYPE,
            new Identifier("aonia", "bullet"),
            FabricEntityTypeBuilder.<BulletEntity>create(SpawnGroup.MISC, BulletEntity::new).dimensions(EntityDimensions.fixed(0.2f, 0.2f)).build()
    );

    @Override
    public void onInitialize() {
        LANDMINE_BLENT = Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier("aonia", "landmine"), FabricBlockEntityTypeBuilder.create(LandmineBlockEntity::new).build());
        Registry.register(Registry.ITEM, new Identifier("aonia", "revolver"), AoniaItems.REVOLVER);
        PolymerEntityUtils.registerType(BULLET);
        DimensionType.OVERWORLD = DimensionType.create(OptionalLong.empty(), true, false, false, true, 1.0, false, false, true, false, false, -64, 1088, 1088, BlockTags.INFINIBURN_OVERWORLD, DimensionType.OVERWORLD_ID, 0.0f);
    }
}
