class Empleado(
    val rut: String,
    val nombre: String,
    val apellido: String,
    val sueldoBase: Double,
    val direccion: Direccion,
    val afp: AFP
) {
    override fun toString(): String {
        return "Empleado: rut ='$rut', nombre = '$nombre', apellido = '$apellido', sueldoBase = $sueldoBase, direccion = $direccion, afp = ${afp.nombre})"
    }
}