package com.thesaugat.instafeed.dataHolder;

import java.io.Serializable;
import java.util.List;

public class StoryData  implements Serializable {
    public int id;
    public boolean is_viewed;
    public String userName;
    public String userPP;
    public String storyContent;
    public List<String> storyContents;

    public StoryData(int id, boolean is_viewed, String userName, String userPP, String storyContent, List<String> storyContents) {
        this.id = id;
        this.is_viewed = is_viewed;
        this.userName = userName;
        this.userPP = userPP;
        this.storyContent = storyContent;
        this.storyContents = storyContents;
    }
}
