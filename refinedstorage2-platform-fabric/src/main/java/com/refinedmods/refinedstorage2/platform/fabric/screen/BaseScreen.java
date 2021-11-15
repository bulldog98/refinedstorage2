package com.refinedmods.refinedstorage2.platform.fabric.screen;

import com.refinedmods.refinedstorage2.platform.fabric.containermenu.slot.ResourceFilterSlot;
import com.refinedmods.refinedstorage2.platform.fabric.screen.widget.SideButtonWidget;

import java.util.List;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import org.lwjgl.opengl.GL11;

public abstract class BaseScreen<T extends AbstractContainerMenu> extends AbstractContainerScreen<T> {
    private int sideButtonY;

    protected BaseScreen(T menu, Inventory playerInventory, Component text) {
        super(menu, playerInventory, text);
    }

    @Override
    protected void init() {
        clearWidgets();
        super.init();
        sideButtonY = 6;
    }

    public void addSideButton(SideButtonWidget button) {
        button.x = leftPos - button.getWidth() - 2;
        button.y = topPos + sideButtonY;

        sideButtonY += button.getHeight() + 2;

        addRenderableWidget(button);
    }

    protected void setScissor(int x, int y, int w, int h) {
        double scale = minecraft.getWindow().getGuiScale();
        int sx = (int) (x * scale);
        int sy = (int) ((minecraft.getWindow().getGuiScaledHeight() - (y + h)) * scale);
        int sw = (int) (w * scale);
        int sh = (int) (h * scale);

        GL11.glEnable(GL11.GL_SCISSOR_TEST);
        GL11.glScissor(sx, sy, sw, sh);
    }

    protected void disableScissor() {
        GL11.glDisable(GL11.GL_SCISSOR_TEST);
    }

    @Override
    protected void renderTooltip(PoseStack matrices, int x, int y) {
        super.renderTooltip(matrices, x, y);
        if (menu.getCarried().isEmpty() && hoveredSlot instanceof ResourceFilterSlot resourceFilterSlot) {
            List<Component> lines = resourceFilterSlot.getTooltipLines();
            if (!lines.isEmpty()) {
                this.renderComponentTooltip(matrices, lines, x, y);
            }
        }
    }

    @Override
    protected void renderSlot(PoseStack poseStack, Slot slot) {
        if (slot instanceof ResourceFilterSlot resourceFilterSlot) {
            resourceFilterSlot.render(poseStack, slot.x, slot.y, getBlitOffset());
        } else {
            super.renderSlot(poseStack, slot);
        }
    }
}
