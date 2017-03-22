package com.udacity.gradle.builditbigger;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.udacity.gradle.builditbigger.utils.AsyncJokeDownloader;
import com.udacity.gradle.builditbigger.utils.JokeDownloadListener;

import me.chandansharma.jokedisplaylib.JokeDisplay;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements JokeDownloadListener{

    private InterstitialAd mInterstitialAd;
    private AdRequest mAdRequest;
    private String mErrorReason;
    private ProgressBar mProgressBar;
    public static final String JOKE_KEY = "joke_key";


    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);

        mAdRequest = new AdRequest.Builder().build();
        mInterstitialAd = new InterstitialAd(getActivity());
        mInterstitialAd.setAdUnitId(getResources().getString(R.string.interstitial_ad_unit_id));

        mProgressBar = (ProgressBar) root.findViewById(R.id.progressBar);
        mProgressBar.setVisibility(View.INVISIBLE);
        root.findViewById(R.id.joke_telling_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AsyncJokeDownloader(MainActivityFragment.this).downloadJoke();
                mProgressBar.setVisibility(View.VISIBLE);
            }
        });
        AdView mAdView = (AdView) root.findViewById(R.id.adView);
        // Create an ad request. Check logcat output for the hashed device ID to
        // get test ads on a physical device. e.g.
        // "Use AdRequest.Builder.addTestDevice("ABCDEF012345") to get test ads on this device."
        mAdView.loadAd(mAdRequest);
        return root;
    }

    @Override
    public void onResume() {
        if(mInterstitialAd.isLoaded()){
            mInterstitialAd.show();
        }
        super.onResume();
    }

    @Override
    public void onStop() {
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdFailedToLoad(int errorCode) {
                mErrorReason = "";
                switch (errorCode){
                    case AdRequest.ERROR_CODE_INTERNAL_ERROR :
                        mErrorReason = "Internal Error";
                        break;
                    case AdRequest.ERROR_CODE_INVALID_REQUEST :
                        mErrorReason = "Invalid Request";
                        break;
                    case AdRequest.ERROR_CODE_NETWORK_ERROR :
                        mErrorReason = "Network Error";
                        break;
                    case AdRequest.ERROR_CODE_NO_FILL:
                        mErrorReason = "No fill";
                        break;
                }
                Toast.makeText(getContext(),String.format("onAdFailedToLoad(%s)", mErrorReason) , Toast.LENGTH_SHORT).show();
            }
        });
        mInterstitialAd.loadAd(mAdRequest);
        super.onStop();
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
