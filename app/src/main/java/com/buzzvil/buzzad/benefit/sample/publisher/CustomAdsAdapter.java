package com.buzzvil.buzzad.benefit.sample.publisher;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.buzzvil.buzzad.benefit.core.models.Ad;
import com.buzzvil.buzzad.benefit.presentation.feed.ad.AdsAdapter;
import com.buzzvil.buzzad.benefit.presentation.feed.ad.FeedAdView;
import com.buzzvil.buzzad.benefit.presentation.media.CtaView;
import com.buzzvil.buzzad.benefit.presentation.media.MediaView;
import com.buzzvil.buzzad.benefit.presentation.nativead.NativeAd;
import com.buzzvil.buzzad.benefit.presentation.nativead.NativeAdView;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

public class CustomAdsAdapter extends AdsAdapter<CustomAdsAdapter.NativeAdViewHolder> {

    private List<NativeAd> ads = new ArrayList<>();

    @Override
    public NativeAdViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final FeedAdView feedAdView = (FeedAdView) inflater.inflate(R.layout.bz_view_feed_ad, parent, false);
        feedAdView.setOnNativeAdEventListener(new NativeAdView.OnNativeAdEventListener() {
            @Override
            public void onImpressed(@NonNull final NativeAdView view, @NonNull final NativeAd nativeAd) {
            }

            @Override
            public void onClicked(@NonNull NativeAdView view, @NonNull NativeAd nativeAd) {
            }

            @Override
            public void onParticipated(@NonNull final NativeAdView view, @NonNull final NativeAd nativeAd) {
                if (view.getNativeAd() == nativeAd) {
                    bind(nativeAd, (FeedAdView) view);
                }
            }
        });
        return new NativeAdViewHolder(feedAdView);
    }

    @Override
    public void onBindViewHolder(NativeAdViewHolder holder, int position) {
        final FeedAdView feedAdView = (FeedAdView) holder.itemView;
        final NativeAd nativeAd = ads.get(position);
        bind(nativeAd, feedAdView);
    }

    @Override
    public int getItemCount() {
        return ads.size();
    }

    private void bind(final NativeAd nativeAd, final FeedAdView view) {
        final Ad ad = nativeAd.getAd();
        final MediaView mediaView = view.getMediaView();
        final TextView titleView = view.getTitleView();
        final ImageView iconView = view.getIconView();
        final TextView descriptionView = view.getDescriptionView();
        final CtaView ctaView = view.getCtaView();

        if (mediaView != null) {
            mediaView.setCreative(ad.getCreative());
        }
        if (titleView != null) {
            titleView.setText(ad.getTitle());
        }
        if (iconView != null) {
            ImageLoader.getInstance().displayImage(ad.getIconUrl(), iconView);
        }
        if (descriptionView != null) {
            descriptionView.setText(ad.getDescription());
        }

        if (ctaView != null) {
            if (nativeAd.isParticipated()) {
                ctaView.setParticipated(true);
                ctaView.setRewardText(null);
                ctaView.setCallToActionText(view.getResources().getString(com.buzzvil.buzzad.benefit.presentation.feed.R.string.bz_cta_done));
            } else {
                ctaView.setParticipated(false);
                if (ad.getReward() > 0) {
                    ctaView.setRewardText(String.format(Locale.US, "+%d", ad.getReward()));
                } else {
                    ctaView.setRewardText(null);
                }
                ctaView.setCallToActionText(ad.getCallToAction());
            }
        }

        final Collection<View> clickableViews = Arrays.asList((View) view.getCtaView());

        view.setNativeAd(nativeAd);
        view.setMediaView(mediaView);
        view.setClickableViews(clickableViews);
    }

    @Override
    public void setAds(@NonNull final List<NativeAd> ads) {
        this.ads = ads;
        notifyDataSetChanged();
    }

    public static class NativeAdViewHolder extends RecyclerView.ViewHolder {
        NativeAdViewHolder(final FeedAdView feedAdView) {
            super(feedAdView);
        }
    }
}
