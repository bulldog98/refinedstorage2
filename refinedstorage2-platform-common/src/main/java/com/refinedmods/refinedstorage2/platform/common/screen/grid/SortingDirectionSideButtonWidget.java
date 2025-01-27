package com.refinedmods.refinedstorage2.platform.common.screen.grid;

import com.refinedmods.refinedstorage2.api.grid.view.GridSortingDirection;
import com.refinedmods.refinedstorage2.platform.common.containermenu.GridContainerMenu;
import com.refinedmods.refinedstorage2.platform.common.screen.TooltipRenderer;
import com.refinedmods.refinedstorage2.platform.common.screen.widget.AbstractSideButtonWidget;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;

import static com.refinedmods.refinedstorage2.platform.common.util.IdentifierUtil.createTranslation;

public class SortingDirectionSideButtonWidget extends AbstractSideButtonWidget {
    private final GridContainerMenu menu;
    private final TooltipRenderer tooltipRenderer;
    private final Map<GridSortingDirection, List<Component>> tooltips = new EnumMap<>(GridSortingDirection.class);

    public SortingDirectionSideButtonWidget(final GridContainerMenu menu,
                                            final TooltipRenderer tooltipRenderer) {
        super(createPressAction(menu));
        this.menu = menu;
        this.tooltipRenderer = tooltipRenderer;
        Arrays.stream(GridSortingDirection.values()).forEach(type -> tooltips.put(type, calculateTooltip(type)));
    }

    private static OnPress createPressAction(final GridContainerMenu menu) {
        return btn -> menu.setSortingDirection(toggle(menu.getSortingDirection()));
    }

    private static GridSortingDirection toggle(final GridSortingDirection sortingDirection) {
        return sortingDirection == GridSortingDirection.ASCENDING
            ? GridSortingDirection.DESCENDING
            : GridSortingDirection.ASCENDING;
    }

    private List<Component> calculateTooltip(final GridSortingDirection type) {
        final List<Component> lines = new ArrayList<>();
        lines.add(createTranslation("gui", "grid.sorting.direction"));
        lines.add(createTranslation(
            "gui",
            "grid.sorting.direction." + type.toString().toLowerCase(Locale.ROOT)
        ).withStyle(ChatFormatting.GRAY));
        return lines;
    }

    @Override
    protected int getXTexture() {
        return menu.getSortingDirection() == GridSortingDirection.ASCENDING ? 0 : 16;
    }

    @Override
    protected int getYTexture() {
        return 16;
    }

    @Override
    public void onTooltip(final PoseStack poseStack, final int mouseX, final int mouseY) {
        tooltipRenderer.render(poseStack, tooltips.get(menu.getSortingDirection()), mouseX, mouseY);
    }
}
