package com.refinedmods.refinedstorage2.fabric.init;

import com.refinedmods.refinedstorage2.fabric.Rs2Mod;
import com.refinedmods.refinedstorage2.fabric.screenhandler.diskdrive.DiskDriveScreenHandler;
import com.refinedmods.refinedstorage2.fabric.screenhandler.grid.GridScreenHandler;

import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.screen.ScreenHandlerType;

public class Rs2ScreenHandlers {
    private ScreenHandlerType<DiskDriveScreenHandler> diskDrive;
    private ScreenHandlerType<GridScreenHandler> grid;

    public void register() {
        diskDrive = ScreenHandlerRegistry.registerSimple(Rs2Mod.createIdentifier("disk_drive"), DiskDriveScreenHandler::new);
        grid = ScreenHandlerRegistry.registerExtended(Rs2Mod.createIdentifier("grid"), GridScreenHandler::new);
    }

    public ScreenHandlerType<DiskDriveScreenHandler> getDiskDrive() {
        return diskDrive;
    }

    public ScreenHandlerType<GridScreenHandler> getGrid() {
        return grid;
    }
}