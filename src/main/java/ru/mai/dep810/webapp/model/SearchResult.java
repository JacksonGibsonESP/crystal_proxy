package ru.mai.dep810.webapp.model;

import java.util.Collection;

/**
 * Класс для хранения результата поиска произвольных объектов.
 */
public class SearchResult<T> {
    private Collection<T> rows;
    private long totalHits;

    public SearchResult(Collection<T> rows, long totalHits) {
        this.rows = rows;
        this.totalHits = totalHits;
    }

    public Collection<T> getRows() {
        return rows;
    }

    public long getTotalHits() {
        return totalHits;
    }
}
