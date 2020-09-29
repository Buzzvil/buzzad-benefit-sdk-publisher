package com.buzzvil.buzzad.benefit.sample.publisher.feed;

import android.app.Activity;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.View;
import android.widget.TextView;

import com.buzzvil.buzzad.benefit.presentation.feed.toolbar.FeedActivityToolbar;
import com.buzzvil.buzzad.benefit.presentation.feed.toolbar.FeedToolbarHolder;
import com.buzzvil.buzzad.benefit.sample.publisher.R;

public class CustomFeedToolbarHolder implements FeedToolbarHolder {
    private FeedActivityToolbar toolbar;

    @Override
    public View getView(final Activity activity) {
        this.toolbar = new FeedActivityToolbar(activity);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.finish();
            }
        });
        toolbar.setTitle("BuzzAdBenefit Feed");

        // 배경색 변경
        toolbar.setBackgroundColor(Color.parseColor("#129000"));

        // 타이틀의 폰트 변경
        TextView textView = toolbar.findViewById(R.id.textTitle);
        Typeface typeface = Typeface.createFromAsset(activity.getAssets(), "fonts/NanumBarunGothic.ttf");
        try {
            typeface = Typeface.create(typeface, textView.getTypeface().getStyle());
        } catch (Exception e) {
            e.printStackTrace();
        }
        textView.setTypeface(typeface);

        // 타이틀의 색상 변경
        textView.setTextColor(Color.parseColor("#ff00ff"));

        return toolbar;
    }

    @Override
    public void onTotalRewardUpdated(int totalReward) {}
}

