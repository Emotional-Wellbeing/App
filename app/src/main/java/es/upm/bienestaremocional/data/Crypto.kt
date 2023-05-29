package es.upm.bienestaremocional.data

import java.security.MessageDigest
import java.util.UUID

/**
 * Converts a [ByteArray] into a hexadecimal [String]
 * Extracted from https://www.baeldung.com/kotlin/byte-arrays-to-hex-strings
 * @return Hexadecimal string
 */
private fun ByteArray.toHex(): String =
    joinToString(separator = "") { eachByte -> "%02x".format(eachByte) }

/**
 * Calculate hash using a [MessageDigest] and [String]
 * @param messageDigest MessageDigest with the desired algorithm
 * @param str String to compute
 * @return Hash computed in hexadecimal format
 */
private fun hash(messageDigest: MessageDigest, str: String) : String =
    messageDigest.digest(str.toByteArray(Charsets.UTF_8)).toHex()

/**
 * Computes the SHA-512 hash from a certain string
 * @param str String to compute
 * @return Hash computed in hexadecimal format
 */
fun sha512hash(str: String): String = hash(MessageDigest.getInstance("SHA-512"),str)

/**
 * Generates UUID using UUID class (hardware ID are nowadays deprecated and unfriendly to use
 * @return UUID generated in String format
 */
private fun generateRawUUID(): String = UUID.randomUUID().toString()

/**
 * Generates a User ID using randomUUID and sha256 hash
 * @return User ID in hexadecimal format
 */
fun generateUID() : String = sha512hash(generateRawUUID())

/**
 * Encrypt private user data
 * @return User ID in String format
 */
fun securePrivateData(message: String) : String = sha512hash(message)