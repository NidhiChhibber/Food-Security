package com.example.research_app.CosumerClasses;

import com.example.research_app.R;

import java.util.Locale;

public class ConsumerDashItemList {
    private int imageDrawable;
    private int id;
    private String title;
    private String description;

    public ConsumerDashItemList(int id, String toDo, String desc, int pictureId)
    {
        this.id = id;
        this.imageDrawable = pictureId;
        this.title = toDo;
        this.description = desc;
    }

    public int getImageDrawable() {
        return imageDrawable;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}