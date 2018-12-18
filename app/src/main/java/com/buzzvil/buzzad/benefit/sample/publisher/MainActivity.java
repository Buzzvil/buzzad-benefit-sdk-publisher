package com.buzzvil.buzzad.benefit.sample.publisher;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.buzzvil.buzzad.benefit.core.ad.AdError;
import com.buzzvil.buzzad.benefit.core.models.Ad;
import com.buzzvil.buzzad.benefit.presentation.feed.FeedConfig;
import com.buzzvil.buzzad.benefit.presentation.feed.FeedHandler;
import com.buzzvil.buzzad.benefit.presentation.media.CtaView;
import com.buzzvil.buzzad.benefit.presentation.media.MediaView;
import com.buzzvil.buzzad.benefit.presentation.nativead.NativeAd;
import com.buzzvil.buzzad.benefit.presentation.nativead.NativeAdLoader;
import com.buzzvil.buzzad.benefit.presentation.nativead.NativeAdView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private Button nativeAdButton;
    private Button feedButton;
    private ProgressBar progressBar;
    private View interstitialView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nativeAdButton = findViewById(R.id.native_ad_button);
        nativeAdButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadNativeAd();
            }
        });

        feedButton = findViewById(R.id.feed_button);
        feedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final FeedConfig feedConfig = new FeedConfig.Builder(App.UNIT_ID_FEED)
                        .title("BuzzAdBenefit Feed")
                        .primaryColor("#1290FF")
                        .adsAdapterClass(CustomAdsAdapter.class)
                        .build();
                final FeedHandler feedHandler = new FeedHandler(feedConfig);
                feedHandler.startFeedActivity(MainActivity.this);
            }
        });

        progressBar = findViewById(R.id.progress_bar);
    }

    private void loadNativeAd() {
        progressBar.setVisibility(View.VISIBLE);
        final NativeAdLoader loader = new NativeAdLoader(App.UNIT_ID_NATIVE_AD);
        loader.loadAd(new NativeAdLoader.OnAdLoadedListener() {
            @Override
            public void onLoadError(@NonNull AdError adError) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(MainActivity.this, "Failed to load native ads.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdLoaded(@NonNull NativeAd nativeAd) {
                progressBar.setVisibility(View.GONE);

                MainActivity.this.interstitialView = populateAd(nativeAd);
                interstitialView.findViewById(R.id.ad_close_text).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        closeInterstitialView();
                    }
                });
                ((ViewGroup) findViewById(android.R.id.content)).addView(interstitialView);
            }
        });
    }

    private View populateAd(final NativeAd nativeAd) {
        final Ad ad = nativeAd.getAd();

        final View interstitialView = LayoutInflater.from(this).inflate(R.layout.view_interstitial_ad, null, false);
        final NativeAdView nativeAdView = interstitialView.findViewById(R.id.native_ad_view);
        final MediaView mediaView = interstitialView.findViewById(R.id.ad_media_view);
        final TextView titleTextView = interstitialView.findViewById(R.id.ad_title_text);
        final TextView descriptionTextView = interstitialView.findViewById(R.id.ad_description_text);
        final ImageView iconImageView = interstitialView.findViewById(R.id.ad_icon_image);
        final CtaView ctaView = interstitialView.findViewById(R.id.ad_cta_view);

        // 2) Assign ad properties.
        mediaView.setCreative(ad.getCreative());
        titleTextView.setText(ad.getTitle());
        descriptionTextView.setText(ad.getDescription());
        Glide.with(this).load(ad.getIconUrl()).into(iconImageView);
        ctaView.setRewardText(String.format(Locale.US, "+%d", ad.getReward()));
        ctaView.setCallToActionText(ad.getCallToAction());

        // 3) Create a list of clickable views.
        final List<View> clickableViews = new ArrayList<>();
        clickableViews.add(ctaView);
        // optional
        clickableViews.add(mediaView);
        clickableViews.add(titleTextView);
        clickableViews.add(descriptionTextView);

        // 4) Setup relevant views and native ad.
        nativeAdView.setMediaView(mediaView);
        nativeAdView.setClickableViews(clickableViews);
        nativeAdView.setNativeAd(nativeAd);

        nativeAdView.setOnNativeAdEventListener(new NativeAdView.OnNativeAdEventListener() {
            @Override
            public void onImpressed(@NonNull NativeAdView nativeAdView, @NonNull NativeAd nativeAd) {

            }

            @Override
            public void onClicked(@NonNull NativeAdView nativeAdView, @NonNull NativeAd nativeAd) {

            }

            @Override
            public void onParticipated(@NonNull NativeAdView nativeAdView, @NonNull NativeAd nativeAd) {
                final CtaView ctaView = interstitialView.findViewById(R.id.ad_cta_view);
                ctaView.setParticipated(true);
                ctaView.setRewardText(null);
            }
        });

        return interstitialView;
    }

    private boolean closeInterstitialView() {
        if (interstitialView != null) {
            ((ViewGroup) interstitialView.getParent()).removeView(interstitialView);
            interstitialView = null;
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onBackPressed() {
        if (!closeInterstitialView()) {
            super.onBackPressed();
        }
    }
}
