package com.cromulent.cartio.utils.security

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec

object TokenManager {
    private val keyStore = KeyStore.getInstance("AndroidKeyStore").apply { load(null) }
    private const val KEY_ALIAS = "api_key_alias"
    
    init {
        if (!keyStore.containsAlias(KEY_ALIAS)) {
            generateKey()
        }
    }
    
    private fun generateKey() {
        val keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore")
        val keyGenParameterSpec = KeyGenParameterSpec.Builder(
            KEY_ALIAS,
            KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
        )
            .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
            .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7)
            .build()
        
        keyGenerator.init(keyGenParameterSpec)
        keyGenerator.generateKey()
    }
    
    fun encryptToken(token: String): Pair<ByteArray, ByteArray> {
        val cipher = Cipher.getInstance("AES/CBC/PKCS7Padding")
        val key = keyStore.getKey(KEY_ALIAS, null) as SecretKey
        cipher.init(Cipher.ENCRYPT_MODE, key)
        
        val encryptedData = cipher.doFinal(token.toByteArray())
        return Pair(encryptedData, cipher.iv)
    }
    
    fun decryptToken(encryptedData: ByteArray, iv: ByteArray): String {
        val cipher = Cipher.getInstance("AES/CBC/PKCS7Padding")
        val key = keyStore.getKey(KEY_ALIAS, null) as SecretKey
        cipher.init(Cipher.DECRYPT_MODE, key, IvParameterSpec(iv))
        
        return String(cipher.doFinal(encryptedData))
    }
}