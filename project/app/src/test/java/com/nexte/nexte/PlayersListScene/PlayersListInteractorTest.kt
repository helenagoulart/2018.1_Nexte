package com.nexte.nexte.PlayersListScene


import com.nexte.nexte.Entities.User.UserAdapterSpy
import com.nexte.nexte.Entities.User.UserManager
import com.nexte.nexte.HelpForRealm
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test


class PlayersListInteractorTest: HelpForRealm() {

    private var mocker : MockPlayersListPresentationLogic? = null
    private var interactor : PlayersListInteractor? = null

    @Before
    fun setUp(){
        super.setUpWithUser()

        this.mocker = MockPlayersListPresentationLogic()
        this.interactor = PlayersListInteractor()
        this.interactor?.presenter = mocker
    }

    @Test
    fun successRequestPlayersToChallenge(){
        //prepare
        val testRequest = PlayersListModel.ShowRankingPlayersRequest.Request(1)

        //call
        this.interactor?.requestPlayersToChallenge(testRequest)

        //assert
        assertEquals(true, this.mocker?.passedHere)

    }

    @Test
    fun successRequestChallengedUser(){
        //prepare
        var worker = PlayersListWorker()
        worker?.userManager = UserManager(UserAdapterSpy())
        this.interactor?.worker = worker
        val testRequest = PlayersListModel.SelectPlayerForChallengeRequest.Request(1)

        //call
        this.interactor?.requestChallengedUser(testRequest)

        //assert
        assertEquals(true, this.mocker?.passedHere)

    }

    @Test
    fun successRequestChallenger(){
        //prepare

        val testRequest = PlayersListModel.ChallengeButtonRequest.Request("Lorrany Freire")

        //call
        this.interactor?.requestChallenger(testRequest)

        //assert
        assertEquals(true, this.mocker?.passedHere)

    }

    @After
    fun tearDown(){
        super.tearDownRealm()

        this.mocker = null
        this.interactor = null
    }

    private class MockPlayersListPresentationLogic : PlayersListPresentationLogic{
        var passedHere = false

        override fun formatExpandedChallengedInfo(response: PlayersListModel.SelectPlayerForChallengeRequest.Response) {
            passedHere = true
        }

        override fun formatMatch(response: PlayersListModel.ChallengeButtonRequest.Response) {
            passedHere = true
        }

        override fun formatNoPlayersMessage() {
            passedHere = true
        }

        override fun formatPlayersToChallenge(response: PlayersListModel.ShowRankingPlayersRequest.Response) {
            passedHere = true
        }
    }



}