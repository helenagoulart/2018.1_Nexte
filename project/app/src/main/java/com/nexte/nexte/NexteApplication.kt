package com.nexte.nexte

import android.app.Application
import android.content.Context
import com.nexte.nexte.Entities.Challenge.ChallengeManager
import com.nexte.nexte.Entities.User.UserCategory.UserCategoryManager
import com.nexte.nexte.Entities.User.UserManager
import io.realm.Realm
import io.realm.RealmConfiguration

class NexteApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
        val config = RealmConfiguration.Builder().name("mockerRealm.realm").build()
        Realm.setDefaultConfiguration(config)

        val sharedPreference = getSharedPreferences("NexteAndroid", Context.MODE_PRIVATE)
        if (sharedPreference.getBoolean("FirstRun", true)) {
            UserCategoryManager().createInitialMocker()
            UserManager().createInitialMocker()
            sharedPreference.edit().putBoolean("FirstRun", false).apply()
        }

        if (sharedPreference.getBoolean("FirstRun_v2", true)) {
            ChallengeManager().createInitialMocker()
            sharedPreference.edit().putBoolean("FirstRun_v2", false).apply()
        }
    }
}