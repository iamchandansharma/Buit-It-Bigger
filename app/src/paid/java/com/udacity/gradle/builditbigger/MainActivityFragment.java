package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.udacity.gradle.builditbigger.utils.AsyncJokeDownloader;
import com.udacity.gradle.builditbigger.utils.JokeDownloadListener;

import me.chandansharma.jokedisplaylib.JokeDisplay;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements JokeDownloadListener {

    private ProgressBar mProgressBar;
    public static final String JOKE_KEY = "joke_key";

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);
        mProgressBar = (ProgressBar) root.findViewById(R.id.progressBar);
        mProgressBar.setVisibility(View.INVISIBLE);
        root.findViewById(R.id.joke_telling_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AsyncJokeDownloader(MainActivityFragment.this).downloadJoke();
                mProgressBar.setVisibility(View.VISIBLE);
            }
        });
        return root;
    }

    @Override
    public void downloadCompleted(String joke) {
        mProgressBar.setVisibility(View.INVISIBLE);
        Toast.makeText(getActivity(), joke, Toast.LENGTH_SHORT).show();
        Intent jokeDisplayIntent = new Intent(getActivity(), JokeDisplay.class);
        jokeDisplayIntent.putExtra(JOKE_KEY,joke);
        startActivity(jokeDisplayIntent);
    }
}
