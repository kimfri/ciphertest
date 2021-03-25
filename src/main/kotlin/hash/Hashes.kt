package hash

import java.security.MessageDigest
import javax.xml.bind.DatatypeConverter
import javax.xml.crypto.dsig.DigestMethod

fun main() {
    test1()
}

fun test1() {
    val msg = "Hello world!"
    val messageDigest = MessageDigest.getInstance("MD5")
    messageDigest.update(msg.toByteArray())
    val digest = messageDigest.digest()
    val value = DatatypeConverter.printHexBinary(digest).toUpperCase()
    print("$value")
}