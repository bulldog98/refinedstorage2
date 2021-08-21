package com.refinedmods.refinedstorage2.core.network.energy;

import com.refinedmods.refinedstorage2.api.core.Action;

public class CreativeEnergyStorage implements EnergyStorage {
    @Override
    public long getStored() {
        return Long.MAX_VALUE;
    }

    @Override
    public long getCapacity() {
        return Long.MAX_VALUE;
    }

    @Override
    public long receive(long amount, Action action) {
        return amount;
    }

    @Override
    public long extract(long amount, Action action) {
        return amount;
    }
}
