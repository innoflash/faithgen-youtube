package net.faithgen.youtube;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class YouTubeHolder extends RecyclerView.ViewHolder {
    private YouTubeView youTubeView;
    public YouTubeHolder(@NonNull View itemView) {
        super(itemView);
        this.youTubeView = (YouTubeView) itemView;
    }

    public YouTubeView getYouTubeView() {
        return youTubeView;
    }
}
