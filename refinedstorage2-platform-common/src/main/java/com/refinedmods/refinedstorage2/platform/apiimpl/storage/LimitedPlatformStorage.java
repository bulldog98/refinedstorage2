package com.refinedmods.refinedstorage2.platform.apiimpl.storage;

import com.refinedmods.refinedstorage2.api.storage.limited.LimitedStorage;
import com.refinedmods.refinedstorage2.api.storage.limited.LimitedStorageImpl;
import com.refinedmods.refinedstorage2.api.storage.tracked.TrackedStorageRepository;
import com.refinedmods.refinedstorage2.platform.api.storage.type.StorageType;

public class LimitedPlatformStorage<T> extends PlatformStorage<T> implements LimitedStorage<T> {
    private final LimitedStorageImpl<T> limitedStorage;

    public LimitedPlatformStorage(LimitedStorageImpl<T> delegate, StorageType<T> type, TrackedStorageRepository<T> trackingRepository, Runnable listener) {
        super(delegate, type, trackingRepository, listener);
        this.limitedStorage = delegate;
    }

    @Override
    public long getCapacity() {
        return limitedStorage.getCapacity();
    }
}
