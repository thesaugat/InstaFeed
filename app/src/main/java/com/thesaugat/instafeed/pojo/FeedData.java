package com.thesaugat.instafeed.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FeedData {

@SerializedName("feed")
@Expose
private List<Feed> feed = null;
@SerializedName("error")
@Expose
private Boolean error;
@SerializedName("message")
@Expose
private String message;

public List<Feed> getFeed() {
return feed;
}

public void setFeed(List<Feed> feed) {
this.feed = feed;
}

public Boolean getError() {
return error;
}

public void setError(Boolean error) {
this.error = error;
}

public String getMessage() {
return message;
}

public void setMessage(String message) {
this.message = message;
}

}