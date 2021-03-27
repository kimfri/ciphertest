package hash

import java.security.MessageDigest
import javax.xml.bind.DatatypeConverter

fun main() {
    hashSimplified()
}

fun hashSimplified() {
    val message = "Hello world"
    val doPrint = true
    hashValue(message, MD5, doPrint)
    hashValue(message, SHA256, doPrint)
    hashValue(message, SHA512, doPrint)
}

fun hashValue(message: String, algorithm: String, printHash: Boolean = false): String {
    val value = digestString(message, algorithm)
    if (printHash) { println("$value ($algorithm)") }
    return value
}

private fun digestString(message: String, algorithm: String): String {
    val messageDigest = MessageDigest.getInstance(algorithm)
    messageDigest.update(message.toByteArray())
    val digest = messageDigest.digest()
    return DatatypeConverter.printHexBinary(digest).toUpperCase()
}

private const val MD5 = "MD5"
private const val SHA256 = "SHA-256"
private const val SHA512 = "SHA-512"
