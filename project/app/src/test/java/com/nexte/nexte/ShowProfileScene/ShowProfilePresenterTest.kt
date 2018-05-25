package com.nexte.nexte.ShowProfileScene

import com.nexte.nexte.Entities.User.User
import com.nexte.nexte.Player
import org.junit.After
import org.junit.Before

import org.junit.Assert.*
import org.junit.Test
import java.util.*

class ShowProfilePresenterTest {

    private var mock: MockShowProfileDisplayLogic? = null
    private var presenter: ShowProfilePresenter? = null

    @Before
    fun setUp() {
        this.mock = MockShowProfileDisplayLogic()
        this.presenter = ShowProfilePresenter()
        this.presenter?.viewScene = mock
    }


    @Test
    fun successPresentUserProfile(){
        //prepare
        val response = ShowProfileModel.Response(user = User("13",
            "Nick Cairo",
            null,
            "Cairo",
            Date(1993, 3, 13),
            12,
            "cairo@nexte.com",
            "130",
            68,
            96,
            User.Gender.MALE,
            null,
            User.Status.AVAILABLE,
            null,
            null,
            emptyList()
        ))
        val formattedPlayer = ShowProfileModel.FormattedPlayer(name = "gabrielalbino", rank = "#2", email = "enggabriel@gmail.com")

        //call
        this.presenter?.presentUserProfile(response = response)

        //assert
        assertEquals(formattedPlayer.name, this.mock?.formatedPlayer?.name)
        assertEquals(formattedPlayer.rank, this.mock?.formatedPlayer?.rank)
        assertEquals(formattedPlayer.email, this.mock?.formatedPlayer?.email)
    }

    @After
    fun tearDown() {
        this.mock = null
        this.presenter = null
    }
}

private class MockShowProfileDisplayLogic: ShowProfileDisplayLogic{
    var formatedPlayer: ShowProfileModel.FormattedPlayer? = null

    override fun displayProfile(viewModel: ShowProfileModel.ViewModel) {
        this.formatedPlayer = viewModel.playerInfo
    }
}