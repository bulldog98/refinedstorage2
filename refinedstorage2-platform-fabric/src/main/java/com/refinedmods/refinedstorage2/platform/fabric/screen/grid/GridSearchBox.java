package com.refinedmods.refinedstorage2.platform.fabric.screen.grid;

import java.util.function.Consumer;

public interface GridSearchBox {
    void setAutoSelected(boolean autoSelected);

    void setText(String text);

    void setListener(Consumer<String> listener);

    void setInvalid(boolean invalid);
}