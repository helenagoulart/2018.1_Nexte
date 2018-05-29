package com.nexte.nexte.ShowProfileScene

import com.nexte.nexte.Entities.User.User
import org.junit.After
import org.junit.Before

import org.junit.Assert.*
import org.junit.Test
import java.util.*

@Suppress("DEPRECATION")
class ShowProfileModelTest {

    @Before
    fun setUp() {

    }

    @Test
    fun initShowProfile(){
        //prepare
        //call
        val testInit = ShowProfileModel()

        //assert
        assertNotNull(testInit)
    }

    @Test
    fun successRequest(){
        //prepare
        val userName = "luis-gustavo"

        //call
        val request = ShowProfileModel.Request(username = "luis-gustavo")
        request.username = userName

        //assert
        assertEquals(userName, request.username)
    }

    @Test
    fun successResponse(){
        //prepare
        val player = User("13",
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
        )

        //call
        val response = ShowProfileModel.Response(user = player)
        response.user = player

        //assert
        assertNotNull(response.user)
        assertEquals(response.user, player)

    }

    @Test
    fun successViewModel(){
        //prepare
        val formattedPlayer = ShowProfileModel.FormattedPlayer(name = "Luis Gustavo", rank = "1", email = "luis@email.com")

        //call
        val viewModel = ShowProfileModel.ViewModel(playerInfo = formattedPlayer)
        viewModel.playerInfo.rank
        viewModel.playerInfo.name
        viewModel.playerInfo.email

        viewModel.playerInfo = formattedPlayer

        //assert
        assertNotNull(viewModel.playerInfo)
        assertEquals(formattedPlayer, viewModel.playerInfo)
        assertEquals(formattedPlayer.name, viewModel.playerInfo.name)
        assertEquals(formattedPlayer.rank, viewModel.playerInfo.rank)
        assertEquals(formattedPlayer.email, viewModel.playerInfo.email)
    }

    @Test
    fun successFormattedPlayer(){
        //prepare
        val name = "Luis Gustavo"
        val rank = "1"
        val email = "luis@email.com"

        //call
        val formattedPlayer = ShowProfileModel.FormattedPlayer(name = "Luis Gustavo", rank = "1", email = "luis@email.com")

        formattedPlayer.email = email
        formattedPlayer.name = name
        formattedPlayer.rank = rank

        //assert
        assertEquals(name, formattedPlayer.name)
        assertEquals(rank, formattedPlayer.rank)
        assertEquals(email, formattedPlayer.email)
    }

    @Test
    fun successShowProfileModel() {
        //prepare

        //call
        val model = ShowProfileModel()

        //assert
        assertNotNull(model)
    }

    @After
    fun tearDown() {

    }
}