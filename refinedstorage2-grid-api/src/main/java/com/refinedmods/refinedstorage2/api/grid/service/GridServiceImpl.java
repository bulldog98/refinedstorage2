package com.refinedmods.refinedstorage2.api.grid.service;

import com.refinedmods.refinedstorage2.api.core.Action;
import com.refinedmods.refinedstorage2.api.resource.ResourceAmount;
import com.refinedmods.refinedstorage2.api.storage.ExtractableStorage;
import com.refinedmods.refinedstorage2.api.storage.InsertableStorage;
import com.refinedmods.refinedstorage2.api.storage.Source;
import com.refinedmods.refinedstorage2.api.storage.channel.StorageChannel;

import java.util.function.Function;

import org.apiguardian.api.API;

@API(status = API.Status.STABLE, since = "2.0.0-milestone.1.2")
public class GridServiceImpl<T> implements GridService<T> {
    private final StorageChannel<T> storageChannel;
    private final Source source;
    private final Function<T, Long> maxCountProvider;
    private final long singleAmount;

    /**
     * @param storageChannel   the storage channel to act on
     * @param source           the source performing the grid interactions
     * @param maxCountProvider provider for the maximum amount of a given resource
     * @param singleAmount     amount that needs to be extracted when using {@link GridInsertMode#SINGLE_RESOURCE} or {@link GridExtractMode#SINGLE_RESOURCE}
     */
    public GridServiceImpl(StorageChannel<T> storageChannel, Source source, Function<T, Long> maxCountProvider, long singleAmount) {
        this.storageChannel = storageChannel;
        this.source = source;
        this.maxCountProvider = maxCountProvider;
        this.singleAmount = singleAmount;
    }

    @Override
    public void extract(T resource, GridExtractMode extractMode, InsertableStorage<T> destination) {
        long amount = getExtractableAmount(resource, extractMode);
        if (amount == 0) {
            return;
        }
        long extractedFromSource = storageChannel.extract(resource, amount, Action.SIMULATE, this.source);
        if (extractedFromSource == 0) {
            return;
        }
        long amountInsertedIntoDestination = destination.insert(resource, extractedFromSource, Action.SIMULATE, this.source);
        if (amountInsertedIntoDestination > 0) {
            extractedFromSource = storageChannel.extract(resource, amountInsertedIntoDestination, source);
            destination.insert(resource, extractedFromSource, Action.EXECUTE, this.source);
        }
    }

    private long getExtractableAmount(T resource, GridExtractMode extractMode) {
        long extractableAmount = getExtractableAmount(resource);
        return switch (extractMode) {
            case ENTIRE_RESOURCE -> extractableAmount;
            case HALF_RESOURCE -> extractableAmount == 1 ? 1 : extractableAmount / 2;
            case SINGLE_RESOURCE -> Math.min(singleAmount, extractableAmount);
        };
    }

    private long getExtractableAmount(T resource) {
        long maxCount = maxCountProvider.apply(resource);
        long totalSize = storageChannel.get(resource).map(ResourceAmount::getAmount).orElse(0L);
        return Math.min(maxCount, totalSize);
    }

    @Override
    public void insert(T resource, GridInsertMode insertMode, ExtractableStorage<T> source) {
        long amount = switch (insertMode) {
            case ENTIRE_RESOURCE -> maxCountProvider.apply(resource);
            case SINGLE_RESOURCE -> singleAmount;
        };
        long extractedFromSource = source.extract(resource, amount, Action.SIMULATE, this.source);
        if (extractedFromSource == 0) {
            return;
        }
        long amountInsertedIntoDestination = storageChannel.insert(resource, extractedFromSource, Action.SIMULATE, this.source);
        if (amountInsertedIntoDestination > 0) {
            extractedFromSource = source.extract(resource, amountInsertedIntoDestination, Action.EXECUTE, this.source);
            if (extractedFromSource > 0) {
                storageChannel.insert(resource, extractedFromSource, this.source);
            }
        }
    }
}
