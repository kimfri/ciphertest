package hash

import java.security.MessageDigest
import javax.xml.bind.DatatypeConverter

class HashCreator {

    fun hashSimplified() {
        val message = "Hello world"
        val doPrint = true
        getHashValue(message, MD5, doPrint)
        getHashValue(message, SHA256, doPrint)
        getHashValue(message, SHA512, doPrint)
    }

    fun getHashValue(message: String,
                             algorithm: String,
                             printHash: Boolean = false
    ): String {
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

    companion object {

        const val MD5 = "MD5"
        const val SHA256 = "SHA-256"
        const val SHA512 = "SHA-512"
    }
}
