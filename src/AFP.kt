class AFP(
    val nombre: String,
    val tasa: Double // Tasa de descuento de AFP (ej: 0.1 para 10%)
) {
    override fun toString(): String {
        return "AFP(nombre='$nombre', tasa=$tasa)"
    }
}