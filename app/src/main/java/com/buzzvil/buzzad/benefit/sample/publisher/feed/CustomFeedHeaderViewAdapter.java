package com.buzzvil.buzzad.benefit.sample.publisher.feed;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.buzzvil.buzzad.benefit.presentation.feed.FeedHeaderViewAdapter;

public class CustomFeedHeaderViewAdapter implements FeedHeaderViewAdapter {
    @NonNull
    @Override
    public View onCreateView(final Context context, final ViewGroup parent) {
        final LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        return inflater.inflate(com.buzzvil.buzzad.benefit.presentation.feed.R.layout.bz_view_feed_header, parent, false);
    }

    @Override
    public void onBindView(final View view, final int reward) {
        final TextView rewardTextView = view.findViewById(com.buzzvil.buzzad.benefit.presentation.feed.R.id.rewardText);
        if (reward == 0) {
            rewardTextView.setVisibility(View.GONE);
        } else {
            rewardTextView.setVisibility(View.VISIBLE);
            final String rewardText = view.getContext().getString(com.buzzvil.buzzad.benefit.presentation.feed.R.string.bz_feed_header_view_reward_text, reward);
            rewardTextView.setText(rewardText);
        }
    }
}
