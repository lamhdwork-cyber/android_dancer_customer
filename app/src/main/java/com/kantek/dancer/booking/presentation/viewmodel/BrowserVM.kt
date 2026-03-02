package com.kantek.dancer.booking.presentation.viewmodel

import android.support.core.event.LoadingEvent
import android.support.core.event.LoadingFlow
import com.kantek.dancer.booking.app.AppViewModel
import com.kantek.dancer.booking.data.repo.GetLinkAboutUsRepo
import com.kantek.dancer.booking.data.repo.GetLinkTermsRepo
import com.kantek.dancer.booking.presentation.extensions.launch

class BrowserVM(
    private val getLinkAboutUsRepo: GetLinkAboutUsRepo,
    private val getLinkTermsRepo: GetLinkTermsRepo
) : AppViewModel() {

    val aboutUs = getLinkAboutUsRepo.result
    val terms = getLinkTermsRepo.result
    val customLoading: LoadingEvent = LoadingFlow()

    fun getAboutUs() = launch(customLoading, error) {
        getLinkAboutUsRepo()
    }

    fun getTerms() = launch(customLoading, error) {
        getLinkTermsRepo()
    }

}