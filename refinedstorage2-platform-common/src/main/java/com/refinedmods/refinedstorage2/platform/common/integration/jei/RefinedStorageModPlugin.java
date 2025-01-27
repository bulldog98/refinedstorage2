package com.refinedmods.refinedstorage2.platform.common.integration.jei;

import javax.annotation.Nullable;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.runtime.IJeiRuntime;
import net.minecraft.resources.ResourceLocation;

import static com.refinedmods.refinedstorage2.platform.common.util.IdentifierUtil.createIdentifier;

@JeiPlugin
public class RefinedStorageModPlugin implements IModPlugin {
    private static final ResourceLocation ID = createIdentifier("plugin");

    @Nullable
    private static IJeiRuntime runtime;

    @Override
    public ResourceLocation getPluginUid() {
        return ID;
    }

    @Override
    public void onRuntimeAvailable(final IJeiRuntime newRuntime) {
        RefinedStorageModPlugin.runtime = newRuntime;
    }

    @Nullable
    public static IJeiRuntime getRuntime() {
        return runtime;
    }
}
