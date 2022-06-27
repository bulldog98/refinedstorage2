package com.refinedmods.refinedstorage2.platform.fabric.packet.c2s;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.world.inventory.AbstractContainerMenu;

public class PropertyChangePacket implements ServerPlayNetworking.PlayChannelHandler {
    @Override
    public void receive(final MinecraftServer server, final ServerPlayer player, final ServerGamePacketListenerImpl handler, final FriendlyByteBuf buf, final PacketSender responseSender) {
        final int id = buf.readInt();
        final int value = buf.readInt();

        server.execute(() -> {
            final AbstractContainerMenu menu = player.containerMenu;
            if (menu != null) {
                // TODO - Check property type
                menu.setData(id, value);
            }
        });
    }
}
