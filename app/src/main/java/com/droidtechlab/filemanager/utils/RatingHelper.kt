package com.droidtechlab.filemanager.utils

import android.os.Bundle
import com.droidtechlab.filemanager.ui.activities.MainActivity
import com.google.firebase.analytics.FirebaseAnalytics
import com.vorlonsoft.android.rate.AppRate


object RatingHelper {

    private const val RATE_IT_NOW = -1
    private const val NO_THANKS = -2
    private const val REMIND_ME_LATER = -3

    @JvmStatic
    fun showRatingDialog(activity: MainActivity) {
        trackEvent(activity = activity, event = FirebaseAnalytics.Event.SCREEN_VIEW, propertyName = FirebaseAnalytics.Param.SCREEN_NAME, propertyValue = "rating_dialog")
        AppRate.with(activity)
                .setInstallDays(0.toByte()) // default is 10, 0 means install day, 10 means app is launched 10 or more days later than installation
                .setLaunchTimes(5.toByte()) // default is 10, 3 means app is launched 3 or more times
                .setRemindInterval(2.toByte()) // default is 1, 1 means app is launched 1 or more days after neutral button clicked
                .setOnClickButtonListener { which ->
                    when (which) {
                        RATE_IT_NOW.toByte() -> {
                            trackEvent(activity = activity, event = "ES_CLICK", propertyName = "RATE_IT_NOW", propertyValue = "rate_it_now")
                        }
                        NO_THANKS.toByte() -> {
                            trackEvent(activity = activity, event = "ES_CLICK", propertyName = "NO_THANKS", propertyValue = "no_thanks")

                        }
                        REMIND_ME_LATER.toByte() -> {
                            trackEvent(activity = activity, event = "ES_CLICK", propertyName = "REMIND_ME_LATER", propertyValue = "remind_me_later")
                        }
                    }
                }
                .monitor() // Monitors the app launch times

        AppRate.showRateDialogIfMeetsConditions(activity) // Shows the Rate Dialog when conditions are met


    }

    private fun trackEvent(activity: MainActivity, event: String, propertyName: String, propertyValue: String) {
        val firebaseAnalytics = FirebaseAnalytics.getInstance(activity);
        val bundle = Bundle()
        bundle.putString(propertyName, propertyValue)
        firebaseAnalytics.logEvent(event, bundle)
    }

//    @JvmStatic
//    fun showGoogleInAppReview(activity: MainActivity) {
//
//        val reviewManger = ReviewManagerFactory.create(activity)
//
//        val requestTask = reviewManger?.requestReviewFlow()
//        requestTask?.addOnCompleteListener { request ->
//            if (request.isSuccessful) {
//                val reviewInfo = request.result
//                val flow = reviewManger?.launchReviewFlow(activity, reviewInfo)
//                flow?.addOnCompleteListener { task ->
//                    if (task.isSuccessful) {
//                    }
//                }
//            } else {
//            }
//        }
//    }
//

//    private fun initReviews(activity: MainActivity) {
//
//        var reviewInfo: ReviewInfo? = null
//        manager = ReviewManagerFactory.create(activity)
//        manager.requestReviewFlow().addOnCompleteListener { request ->
//            if (request.isSuccessful) {
//                reviewInfo = request.result
//            } else {
//                // Log error
//            }
//        }
//    }
//
//    // Call this when you want to show the dialog
//    private fun askForReview() {
//        if (reviewInfo != null) {
//            manager.launchReviewFlow(this, reviewInfo).addOnFailureListener {
//                // Log error and continue with the flow
//            }.addOnCompleteListener { _ ->
//                // Log success and continue with the flow
//            }
//        }
//    }


}