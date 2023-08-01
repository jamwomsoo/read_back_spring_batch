package com.example.daemonTest.job.reader;

import org.springframework.batch.item.ItemReader;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MultiItemReader<T> implements ItemReader<T>{
    private final List<ItemReader<T>> readers;
    private Iterator<T> currentReaderIterator;

    private MultiItemReader(List<ItemReader<T>> readers) {
        this.readers = readers;
    }

    public static <T> MultiItemReaderBuilder<T> builder() {
        return new MultiItemReaderBuilder<>();
    }

    @Override
    public T read() throws Exception {
        if (currentReaderIterator == null || !currentReaderIterator.hasNext()) {
            if (!readNextReader()) {
                return null;
            }
        }
        return currentReaderIterator.next();
    }

    private boolean readNextReader() {
        if (readers.isEmpty()) {
            return false;
        }

        ItemReader<T> nextReader = readers.remove(0);
        try {
            List<T> items = new ArrayList<>();
            T item;
            while ((item = nextReader.read()) != null) {
                items.add(item);
            }
            currentReaderIterator = items.iterator();
            return true;
        } catch (Exception e) {
            // Handle any exception
            return false;
        }
    }

    public static class MultiItemReaderBuilder<T> {
        private final List<ItemReader<T>> readers = new ArrayList<>();

        public MultiItemReaderBuilder<T> addReader(ItemReader<T> reader) {
            this.readers.add(reader);
            return this;
        }

        public MultiItemReader<T> build() {
            return new MultiItemReader<>(readers);
        }
    }
}
