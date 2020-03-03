package net.faithgen.youtube

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

final class YouTubeAdapter(private val context: Context, private val youTubeObjects: List<YouTubeObject>?) : RecyclerView.Adapter<YouTubeHolder>() {
    private val layoutInflater: LayoutInflater by lazy { LayoutInflater.from(context) }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): YouTubeHolder {
        val youtubeView = layoutInflater.inflate(R.layout.list_youtube_item, parent, false) as YouTubeView
        return YouTubeHolder(youtubeView)
    }

    override fun getItemCount(): Int {
        if (youTubeObjects === null)
            return 0
        return youTubeObjects.size
    }

    override fun onBindViewHolder(holder: YouTubeHolder, position: Int) {
        holder.youTubeView.youTubeObject = youTubeObjects!![position]
    }
}