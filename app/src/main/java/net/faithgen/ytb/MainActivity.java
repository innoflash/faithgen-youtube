package net.faithgen.ytb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import net.faithgen.youtube.activities.YouTubeActivity;

public class MainActivity extends AppCompatActivity {

    private Button open;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        open = findViewById(R.id.open);
        open.setOnClickListener(view -> startActivity(new Intent(this, YouTubeActivity.class)));
    }
}
