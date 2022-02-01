package me.alekseinovikov.qts.queue;

import me.alekseinovikov.qts.persistence.model.Timestamp;

public interface TimestampQueue {
    /**
     * Adds timestamp to queue for processing
     *
     * @param timestamp
     */
    void put(final Timestamp timestamp);

    /**
     * Gets timestamp from queue for processing
     * Blocks if there's no timestamps until present
     *
     * @return timestamp
     */
    Timestamp get();
}
