package com.refinedmods.refinedstorage2.platform.common;

import com.refinedmods.refinedstorage2.api.network.component.EnergyNetworkComponent;
import com.refinedmods.refinedstorage2.api.network.component.StorageNetworkComponent;
import com.refinedmods.refinedstorage2.api.network.impl.component.EnergyNetworkComponentImpl;
import com.refinedmods.refinedstorage2.api.network.impl.component.GraphNetworkComponent;
import com.refinedmods.refinedstorage2.api.network.impl.component.StorageNetworkComponentImpl;
import com.refinedmods.refinedstorage2.platform.api.PlatformApi;
import com.refinedmods.refinedstorage2.platform.api.PlatformApiProxy;
import com.refinedmods.refinedstorage2.platform.common.internal.resource.filter.fluid.FluidFilteredResourceFactory;
import com.refinedmods.refinedstorage2.platform.common.internal.storage.channel.StorageChannelTypes;
import com.refinedmods.refinedstorage2.platform.common.internal.storage.type.FluidStorageType;
import com.refinedmods.refinedstorage2.platform.common.internal.upgrade.UpgradeDestinations;

import java.util.function.Supplier;

import net.minecraft.world.item.Item;

import static com.refinedmods.refinedstorage2.platform.common.util.IdentifierUtil.createIdentifier;

public abstract class AbstractModInitializer {
    private static final String FLUID_REGISTRY_KEY = "fluid";

    protected void initializePlatform(final Platform platform) {
        ((PlatformProxy) Platform.INSTANCE).setPlatform(platform);
    }

    protected void initializePlatformApi() {
        ((PlatformApiProxy) PlatformApi.INSTANCE).setDelegate(new PlatformApiImpl());
    }

    protected void registerAdditionalStorageTypes() {
        PlatformApi.INSTANCE.getStorageTypeRegistry().register(
            createIdentifier(FLUID_REGISTRY_KEY),
            FluidStorageType.INSTANCE
        );
    }

    protected void registerAdditionalStorageChannelTypes() {
        PlatformApi.INSTANCE.getStorageChannelTypeRegistry().register(
            createIdentifier(FLUID_REGISTRY_KEY),
            StorageChannelTypes.FLUID
        );
    }

    protected void registerAdditionalFilteredResourceFactories() {
        PlatformApi.INSTANCE.addFilteredResourceFactory(new FluidFilteredResourceFactory());
    }

    protected void registerNetworkComponents() {
        PlatformApi.INSTANCE.getNetworkComponentMapFactory().addFactory(
            EnergyNetworkComponent.class,
            network -> new EnergyNetworkComponentImpl()
        );
        PlatformApi.INSTANCE.getNetworkComponentMapFactory().addFactory(
            GraphNetworkComponent.class,
            GraphNetworkComponent::new
        );
        PlatformApi.INSTANCE.getNetworkComponentMapFactory().addFactory(
            StorageNetworkComponent.class,
            network -> new StorageNetworkComponentImpl(
                PlatformApi.INSTANCE.getStorageChannelTypeRegistry().getAll()
            )
        );
    }

    protected void addApplicableUpgrades(final Supplier<Item> speedUpgrade,
                                         final Supplier<Item> stackUpgrade) {
        PlatformApi.INSTANCE.getUpgradeRegistry().addApplicableUpgrade(
            UpgradeDestinations.IMPORTER,
            speedUpgrade,
            4
        );
        PlatformApi.INSTANCE.getUpgradeRegistry().addApplicableUpgrade(
            UpgradeDestinations.IMPORTER,
            stackUpgrade,
            1
        );
        PlatformApi.INSTANCE.getUpgradeRegistry().addApplicableUpgrade(
            UpgradeDestinations.EXPORTER,
            speedUpgrade,
            4
        );
        PlatformApi.INSTANCE.getUpgradeRegistry().addApplicableUpgrade(
            UpgradeDestinations.EXPORTER,
            stackUpgrade,
            1
        );
    }
}
