package com.refinedmods.refinedstorage2.fabric;

import com.refinedmods.refinedstorage2.fabric.loot.ControllerLootFunction;

import net.minecraft.loot.function.LootFunctionType;
import net.minecraft.util.registry.Registry;

public class Rs2LootFunctions {
    private LootFunctionType controller;

    public void register() {
        controller = Registry.register(Registry.LOOT_FUNCTION_TYPE, Rs2Mod.createIdentifier("controller"), new LootFunctionType(new ControllerLootFunction.Serializer()));
    }

    public LootFunctionType getController() {
        return controller;
    }
}
