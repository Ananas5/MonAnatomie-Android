package com.example.monanatomie

import android.content.Context
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore by preferencesDataStore(name = "user_prefs")


object PreferencesKeys {
    // définir les 2 clés utiliser dans profil
    val NAME = stringPreferencesKey("name")
    val AVATAR = intPreferencesKey("avatar")
}

suspend fun saveProfile(context: Context, name: String, avatar: Int) {
    // sauvergarder les données de profil
    context.dataStore.edit { prefs ->
        prefs[PreferencesKeys.NAME] = name
        prefs[PreferencesKeys.AVATAR] = avatar
    }
}

fun getProfile(context: Context): Flow<Pair<String, Int>> {
    //pouvoir lire les données
    return context.dataStore.data.map { prefs ->
        val name = prefs[PreferencesKeys.NAME] ?: "Super Explorateur !"
        val avatar = prefs[PreferencesKeys.AVATAR] ?: R.drawable.avatar1
        name to avatar
    }
}