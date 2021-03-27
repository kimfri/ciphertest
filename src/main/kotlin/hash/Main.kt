package hash

fun main() {
    val hashCreator = HashCreator()
//    hashCreator.hashSimplified()

    hashCreator.getHashValue("apa", HashCreator.MD5, true)
}
