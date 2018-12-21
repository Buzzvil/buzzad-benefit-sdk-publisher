package com.buzzvil.buzzad.benefit.sample.publisher.feed;

import android.content.Context;
import android.support.annotation.NonNull;
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

import java.util.Arrays;
import java.util.Collection;
import java.util.Locale;

//public class CustomAdsAdapter extends AdsAdapter<AdsAdapter.NativeAdViewHolder> {
//
//    @Override
//    public NativeAdViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        final LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        final FeedAdView feedAdView = (FeedAdView) inflater.inflate(R.layout.bz_view_feed_ad, parent, false);
//        return new NativeAdViewHolder(feedAdView);
//    }
//
//    @Override
//    public void onBindViewHolder(NativeAdViewHolder holder, NativeAd nativeAd) {
//        final FeedAdView view = (FeedAdView) holder.itemView;
//
//        final Ad ad = nativeAd.getAd();
//        final MediaView mediaView = view.getMediaView();
//        final TextView titleView = view.getTitleView();
//        final ImageView iconView = view.getIconView();
//        final TextView descriptionView = view.getDescriptionView();
//        final CtaView ctaView = view.getCtaView();
//
//        if (mediaView != null) {
//            mediaView.setCreative(ad.getCreative());
//        }
//        if (titleView != null) {
//            titleView.setText(ad.getTitle());
//        }
//        if (iconView != null) {
//            ImageLoader.getInstance().displayImage(ad.getIconUrl(), iconView);
//        }
//        if (descriptionView != null) {
//            descriptionView.setText(ad.getDescription());
//        }
//
//        if (ctaView != null) {
//            if (nativeAd.isParticipated()) {
//                ctaView.setParticipated(true);
//                ctaView.setRewardText(null);
//                ctaView.setCallToActionText(view.getResources().getString(R.string.bz_cta_done));
//            } else {
//                ctaView.setParticipated(false);
//                if (ad.getReward() > 0) {
//                    ctaView.setRewardText(String.format(Locale.US, "+%d", ad.getReward()));
//                } else {
//                    ctaView.setRewardText(null);
//                }
//                ctaView.setCallToActionText(ad.getCallToAction());
//            }
//        }
//
//        final Collection<View> clickableViews = Arrays.asList((View) view.getCtaView());
//
//        view.setNativeAd(nativeAd);
//        view.setMediaView(mediaView);
//        view.setClickableViews(clickableViews);
//    }
//
//    @Override
//    public void onImpressed(@NonNull NativeAdView view, @NonNull NativeAd nativeAd) {
//        super.onImpressed(view, nativeAd);
//    }
//
//    @Override
//    public void onClicked(@NonNull NativeAdView view, @NonNull NativeAd nativeAd) {
//        super.onClicked(view, nativeAd);
//    }
//
//    @Override
//    public void onParticipated(@NonNull NativeAdView view, @NonNull NativeAd nativeAd) {
//        super.onParticipated(view, nativeAd);
//    }
//}
