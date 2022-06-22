package com.refinedmods.refinedstorage2.platform.common.item.block;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class GridBlockItem extends NameableBlockItem {
    public GridBlockItem(Block block, CreativeModeTab tab, Component name) {
        super(block, new Item.Properties().tab(tab), name);
    }
}
