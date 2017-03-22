package me.chandansharma.jokedisplaylib;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;


public class JokeDisplay extends AppCompatActivity {

    public static final String JOKE_KEY = "joke_key";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joke_display);
        TextView jokeDisplayTextView;
        jokeDisplayTextView = (TextView) findViewById(R.id.joke_display_text_view);

        Intent JokeReceiveIntent = getIntent();
        String Joke = JokeReceiveIntent.getStringExtra(JOKE_KEY);
        jokeDisplayTextView.setText(Joke);
    }
}
