package com.refinedmods.refinedstorage2.platform.common.screen.widget;

import com.refinedmods.refinedstorage2.platform.common.containermenu.property.ClientProperty;
import com.refinedmods.refinedstorage2.platform.common.screen.TooltipRenderer;
import com.refinedmods.refinedstorage2.platform.common.util.RedstoneMode;

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

public class RedstoneModeSideButtonWidget extends AbstractSideButtonWidget {
    private final Map<RedstoneMode, List<Component>> tooltips = new EnumMap<>(RedstoneMode.class);
    private final TooltipRenderer tooltipRenderer;
    private final ClientProperty<RedstoneMode> property;

    public RedstoneModeSideButtonWidget(final ClientProperty<RedstoneMode> property,
                                        final TooltipRenderer tooltipRenderer) {
        super(createPressAction(property));
        this.tooltipRenderer = tooltipRenderer;
        this.property = property;
        Arrays.stream(RedstoneMode.values()).forEach(type -> tooltips.put(type, calculateTooltip(type)));
    }

    private static OnPress createPressAction(final ClientProperty<RedstoneMode> property) {
        return btn -> property.setValue(property.getValue().toggle());
    }

    private List<Component> calculateTooltip(final RedstoneMode type) {
        final List<Component> lines = new ArrayList<>();
        lines.add(createTranslation("gui", "redstone_mode"));
        lines.add(createTranslation("gui", "redstone_mode." + type.toString().toLowerCase(Locale.ROOT))
            .withStyle(ChatFormatting.GRAY));
        return lines;
    }

    @Override
    protected int getXTexture() {
        return switch (property.getValue()) {
            case IGNORE -> 0;
            case HIGH -> 16;
            case LOW -> 32;
        };
    }

    @Override
    protected int getYTexture() {
        return 0;
    }

    @Override
    public void onTooltip(final PoseStack poseStack, final int mouseX, final int mouseY) {
        tooltipRenderer.render(poseStack, tooltips.get(property.getValue()), mouseX, mouseY);
    }
}
