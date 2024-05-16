package es.upm.bienestaremocional.data.crypto

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import java.security.KeyStore
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey

object SecretKey {
    private const val SECRET_KEY_ALIAS = "database_key"
    private const val PROVIDER = "AndroidKeyStore"
    private const val ALGORITHM = KeyProperties.KEY_ALGORITHM_AES
    private const val CIPHER_MODE = KeyProperties.BLOCK_MODE_GCM

    private fun generateSecretKey(): SecretKey {
        val keyGenerator = KeyGenerator.getInstance(ALGORITHM, PROVIDER)
        val spec = KeyGenParameterSpec
            .Builder(SECRET_KEY_ALIAS, KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT)
            .setBlockModes(CIPHER_MODE)
            .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
            .build()

        keyGenerator.init(spec)
        return keyGenerator.generateKey()
    }

    fun getSecretKey(): SecretKey {
        // Get keyStore instance, no parameters at load stage are needed
        val keyStore = KeyStore.getInstance(PROVIDER).apply { load(null) }
        // Get key entry
        val secretKeyEntry = keyStore.getEntry(SECRET_KEY_ALIAS, null) as KeyStore.SecretKeyEntry?
        // Get the key itself or if is null generate it
        return secretKeyEntry?.secretKey ?: generateSecretKey()
    }
}

