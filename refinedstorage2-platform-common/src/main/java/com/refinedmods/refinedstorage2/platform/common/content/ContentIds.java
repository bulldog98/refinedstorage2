package com.refinedmods.refinedstorage2.platform.common.content;

import com.refinedmods.refinedstorage2.platform.common.item.FluidStorageDiskItem;
import com.refinedmods.refinedstorage2.platform.common.item.ItemStorageDiskItem;
import com.refinedmods.refinedstorage2.platform.common.item.ProcessorItem;

import net.minecraft.resources.ResourceLocation;

import static com.refinedmods.refinedstorage2.platform.common.util.IdentifierUtil.createIdentifier;

public final class ContentIds {
    public static final ResourceLocation DISK_DRIVE = createIdentifier("disk_drive");
    public static final ResourceLocation MACHINE_CASING = createIdentifier("machine_casing");
    public static final ResourceLocation CABLE = createIdentifier("cable");
    public static final ResourceLocation QUARTZ_ENRICHED_IRON_BLOCK = createIdentifier("quartz_enriched_iron_block");
    public static final ResourceLocation QUARTZ_ENRICHED_IRON = createIdentifier("quartz_enriched_iron");
    public static final ResourceLocation SILICON = createIdentifier("silicon");
    public static final ResourceLocation PROCESSOR_BINDING = createIdentifier("processor_binding");
    public static final ResourceLocation WRENCH = createIdentifier("wrench");
    public static final ResourceLocation STORAGE_HOUSING = createIdentifier("storage_housing");
    public static final ResourceLocation GRID = createIdentifier("grid");
    public static final ResourceLocation FLUID_GRID = createIdentifier("fluid_grid");
    public static final ResourceLocation CONTROLLER = createIdentifier("controller");
    public static final ResourceLocation CREATIVE_CONTROLLER = createIdentifier("creative_controller");
    public static final ResourceLocation CONSTRUCTION_CORE = createIdentifier("construction_core");
    public static final ResourceLocation DESTRUCTION_CORE = createIdentifier("destruction_core");

    public static ResourceLocation forItemStoragePart(ItemStorageDiskItem.ItemStorageType type) {
        return createIdentifier(type.getName() + "_storage_part");
    }

    public static ResourceLocation forFluidStoragePart(FluidStorageDiskItem.FluidStorageType type) {
        return createIdentifier(type.getName() + "_fluid_storage_part");
    }

    public static ResourceLocation forProcessor(ProcessorItem.Type type) {
        return createIdentifier(type.getName() + "_processor");
    }

    public static ResourceLocation forStorageDisk(ItemStorageDiskItem.ItemStorageType type) {
        return createIdentifier(type.getName() + "_storage_disk");
    }

    public static ResourceLocation forFluidStorageDisk(FluidStorageDiskItem.FluidStorageType type) {
        return createIdentifier(type.getName() + "_fluid_storage_disk");
    }

    private ContentIds() {
    }
}