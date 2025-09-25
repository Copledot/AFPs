class LiquidacionSueldo(
    val periodo: String,
    val imponible: Double,
    val noImponible: Double,
    val descAfp: Double,
    val descSalud: Double,
    val descCesantia: Double,
    val totalDescuentos: Double,
    val sueldoLiquido: Double

){

    fun resumen(){


    }

    fun calcular(periodo: String, empleado: Empleado, tasaSalud: Double = 0.07, tasaCesantia: Double = 0.006){

    }


}