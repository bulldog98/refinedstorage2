package com.refinedmods.refinedstorage2.platform.api.grid;

import com.refinedmods.refinedstorage2.api.grid.service.GridInsertMode;

import org.apiguardian.api.API;

@API(status = API.Status.STABLE, since = "2.0.0-milestone.2.6")
public interface GridInsertionStrategy {
    boolean onInsert(GridInsertMode insertMode, boolean tryAlternatives);

    boolean onTransfer(int slotIndex);
}
