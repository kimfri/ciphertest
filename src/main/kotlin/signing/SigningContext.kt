package signing

data class SigningContext(
    val keystore: String,
    val keystorePassword: CharArray,
    val alias: String,
    val intputFile: String = "message.txt",
    val path: String = "c:\\Users\\kimfr\\utv\\keys\\",
    val signatureFile: String = "digital_signature_2",
    val algorithm: String = "SHA256withRSA"
) {
    fun getKeystoreFilePath() = path + keystore

    fun getInputFilePath() = path + intputFile

    fun getSignatureFilePath() = path + signatureFile

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as SigningContext

        if (keystore != other.keystore) return false
        if (!keystorePassword.contentEquals(other.keystorePassword)) return false
        if (alias != other.alias) return false
        if (path != other.path) return false
        if (signatureFile != other.signatureFile) return false

        return true
    }

    override fun hashCode(): Int {
        var result = keystore.hashCode()
        result = 31 * result + keystorePassword.contentHashCode()
        result = 31 * result + alias.hashCode()
        result = 31 * result + path.hashCode()
        result = 31 * result + signatureFile.hashCode()
        return result
    }
}
