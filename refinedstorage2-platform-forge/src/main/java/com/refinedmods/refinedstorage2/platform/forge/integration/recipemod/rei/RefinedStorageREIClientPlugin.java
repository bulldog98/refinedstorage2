package com.refinedmods.refinedstorage2.platform.forge.integration.recipemod.rei;

import com.refinedmods.refinedstorage2.platform.api.PlatformApi;
import com.refinedmods.refinedstorage2.platform.api.integration.recipemod.IngredientConverter;
import com.refinedmods.refinedstorage2.platform.common.block.ColorableBlock;
import com.refinedmods.refinedstorage2.platform.common.content.BlockColorMap;
import com.refinedmods.refinedstorage2.platform.common.content.Blocks;
import com.refinedmods.refinedstorage2.platform.common.content.ContentIds;
import com.refinedmods.refinedstorage2.platform.common.content.Tags;

import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.api.client.registry.entry.CollapsibleEntryRegistry;
import me.shedaniel.rei.api.client.registry.screen.ScreenRegistry;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import me.shedaniel.rei.forge.REIPluginClient;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

@REIPluginClient
public class RefinedStorageREIClientPlugin implements REIClientPlugin {
    @Override
    public void registerScreens(final ScreenRegistry registry) {
        final IngredientConverter converter = PlatformApi.INSTANCE.getIngredientConverter();
        registry.registerFocusedStack(new GridFocusedStackProvider(converter));
        registry.registerFocusedStack(new FilteredResourceFocusedStackProvider(converter));
    }

    public static void registerIngredientConverters() {
        PlatformApi.INSTANCE.registerIngredientConverter(new GridResourceIngredientConverter());
        PlatformApi.INSTANCE.registerIngredientConverter(new FilteredResourceIngredientConverter());
    }

    @SuppressWarnings("UnstableApiUsage")
    @Override
    public void registerCollapsibleEntries(final CollapsibleEntryRegistry registry) {
        groupItems(registry, Blocks.INSTANCE.getCable(), DyeColor.GRAY, ContentIds.CABLE, Tags.CABLES);
        groupItems(registry, Blocks.INSTANCE.getGrid(), DyeColor.LIGHT_BLUE, ContentIds.GRID, Tags.GRIDS);
        groupItems(registry, Blocks.INSTANCE.getCraftingGrid(), DyeColor.LIGHT_BLUE,
            ContentIds.CRAFTING_GRID, Tags.CRAFTING_GRIDS);
        groupItems(registry, Blocks.INSTANCE.getImporter(), DyeColor.GRAY, ContentIds.IMPORTER, Tags.IMPORTERS);
        groupItems(registry, Blocks.INSTANCE.getExporter(), DyeColor.GRAY, ContentIds.EXPORTER, Tags.EXPORTERS);
        groupItems(registry, Blocks.INSTANCE.getExternalStorage(), DyeColor.GRAY,
            ContentIds.EXTERNAL_STORAGE, Tags.EXTERNAL_STORAGES);
        groupItems(registry, Blocks.INSTANCE.getController(), DyeColor.LIGHT_BLUE,
            ContentIds.CONTROLLER, Tags.CONTROLLERS);
        groupItems(registry, Blocks.INSTANCE.getCreativeController(), DyeColor.LIGHT_BLUE,
            ContentIds.CREATIVE_CONTROLLER, Tags.CREATIVE_CONTROLLERS);
    }

    @SuppressWarnings("UnstableApiUsage")
    private static void groupItems(
        final CollapsibleEntryRegistry registry,
        final BlockColorMap<? extends ColorableBlock<? extends Block>> blocks,
        final DyeColor defaultColor,
        final ResourceLocation itemIdentifier,
        final TagKey<Item> tag
    ) {
        registry.group(
            blocks.getId(defaultColor, itemIdentifier),
            blocks.get(defaultColor).getName(),
            EntryIngredients.ofItemTag(tag)
        );
    }
}
