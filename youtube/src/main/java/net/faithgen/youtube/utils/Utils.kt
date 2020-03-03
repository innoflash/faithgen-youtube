package net.faithgen.youtube.utils

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import net.faithgen.sdk.SDK
import java.io.UnsupportedEncodingException
import java.net.URLEncoder

object Utils {
    fun getChannelId(fullUrl: String): String {
        return fullUrl.replace("https://www.youtube.com/channel/", "").replace("?view_as=subscriber", "")
    }

    @JvmStatic
    fun getYouTubeUrl(pageToken: String?, filterText: String): String {
        return if (pageToken != null) "https://www.googleapis.com/youtube/v3/search?key=AIzaSyC64i92ZZaZFE4-PyV_XgC2Zg3af5QuMyY&channelId=" + getChannelId(SDK.getMinistry().links.youtube) + "&part=snippet,id&order=date&maxResults=20&type=video&pageToken=" + pageToken + encodeSearchText(filterText) else "https://www.googleapis.com/youtube/v3/search?key=AIzaSyC64i92ZZaZFE4-PyV_XgC2Zg3af5QuMyY&channelId=" + getChannelId(SDK.getMinistry().links.youtube) + "&part=snippet,id&order=date&maxResults=20&type=video" + encodeSearchText(filterText)
    }

    private fun encodeSearchText(filterText: String): String? {
        return if (filterText.isEmpty()) filterText else {
            try {
                "&q=" + URLEncoder.encode(filterText, "UTF-8")
            } catch (e: UnsupportedEncodingException) {
                e.printStackTrace()
                null
            }
        }
    }

    @JvmStatic
    fun openVideo(context: Context, video_id: String) {
        val appIntent = Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:$video_id"))
        val webIntent = Intent(Intent.ACTION_VIEW,
                Uri.parse("http://www.youtube.com/watch?v=$video_id"))
        try {
            context.startActivity(appIntent)
        } catch (ex: ActivityNotFoundException) {
            context.startActivity(webIntent)
        }
    }
}