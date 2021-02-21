package com.thesaugat.instafeed;

import java.io.Serializable;
import java.util.ArrayList;

public class NewsData implements Serializable {
    public int id;
    public String title;
    public String desc;
    public ArrayList<String> images;

    public NewsData(int id, String title, String desc, ArrayList<String> images) {
        this.id = id;
        this.title = title;
        this.desc = desc;
        this.images = images;
    }
}
