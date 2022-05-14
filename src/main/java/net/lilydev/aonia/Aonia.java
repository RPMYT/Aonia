package net.lilydev.aonia;

import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistry;
import dev.onyxstudios.cca.api.v3.item.ItemComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.item.ItemComponentInitializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.lilydev.aonia.api.component.WandDataComponent;
import net.lilydev.aonia.api.item.Wand;
import net.lilydev.aonia.impl.block.LandmineBlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.Item;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.dimension.DimensionType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.OptionalLong;
import java.util.function.Predicate;

public class Aonia implements ModInitializer, ItemComponentInitializer {
    public static final Logger LOGGER = LogManager.getLogger("Aonia");

    public static final ComponentKey<WandDataComponent> WAND_DATA =
            ComponentRegistry.getOrCreate(new Identifier("aonia", "wand_data"), WandDataComponent.class);

    public static BlockEntityType<LandmineBlockEntity> LANDMINE_BLENT;

    @Override
    public void onInitialize() {
        LANDMINE_BLENT = Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier("aonia", "landmine"), FabricBlockEntityTypeBuilder.create(LandmineBlockEntity::new).build());
        DimensionType.OVERWORLD = DimensionType.create(OptionalLong.empty(), true, false, false, true, 1.0, false, false, true, false, true, -640, 960, 960, BlockTags.INFINIBURN_OVERWORLD, DimensionType.OVERWORLD_ID, 0.0f);
    }

    @Override
    @SuppressWarnings("UnstableApiUsage")
    public void registerItemComponentFactories(ItemComponentFactoryRegistry registry) {
        registry.register(Wand::isWand, WAND_DATA, WandDataComponent::new);
    }
}
