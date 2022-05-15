package net.lilydev.aonia;

import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistry;
import dev.onyxstudios.cca.api.v3.item.ItemComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.item.ItemComponentInitializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.lilydev.aonia.api.item.WandDataWrapper;
import net.lilydev.aonia.api.item.Wand;
import net.lilydev.aonia.impl.block.LandmineBlockEntity;
import net.lilydev.aonia.util.AoniaItems;
import net.minecraft.block.entity.BlockEntityType;
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

    @Override
    public void onInitialize() {
        LANDMINE_BLENT = Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier("aonia", "landmine"), FabricBlockEntityTypeBuilder.create(LandmineBlockEntity::new).build());
        Registry.register(Registry.ITEM, new Identifier("aonia", "wand"), AoniaItems.WAND);
        DimensionType.OVERWORLD = DimensionType.create(OptionalLong.empty(), true, false, false, true, 1.0, false, false, true, false, false, -64, 1088, 1088, BlockTags.INFINIBURN_OVERWORLD, DimensionType.OVERWORLD_ID, 0.0f);
    }
}
