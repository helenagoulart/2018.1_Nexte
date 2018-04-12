package com.nexte.nexte.ShowProfileScene

/**
 * Created by albino on 25/03/18.
 */

interface ShowProfileBusinessLogic {

    fun showProfile(request: ShowProfileModel.Request)
}

/* This class connects Presenter and Worker,
    passing the request to Worker and sending
    response received by Worker to Presenter */
class ShowProfileInteractor : ShowProfileBusinessLogic {

    private var worker = ShowProfileWorker() // Receives token and send empty or valid player
    var presenter : ShowProfilePresentationLogic? = null // Receives player as response

    override fun showProfile(request: ShowProfileModel.Request) {

        worker.getUserProfile(request) { response ->
            this.presenter?.presentUserProfile(response)
        }
    }
}

