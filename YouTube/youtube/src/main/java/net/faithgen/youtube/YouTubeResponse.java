package net.faithgen.youtube;

import java.util.List;

public class YouTubeResponse {
    private String nextPageToken;
    private List<YouTubeObject> items;

    public String getNextPageToken() {
        return nextPageToken;
    }

    public void setNextPageToken(String nextPageToken) {
        this.nextPageToken = nextPageToken;
    }

    public List<YouTubeObject> getItems() {
        return items;
    }

    public void setItems(List<YouTubeObject> items) {
        this.items = items;
    }
}
