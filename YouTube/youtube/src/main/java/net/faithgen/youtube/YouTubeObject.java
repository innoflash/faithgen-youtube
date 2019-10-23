package net.faithgen.youtube;

import org.itishka.gsonflatten.Flatten;

public class YouTubeObject {
    public static final String VIDEO_ID = "video_id";
    @Flatten("id::videoId")
    private String videoId;
    @Flatten("snippet::publishedAt")
    private String publishedAt;
    @Flatten("snippet::title")
    private String title;
    @Flatten("snippet::description")
    private String description;
    @Flatten("snippet::thumbnails::medium::url")
    private String thumbnailUrl;

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getDate() {
        String[] months = {
                "Jan",
                "Feb",
                "Mar",
                "Apr",
                "May",
                "Jun",
                "Jul",
                "Aug",
                "Sept",
                "Oct",
                "Nov",
                "Dev"
        };
        String[] splitDate = getPublishedAt().substring(0, getPublishedAt().indexOf("T")).split("-");
        return splitDate[2] + " " + months[Integer.parseInt(splitDate[1]) - 1] + " " + splitDate[0];
    }
}
