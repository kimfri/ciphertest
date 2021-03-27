package hash

import java.security.MessageDigest
import javax.xml.bind.DatatypeConverter

fun main() {
    hash()
    hashSimplified()
}

fun hashSimplified() {
    val message = "Hello world"
    val doPrint = true
    hashValue(message, MD5, doPrint)
    hashValue(message, SHA256, doPrint)
    hashValue(message, SHA512, doPrint)
}

fun hash() {
    val msg = "Hello world!"
    md5(msg)
    sha256(msg)
    sha512(msg)
}

fun hashValue(message: String, algorithm: String, printHash: Boolean = false): String {
    val value = digestString(message, algorithm)
    if (printHash) { println("$value ($algorithm)") }
    return value
}

private fun sha512(message: String, printHash: Boolean = false): String {
    val algorithm = "SHA-512"
    val value = digestString(message, algorithm)
    if (printHash) {
        println("$value ($algorithm)")
    }
    return value
}

private fun sha256(message: String, printHash: Boolean = false): String {
    val algorithm = "SHA-256"
    val value = digestString(message, algorithm)
    if (printHash) {
        println("$value ($algorithm)")
    }
    return value
}

private fun md5(message: String, printHash: Boolean = false): String {
    val algorithm = "MD5"
    val value = digestString(message, algorithm)
    if (printHash) {
        println("$value ($algorithm)")
    }
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
