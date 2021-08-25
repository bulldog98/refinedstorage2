package com.refinedmods.refinedstorage2.platform.fabric.screen.grid;

import com.refinedmods.refinedstorage2.api.grid.view.GridSortingType;
import com.refinedmods.refinedstorage2.platform.fabric.Rs2Mod;
import com.refinedmods.refinedstorage2.platform.fabric.screen.TooltipRenderer;
import com.refinedmods.refinedstorage2.platform.fabric.screen.widget.SideButtonWidget;
import com.refinedmods.refinedstorage2.platform.fabric.screenhandler.grid.GridScreenHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class SortingTypeSideButtonWidget extends SideButtonWidget {
    private final GridScreenHandler screenHandler;
    private final TooltipRenderer tooltipRenderer;
    private final Map<GridSortingType, List<Text>> tooltips = new EnumMap<>(GridSortingType.class);

    public SortingTypeSideButtonWidget(GridScreenHandler screenHandler, TooltipRenderer tooltipRenderer) {
        super(createPressAction(screenHandler));
        this.screenHandler = screenHandler;
        this.tooltipRenderer = tooltipRenderer;
        Arrays.stream(GridSortingType.values()).forEach(type -> tooltips.put(type, calculateTooltip(type)));
    }

    private static PressAction createPressAction(GridScreenHandler screenHandler) {
        return btn -> screenHandler.setSortingType(screenHandler.getSortingType().toggle());
    }

    private List<Text> calculateTooltip(GridSortingType type) {
        List<Text> lines = new ArrayList<>();
        lines.add(Rs2Mod.createTranslation("gui", "grid.sorting.type"));
        lines.add(Rs2Mod.createTranslation("gui", "grid.sorting.type." + type.toString().toLowerCase(Locale.ROOT)).formatted(Formatting.GRAY));
        return lines;
    }

    @Override
    protected int getXTexture() {
        return switch (screenHandler.getSortingType()) {
            case QUANTITY -> 0;
            case NAME -> 16;
            case ID -> 32;
            case LAST_MODIFIED -> 48;
        };
    }

    @Override
    protected int getYTexture() {
        return screenHandler.getSortingType() == GridSortingType.LAST_MODIFIED ? 48 : 32;
    }

    @Override
    public void onTooltip(ButtonWidget buttonWidget, MatrixStack matrixStack, int mouseX, int mouseY) {
        tooltipRenderer.render(matrixStack, tooltips.get(screenHandler.getSortingType()), mouseX, mouseY);
    }
}