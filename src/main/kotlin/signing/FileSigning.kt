package signing

import java.io.FileInputStream
import java.nio.file.Files
import java.nio.file.Paths
import java.security.KeyStore
import java.security.PrivateKey
import java.security.PublicKey
import java.security.Signature
import java.security.cert.Certificate
import java.security.cert.X509Certificate
import java.util.Date


//https://www.baeldung.com/java-digital-signature
private const val path = "c:\\Users\\kimfr\\utv\\keys\\"

fun main() {
//    signFile()
    verifyFileSignature()
    info()
}

private fun signFile() {
    val signingContext = SigningContext("sender_keystore.p12",
        "mypasswd".toCharArray(),
        "senderKeyPair")
    sign(signingContext)
}

private fun info() {
    val signingContext = SigningContext("sender_keystore.p12",
        "mypasswd".toCharArray(),
        "senderKeyPair")
    printKeystoreInfo(signingContext)
}

private fun verifyFileSignature() {
    val signingContext = SigningContext("receiver_keystore.p12",
        "mypasswd1".toCharArray(),
        "receiverKeyPair")
    verify(signingContext)
}

fun sign(signingContext: SigningContext) {
    println("<fun sign>")
    val keyStore = KeyStore.getInstance("PKCS12")
    keyStore.load(FileInputStream(signingContext.getKeystoreFilePath()), signingContext.keystorePassword)
    val privateKey = keyStore.getKey(signingContext.alias, signingContext.keystorePassword) as PrivateKey

    val signature = Signature.getInstance(signingContext.algorithm)
    signature.initSign(privateKey)

    val messageBytes = Files.readAllBytes(Paths.get(signingContext.getInputFilePath()))

    signature.update(messageBytes)
    val digitalSignature = signature.sign()

    Files.write(Paths.get(signingContext.getSignatureFilePath()), digitalSignature);
}

fun verify(signingContext: SigningContext) {
    println("<fun verify>")
    val keyStore = KeyStore.getInstance("PKCS12")
    keyStore.load(FileInputStream(signingContext.getKeystoreFilePath()), signingContext.keystorePassword)
    val certificate: Certificate = keyStore.getCertificate(signingContext.alias)
    val publicKey: PublicKey = certificate.publicKey

    val signature = Signature.getInstance(signingContext.algorithm)
    signature.initVerify(publicKey);

    val messageBytes = Files.readAllBytes(Paths.get(signingContext.getInputFilePath()))

    signature.update(messageBytes)
    val receivedSignature = Files.readAllBytes(Paths.get(signingContext.getSignatureFilePath()))
    val isCorrect = signature.verify(receivedSignature)
    println("Signature is correct: $isCorrect")
}

fun printKeystoreInfo(signingContext: SigningContext) {
    println("<fun printKeystoreInfo>")
    val keyStore = KeyStore.getInstance("PKCS12")
    keyStore.load(FileInputStream(signingContext.getKeystoreFilePath()), signingContext.keystorePassword)
    val privateKey = keyStore.getKey(signingContext.alias, signingContext.keystorePassword) as PrivateKey

    println("PK: ${privateKey.algorithm}")
    val date = keyStore.getCreationDate(signingContext.alias)
    println("Date: ${date.toString()}")

    val aliases = keyStore.aliases()
    while(aliases.hasMoreElements()){
        val alias = aliases.nextElement()
        val dueDate = (keyStore.getCertificate(alias) as X509Certificate).notAfter
        println("DueDate: $dueDate")
    }
    println("Is keystore valid: ${isKeystoreValid(keyStore)}")
}

fun isKeystoreValid(keyStore: KeyStore): Boolean {
    println("<fun isKeyStoreValid>")
//    val calendar: Calendar = GregorianCalendar(2021, 3, 3)
//    val currentDate = calendar.time
    val currentDate = Date()
    println("Current date is set to: $currentDate")
    var isValid = false
    val aliases = keyStore.aliases()
    while (aliases.hasMoreElements()) {
        val alias = aliases.nextElement()
        val dueDate = (keyStore.getCertificate(alias) as X509Certificate).notAfter
        isValid = currentDate < dueDate
    }
    return isValid
}


/**
 * //Let's generate a key pair using the genkeypair command:
keytool -genkeypair -alias senderKeyPair -keyalg RSA -keysize 2048 \
-dname "CN=Baeldung" -validity 365 -storetype PKCS12 \
-keystore sender_keystore.p12 -storepass changeit

 //publish public key
keytool -exportcert -alias senderKeyPair -storetype PKCS12 \
-keystore sender_keystore.p12 -file \
sender_certificate.cer -rfc -storepass changeit

//having access to the other partys public key, it can be imported
 //to a new keystore
keytool -importcert -alias receiverKeyPair -storetype PKCS12 \
-keystore receiver_keystore.p12 -file \
sender_certificate.cer -rfc -storepass changeit

 *
 *
 *
 * */
