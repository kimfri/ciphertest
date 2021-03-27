package hash

import java.io.File
import java.io.FileInputStream
import java.security.DigestInputStream
import java.security.MessageDigest
import javax.xml.bind.DatatypeConverter


class HashStream {

    fun getHashCodeForFile(file: File): String {
        val dataSize = 1024 * 1024 * 100
        val trashData = ByteArray(dataSize)
        val md = MessageDigest.getInstance("MD5")
        FileInputStream(file)
            .use { fis ->
                DigestInputStream(fis, md)
                    .use { dis ->
                        while (dis.read(trashData) == dataSize) {
                        }
                    }
            }
        val digest = md.digest()
        return DatatypeConverter.printHexBinary(digest).toUpperCase()
    }
}