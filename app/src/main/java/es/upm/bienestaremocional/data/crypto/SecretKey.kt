package es.upm.bienestaremocional.data.crypto

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import java.security.KeyStore
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey

object SecretKey {
    private const val secretKeyAlias = "database_key"
    private const val provider = "AndroidKeyStore"
    private const val algorithm = KeyProperties.KEY_ALGORITHM_AES
    private const val cipherMode = KeyProperties.BLOCK_MODE_GCM

    private fun generateSecretKey(): SecretKey {
        val keyGenerator = KeyGenerator.getInstance(algorithm, provider)
        val spec = KeyGenParameterSpec
            .Builder(secretKeyAlias, KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT)
            .setBlockModes(cipherMode)
            .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
            .build()

        keyGenerator.init(spec)
        return keyGenerator.generateKey()
    }

    fun getSecretKey(): SecretKey {
        // Get keyStore instance, no parameters at load stage are needed
        val keyStore = KeyStore.getInstance(provider).apply { load(null) }
        // Get key entry
        val secretKeyEntry = keyStore.getEntry(secretKeyAlias, null) as KeyStore.SecretKeyEntry?
        // Get the key itself or if is null generate it
        return secretKeyEntry?.secretKey ?: generateSecretKey()
    }
}

