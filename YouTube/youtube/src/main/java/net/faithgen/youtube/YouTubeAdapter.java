package net.faithgen.youtube;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class YouTubeAdapter extends RecyclerView.Adapter<YouTubeHolder> {

    private Context context;
    private List<YouTubeObject> youTubeObjects;
    private LayoutInflater layoutInflater;

    public YouTubeAdapter(Context context, List<YouTubeObject> youTubeObjects) {
        this.context = context;
        this.youTubeObjects = youTubeObjects;
        this.layoutInflater = LayoutInflater.from(this.context);
    }

    @NonNull
    @Override
    public YouTubeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new YouTubeHolder(layoutInflater.inflate(R.layout.list_youtube_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull YouTubeHolder holder, int position) {
        holder.getYouTubeView().setYouTubeObject(youTubeObjects.get(position));
    }

    @Override
    public int getItemCount() {
        if (youTubeObjects == null)
            return 0;
        return youTubeObjects.size();
    }
}
