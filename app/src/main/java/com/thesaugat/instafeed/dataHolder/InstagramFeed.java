package com.thesaugat.instafeed.dataHolder;

import java.io.Serializable;
import java.util.List;

public class InstagramFeed implements Serializable {
    public String profilePicture;
    public String personName;
    public String time;
    public String bodyImage;
    public String description;
    public int viewsCount;
    public int heartsCount;
    public int commentsCount;
    public List<Comments> commentsList;

    public InstagramFeed(String profilePicture, String personName, String time, String bodyImage, String description, int viewsCount, int heartsCount, int commentsCount, List<Comments> commentsList) {
        this.profilePicture = profilePicture;
        this.personName = personName;
        this.time = time;
        this.bodyImage = bodyImage;
        this.description = description;
        this.viewsCount = viewsCount;
        this.heartsCount = heartsCount;
        this.commentsCount = commentsCount;
        this.commentsList = commentsList;
    }
}
