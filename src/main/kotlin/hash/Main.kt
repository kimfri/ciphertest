package hash

import java.io.File

fun main() {
    val hashCreator = HashCreator()
    hashCreator.getHashValue("apa", HashCreator.MD5, true)

    val hashStream = HashStream()
//    val fileName = "c:\\Users\\kimfr\\Downloads\\DiscordSetup.exe"
    val fileName = "c:\\Users\\kimfr\\utv\\spring-tool-suite-3.9.9.RELEASE-e4.12.0-win32-x86_64.zip"
    val startTime = System.currentTimeMillis()
    val hashValue = hashStream.getHashCodeForFile(File(fileName))
    val stopTime = System.currentTimeMillis()
    val totalTime = stopTime - startTime
    println("stop: $stopTime - start $startTime ==$totalTime ms")
    println("Hashcode for discord is: $hashValue")
}
