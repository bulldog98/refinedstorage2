package com.refinedmods.refinedstorage2.platform.forge.packet.c2s;

import com.refinedmods.refinedstorage2.api.grid.service.GridExtractMode;
import com.refinedmods.refinedstorage2.api.grid.service.GridInsertMode;
import com.refinedmods.refinedstorage2.platform.api.PlatformApi;
import com.refinedmods.refinedstorage2.platform.api.grid.GridScrollMode;
import com.refinedmods.refinedstorage2.platform.api.storage.channel.PlatformStorageChannelType;
import com.refinedmods.refinedstorage2.platform.common.containermenu.property.PropertyType;
import com.refinedmods.refinedstorage2.platform.common.packet.ClientToServerCommunications;
import com.refinedmods.refinedstorage2.platform.forge.packet.NetworkManager;

import java.util.UUID;

public class ClientToServerCommunicationsImpl implements ClientToServerCommunications {
    private final NetworkManager networkManager;

    public ClientToServerCommunicationsImpl(final NetworkManager networkManager) {
        this.networkManager = networkManager;
    }

    @Override
    public <T> void sendGridExtract(final PlatformStorageChannelType<T> storageChannelType,
                                    final T resource,
                                    final GridExtractMode mode,
                                    final boolean cursor) {
        PlatformApi.INSTANCE.getStorageChannelTypeRegistry()
            .getId(storageChannelType)
            .ifPresent(id -> networkManager.send(new GridExtractPacket<>(
                storageChannelType,
                id,
                resource,
                mode,
                cursor
            )));
    }

    @Override
    public <T> void sendGridScroll(final PlatformStorageChannelType<T> storageChannelType,
                                   final T resource,
                                   final GridScrollMode mode,
                                   final int slotIndex) {
        PlatformApi.INSTANCE.getStorageChannelTypeRegistry()
            .getId(storageChannelType)
            .ifPresent(id -> networkManager.send(new GridScrollPacket<>(
                storageChannelType,
                id,
                resource,
                mode,
                slotIndex
            )));
    }

    @Override
    public void sendGridInsert(final GridInsertMode mode, final boolean tryAlternatives) {
        networkManager.send(new GridInsertPacket(mode == GridInsertMode.SINGLE_RESOURCE, tryAlternatives));
    }

    @Override
    public <T> void sendPropertyChange(final PropertyType<T> type, final T value) {
        networkManager.send(new PropertyChangePacket(type.id(), type.serializer().apply(value)));
    }

    @Override
    public void sendStorageInfoRequest(final UUID storageId) {
        networkManager.send(new StorageInfoRequestPacket(storageId));
    }

    @Override
    public void sendResourceFilterSlotChange(final int slotIndex, final boolean tryAlternatives) {
        networkManager.send(new ResourceFilterSlotChangePacket(slotIndex, tryAlternatives));
    }

    @Override
    public void sendResourceFilterSlotAmountChange(final int slotIndex, final long amount) {
        networkManager.send(new ResourceFilterSlotAmountChangePacket(slotIndex, amount));
    }
}
