package net.faithgen.youtube.utils;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import net.faithgen.sdk.SDK;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class Utils {
    public static String getChannelId(String fullUrl) {
        return fullUrl.replace("https://www.youtube.com/channel/", "").replace("?view_as=subscriber", "");
    }

    public static String getYouTubeUrl(String pageToken, String filterText) {
        if (pageToken != null)
            return "https://www.googleapis.com/youtube/v3/search?key=AIzaSyC64i92ZZaZFE4-PyV_XgC2Zg3af5QuMyY&channelId=" + getChannelId(SDK.getMinistry().getLinks().getYoutube()) + "&part=snippet,id&order=date&maxResults=20&type=video&pageToken=" + pageToken + encodeSearchText(filterText);
        else
            return "https://www.googleapis.com/youtube/v3/search?key=AIzaSyC64i92ZZaZFE4-PyV_XgC2Zg3af5QuMyY&channelId=" + getChannelId(SDK.getMinistry().getLinks().getYoutube()) + "&part=snippet,id&order=date&maxResults=20&type=video" + encodeSearchText(filterText);
    }

    private static String encodeSearchText(String filterText){
        if (filterText.isEmpty()) return filterText;
        else {
            try {
                return "&q=" + URLEncoder.encode(filterText, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    public static void openVideo(Context context,String video_id) {
        Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + video_id));
        Intent webIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("http://www.youtube.com/watch?v=" + video_id));
        try {
            context.startActivity(appIntent);
        } catch (ActivityNotFoundException ex) {
            context.startActivity(webIntent);
        }
    }
}
