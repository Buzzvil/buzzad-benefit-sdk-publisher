package com.buzzvil.buzzad.benefit.sample.publisher;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.buzzvil.buzzad.benefit.core.models.Ad;
import com.buzzvil.buzzad.benefit.presentation.media.CtaView;
import com.buzzvil.buzzad.benefit.presentation.media.MediaView;
import com.buzzvil.buzzad.benefit.presentation.nativead.NativeAd;
import com.buzzvil.buzzad.benefit.presentation.nativead.NativeAdView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

public class NativeAdsView extends FrameLayout {
    private ViewPager pager;
    private TabLayout tabLayoutDots;
    private NativeAdsAdapter nativeAdsAdapter;

    private static final int PAGER_DISTANCE_DP = 16;

    public NativeAdsView(@NonNull Context context) {
        this(context, null);
    }

    public NativeAdsView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.view_native_ads, this);
        this.pager = findViewById(R.id.pager);
        this.tabLayoutDots = findViewById(R.id.tab_layout_dots);
        final int pagerDistance = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, PAGER_DISTANCE_DP, getResources().getDisplayMetrics());
        pager.setClipToPadding(false);
        pager.setPadding(pagerDistance * 2, 0, pagerDistance * 2, 0);
        pager.setPageMargin(pagerDistance);
        tabLayoutDots.setupWithViewPager(pager);
    }

    public void setNativeAds(Collection<NativeAd> nativeAds) {
        this.nativeAdsAdapter = new NativeAdsAdapter(nativeAds);
        pager.setAdapter(nativeAdsAdapter);
    }

    private static class NativeAdsAdapter extends android.support.v4.view.PagerAdapter {
        private final List<NativeAd> nativeAds;

        NativeAdsAdapter(Collection<NativeAd> nativeAds) {
            this.nativeAds = new ArrayList<>(nativeAds);
        }

        @Override
        public int getCount() {
            return nativeAds.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = populateAd(container.getContext(), nativeAds.get(position));
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        private View populateAd(final Context context, final NativeAd nativeAd) {
            final Ad ad = nativeAd.getAd();

            final NativeAdView nativeAdView = (NativeAdView) LayoutInflater.from(context).inflate(R.layout.view_native_ad_item, null, false);
            final MediaView mediaView = nativeAdView.findViewById(R.id.ad_media_view);
            final TextView titleTextView = nativeAdView.findViewById(R.id.ad_title_text);
            final TextView descriptionTextView = nativeAdView.findViewById(R.id.ad_description_text);
            final ImageView iconImageView = nativeAdView.findViewById(R.id.ad_icon_image);
            final CtaView ctaView = nativeAdView.findViewById(R.id.ad_cta_view);

            mediaView.setCreative(ad.getCreative());
            titleTextView.setText(ad.getTitle());
            descriptionTextView.setText(ad.getDescription());
            Glide.with(context).load(ad.getIconUrl()).into(iconImageView);
            ctaView.setRewardText(String.format(Locale.US, "+%d", ad.getReward()));
            ctaView.setCallToActionText(ad.getCallToAction());

            final List<View> clickableViews = new ArrayList<>();
            clickableViews.add(ctaView);
            clickableViews.add(mediaView);
            clickableViews.add(titleTextView);
            clickableViews.add(descriptionTextView);

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
                    final CtaView ctaView = nativeAdView.findViewById(R.id.ad_cta_view);
                    ctaView.setParticipated(true);
                    ctaView.setRewardText(null);
                }
            });

            return nativeAdView;
        }
    }

}
