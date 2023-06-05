package utilidades

fun clean(texto: String): String {
    return texto.trimIndent()
        .replace("\n", "")
        .replace("    ", "")
        .replace("\": ", "\":")
}