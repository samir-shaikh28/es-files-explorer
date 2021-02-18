package com.droidtechlab.filemanager.adapters.holders;


import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.droidtechlab.filemanager.R;
import com.google.android.gms.ads.nativead.NativeAdView;

public class UnifiedNativeAdViewHolder extends RecyclerView.ViewHolder {

    private NativeAdView adView;

    public NativeAdView getAdView() {
        return adView;
    }

    public UnifiedNativeAdViewHolder(View view) {
        super(view);
        adView = (NativeAdView) view.findViewById(R.id.ad_view);

        // The MediaView will display a video asset if one is present in the ad, and the
        // first image asset otherwise.
//        adView.setMediaView((MediaView) adView.findViewById(R.id.ad_media));
        adView.setCallToActionView(adView.findViewById(R.id.ad_call_to_action));
//        adView.setPriceView(adView.findViewById(R.id.ad_price));
//        adView.setStoreView(adView.findViewById(R.id.ad_store));

        // Register the view used for each individual asset.
        adView.setHeadlineView(adView.findViewById(R.id.ad_headline));
        adView.setBodyView(adView.findViewById(R.id.ad_body));
        adView.setIconView(adView.findViewById(R.id.ad_icon));
        adView.setStarRatingView(adView.findViewById(R.id.ad_stars));
        adView.setAdvertiserView(adView.findViewById(R.id.ad_advertiser));
    }
}