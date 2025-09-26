class Direccion(
    val calle: String,
    val numero: Int,
    val ciudad: String,
    val comuna: String
) {
    override fun toString(): String {
        return "$calle $numero, $comuna, $ciudad"
    }
}