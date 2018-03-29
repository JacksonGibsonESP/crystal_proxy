package ru.mai.dep810.webapp.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JacksonGibsonESP on 29.03.2018.
 */
public class RowItem {
    private String _id;
    private List<String> highlights = new ArrayList<>();
    private String author;
    private String date;
    private String title;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public List<String> getHighlights() {
        return highlights;
    }

    public void setHighlights(List<String> highlights) {
        this.highlights = highlights;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
