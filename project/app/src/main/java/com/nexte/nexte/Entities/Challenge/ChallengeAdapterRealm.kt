package com.nexte.nexte.Entities.Challenge

import com.nexte.nexte.Entities.Challenge.Helper.CancelledRealm
import com.nexte.nexte.Entities.Challenge.Helper.PlayedRealm
import io.realm.Realm
import io.realm.RealmResults
import io.realm.kotlin.where

class ChallengeAdapterRealm: ChallengeAdapter {

    var realm: Realm = Realm.getDefaultInstance()

    override fun get(identifier: String): Challenge? {

        val challengeRealm = realm.where<ChallengeRealm>().equalTo("id", identifier).findFirst()
        return convertChallengeRealmToChallenge(challengeRealm)
    }

    override fun getAll(): List<Challenge> {

        val challengeRealmResults = realm.where<ChallengeRealm>().findAll()
        return convertListChallengeRealmToChallengeList(challengeRealmResults)
    }

    override fun updateOrInsert(challenge: Challenge): Challenge? {

        convertChallengeToChallengeRealm(challenge)?.let {
            realm.beginTransaction()
            realm.insertOrUpdate(it)
            realm.commitTransaction()
            return challenge
        }
        return null
    }

    override fun delete(identifier: String): Challenge? {

        val challengeRealm = realm.where<ChallengeRealm>().equalTo("id", identifier).findAll()
        realm.beginTransaction()
        val challenge = convertChallengeRealmToChallenge(challengeRealm.first())
        challengeRealm.deleteAllFromRealm()
        realm.commitTransaction()
        return challenge
    }

    private fun convertChallengeToChallengeRealm(challenge: Challenge?): ChallengeRealm? {

        var challengeRealm: ChallengeRealm? = null

        challenge?.let {
            challengeRealm = ChallengeRealm().apply {
                this.id = it.id
                this.challengerId = it.challengerId
                this.challengedId = it.challengedId
                this.challegeDate = it.challengeDate
                when (it.stage) {
                    is Challenge.Stage.Played -> {
                        this.stagePlayedRealm = PlayedRealm(it.stage)
                    }
                    is Challenge.Stage.Canceled -> {
                        this.stageCancelledRealm = CancelledRealm(it.stage)
                    }
                    is Challenge.Stage.Scheduled -> {
                        // Do Nothing
                    }
                }
            }
        }
        return challengeRealm
    }

    private fun convertChallengeRealmToChallenge(challengeRealm: ChallengeRealm?): Challenge? {

        var challenge: Challenge? = null

        challengeRealm?.let {

            var stage: Challenge.Stage? = null

            if (challengeRealm.stagePlayedRealm != null) {
                challengeRealm.stagePlayedRealm?.let {

                    stage = Challenge.Stage.Played(it.setChallenger,
                            it.setChallenged,
                            it.date!!,
                            Challenge.Stage.Played.Game(it.firstGame!!.gameChallenger, it.firstGame!!.gameChallenged),
                            Challenge.Stage.Played.Game(it.secondGame!!.gameChallenger, it.secondGame!!.gameChallenged),
                            Challenge.Stage.Played.Game(it.thirdGame!!.gameChallenger, it.thirdGame!!.gameChallenged),
                            Challenge.Stage.Played.Game(it.fourthGame!!.gameChallenger, it.fourthGame!!.gameChallenged),
                            Challenge.Stage.Played.Game(it.fifthGame!!.gameChallenger, it.fifthGame!!.gameChallenged),
                            it.detail)
                }
            } else if (challengeRealm.stageCancelledRealm != null) {
                challengeRealm.stageCancelledRealm?.let {
                    stage = Challenge.Stage.Canceled(it.reason,
                            Challenge.UserType.valueOf(it.userType),
                            it.date!!)
                }
            } else {
                  stage = Challenge.Stage.Scheduled()
            }

            challenge = Challenge(it.id,
                    it.challengerId,
                    it.challengedId,
                    it.challegeDate,
                    Challenge.Status.valueOf(it.status),
                    stage!!)
        }

        return challenge
    }

    private fun convertListChallengeRealmToChallengeList(challengeRealmResults: RealmResults<ChallengeRealm>): List<Challenge> {

        val challenges: MutableList<Challenge> = mutableListOf()
        for(challengeRealm in challengeRealmResults) {
            convertChallengeRealmToChallenge(challengeRealm)?.let {
                challenges.add(it)
            }
        }
        return challenges.toList()
    }

}