package com.refinedmods.refinedstorage2.platform.common.item.block;

import com.refinedmods.refinedstorage2.platform.api.item.block.StorageBlockItem;
import com.refinedmods.refinedstorage2.platform.common.block.entity.storage.StorageBlockEntity;
import com.refinedmods.refinedstorage2.platform.common.content.Blocks;
import com.refinedmods.refinedstorage2.platform.common.content.Items;
import com.refinedmods.refinedstorage2.platform.common.internal.storage.type.ItemStorageType;

import java.util.UUID;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ItemStorageBlockItem extends StorageBlockItem {
    private static final Logger LOGGER = LogManager.getLogger();

    private final ItemStorageType.Variant variant;

    public ItemStorageBlockItem(Block block, Properties properties, ItemStorageType.Variant variant) {
        super(block, properties);
        this.variant = variant;
    }

    @Override
    protected ItemStack createPrimaryDisassemblyByproduct(int count) {
        return new ItemStack(Blocks.INSTANCE.getMachineCasing(), count);
    }

    @Override
    protected ItemStack createSecondaryDisassemblyByproduct(int count) {
        if (variant == ItemStorageType.Variant.CREATIVE) {
            return null;
        }
        return new ItemStack(Items.INSTANCE.getStoragePart(variant), count);
    }

    @Override
    protected void updateBlockEntityWithStorageId(BlockPos pos, BlockEntity blockEntity, UUID id) {
        if (blockEntity instanceof StorageBlockEntity<?> storageBlockEntity) {
            LOGGER.info("Transferred storage {} to block at {}", id, pos);
            storageBlockEntity.modifyStorageIdAfterAlreadyInitialized(id);
        } else {
            LOGGER.warn("Storage {} could not be set, block entity does not exist yet at {}", id, pos);
        }
    }
}
