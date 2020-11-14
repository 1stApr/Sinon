package com.crocussativus.wallpaper;

import java.util.HashMap;
import java.util.Map;

public class Image {
    public String id;
    public String link;

    public Image() {
    }

    public Image(String category, String link) {
        this.id = category;
        this.link = link;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("id", id);
        result.put("link", link);
        return result;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
