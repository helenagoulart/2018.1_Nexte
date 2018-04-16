package com.nexte.nexte.RankingScene

import android.content.Context
import android.widget.ArrayAdapter
import android.widget.BaseAdapter
import android.widget.ListView

/**
 * Created by albino on 02/04/18.
 */

class RankingModel {

    class Request

    class Response(var players: Array<Player>)

    class ViewModel(var formattedPlayers: List<FormattedPlayerInfo> )

    class Player(var name: String, var pictureURL: Int, var wins: Int, var losses: Int,
                        var rankPosition: Int)

    class FormattedPlayerInfo(var player: FormattedPlayer,
                              var shouldDrawChild: Boolean)

    class FormattedPlayer(var userName: String,
                                   var userPictureURL: Int,
                                   var userWins: String,
                                   var userLosses: String,
                                   var userRankPosition: String)
}
