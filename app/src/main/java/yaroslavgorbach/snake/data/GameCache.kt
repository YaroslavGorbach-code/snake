package yaroslavgorbach.snake.data

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import yaroslavgorbach.snake.R
import yaroslavgorbach.snake.data.model.HighScore
import javax.inject.Inject


class GameCache @Inject constructor(private val context: Application) {

    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("DATASTORE_NAME")
        val HIGH_SCORES_KEY = stringPreferencesKey("DATASTORE_KEY_HIGH_SCORES")
        val PLAYER_NAME_KEY = stringPreferencesKey("DATASTORE_KEY_PLAYER_NAME")
    }

    val getHighScores: Flow<List<HighScore>> = context.dataStore.data.map { preferences ->
        val scores = preferences[HIGH_SCORES_KEY]
        val listType = object : TypeToken<List<HighScore?>?>() {}.type
        Gson().fromJson<List<HighScore>>(scores, listType) ?: listOf()
    }

    val getPlayerName: Flow<String> = context.dataStore.data.map { preferences ->
        preferences[PLAYER_NAME_KEY] ?: context.getString(R.string.default_player_name)
    }

    suspend fun saveHighScore(highScore: HighScore) {
        context.dataStore.edit { preferences ->
            preferences[HIGH_SCORES_KEY] = Gson().toJson(getHighScores.first().plus(highScore))
        }
    }

    suspend fun savePlayerName(name: String) {
        context.dataStore.edit { preferences ->
            preferences[PLAYER_NAME_KEY] = name
        }
    }
}