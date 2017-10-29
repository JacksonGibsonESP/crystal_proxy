package ru.mai.dep810.webapp.model;

import java.util.Collection;

/**
 * Класс для хранения результата поиска произвольных объектов.
 */
public class SearchResult<T> {
    private Collection<T> rows;
    private long totalRows;

    public SearchResult(Collection<T> rows, long totalRows) {
        this.rows = rows;
        this.totalRows = totalRows;
    }

    public Collection<T> getRows() {
        return rows;
    }

    public long getTotalRows() {
        return totalRows;
    }
}
