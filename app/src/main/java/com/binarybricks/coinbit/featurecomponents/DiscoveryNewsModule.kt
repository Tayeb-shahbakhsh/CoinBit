package com.binarybricks.coinbit.featurecomponents

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.binarybricks.coinbit.R
import com.binarybricks.coinbit.network.models.CryptoCompareNews
import com.binarybricks.coinbit.utils.Formaters
import com.binarybricks.coinbit.utils.resourcemanager.AndroidResourceManager
import com.binarybricks.coinbit.utils.openCustomTab
import com.squareup.picasso.Picasso
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation
import kotlinx.android.synthetic.main.discovery_news_module.view.*
import timber.log.Timber

/**
 * Created by Pranay Airan
 *
 * Simple class that wraps all logic related to showing news on discovery feed.
 */

class DiscoveryNewsModule(private val androidResourceManager: AndroidResourceManager) : Module() {

    override fun init(layoutInflater: LayoutInflater, parent: ViewGroup?): View {
        return layoutInflater.inflate(R.layout.discovery_news_module, parent, false)
    }

    fun showNewsOnDiscoverFeed(inflatedView: View, discoveryNewsModuleData: DiscoveryNewsModuleData) {

        inflatedView.tvSource.text = discoveryNewsModuleData.coinNews.source
        inflatedView.tvHeadlines.text = discoveryNewsModuleData.coinNews.title
        if (discoveryNewsModuleData.coinNews.published_on != null) {
            inflatedView.tvTimePeriod.text = Formaters(androidResourceManager).formatTransactionDate(discoveryNewsModuleData.coinNews.published_on)
        }

        Picasso.get().load(discoveryNewsModuleData.coinNews.imageurl)
                .transform(RoundedCornersTransformation(15, 0))
                .into(inflatedView.ivNewsCover)

        inflatedView.clNewsArticleContainer.setOnClickListener {
            if (discoveryNewsModuleData.coinNews.url != null) {
                openCustomTab(discoveryNewsModuleData.coinNews.url, inflatedView.context)
            }
        }
    }

    override fun cleanUp() {
        Timber.d("Clean up about DiscoveryNews module")
    }

    data class DiscoveryNewsModuleData(val coinNews: CryptoCompareNews) : ModuleItem
}