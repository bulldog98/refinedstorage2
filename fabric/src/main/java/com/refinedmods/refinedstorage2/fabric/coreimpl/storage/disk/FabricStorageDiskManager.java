package com.refinedmods.refinedstorage2.fabric.coreimpl.storage.disk;

import com.refinedmods.refinedstorage2.core.storage.disk.StorageDisk;
import com.refinedmods.refinedstorage2.core.storage.disk.StorageDiskInfo;
import com.refinedmods.refinedstorage2.core.storage.disk.StorageDiskManager;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.PersistentState;

import java.util.Optional;
import java.util.UUID;

public class FabricStorageDiskManager extends PersistentState implements StorageDiskManager {
    public static final String NAME = "refinedstorage2_disks";

    private final StorageDiskManager parent;

    public FabricStorageDiskManager(String name, StorageDiskManager parent) {
        super(name);
        this.parent = parent;
    }

    @Override
    public <T> Optional<StorageDisk<T>> getDisk(UUID id) {
        return parent.getDisk(id);
    }

    @Override
    public <T> void setDisk(UUID id, StorageDisk<T> storage) {
        parent.setDisk(id, storage);
    }

    @Override
    public StorageDiskInfo getInfo(UUID id) {
        return parent.getInfo(id);
    }

    @Override
    public void fromTag(CompoundTag tag) {

    }

    @Override
    public CompoundTag toTag(CompoundTag tag) {
        return tag;
    }
}
