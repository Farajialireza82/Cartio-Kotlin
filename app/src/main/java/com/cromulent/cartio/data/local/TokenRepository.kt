package com.cromulent.cartio.data.local

import android.content.Context
import android.util.Base64
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.cromulent.cartio.utils.security.TokenManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TokenRepository(private val context: Context) {
    private val Context.dataStore by preferencesDataStore(name = "token_store")
    
    // Keys for DataStore
    private companion object {
        val ENCRYPTED_TOKEN = stringPreferencesKey("encrypted_token")
        val IV = stringPreferencesKey("iv")
    }

    suspend fun saveToken(token: String) {
        val (encryptedData, iv) = TokenManager.encryptToken(token)
        context.dataStore.edit { preferences ->
            preferences[ENCRYPTED_TOKEN] = Base64.encodeToString(encryptedData, Base64.DEFAULT)
            preferences[IV] = Base64.encodeToString(iv, Base64.DEFAULT)
        }
    }

    suspend fun deleteToken() {
        context.dataStore.edit { preferences ->
            preferences.clear()
        }
    }
    
    fun getToken(): Flow<String?> {
        return context.dataStore.data.map { preferences ->
            try {
                val encryptedData = preferences[ENCRYPTED_TOKEN]?.let {
                    Base64.decode(it, Base64.DEFAULT)
                } ?: return@map null
                
                val iv = preferences[IV]?.let {
                    Base64.decode(it, Base64.DEFAULT)
                } ?: return@map null

                TokenManager.decryptToken(encryptedData, iv)
            } catch (e: Exception) {
                null
            }
        }
    }
}