object Repositorio {
    private val _afps = mutableListOf<AFP>()
    private val _empleados = mutableListOf<Empleado>()
    private val _liquidaciones = mutableListOf<LiquidacionSueldo>()

    val afps: List<AFP> = _afps
    val empleados: List<Empleado> = _empleados
    val liquidaciones: List<LiquidacionSueldo> = _liquidaciones

    // Métodos para AFP
    fun agregarAFP(afp: AFP) {
        _afps.add(afp)
    }

    fun obtenerAFP(nombre: String): AFP? {
        return _afps.find { it.nombre.equals(nombre, ignoreCase = true) }
    }

    // Métodos para Empleados
    fun agregarEmpleado(empleado: Empleado) {
        _empleados.add(empleado)
    }

    fun obtenerEmpleado(rut: String): Empleado? {
        return _empleados.find { it.rut == rut }
    }

    fun eliminarEmpleado(rut: String): Boolean {
        val empleado = obtenerEmpleado(rut)
        return if (empleado != null) {
            _empleados.remove(empleado)
            true
        } else {
            false
        }
    }

    fun filtrarEmpleadosPorAFP(nombreAFP: String): List<Empleado> {
        return _empleados.filter { it.afp.nombre.equals(nombreAFP, ignoreCase = true) }
    }

    // Métodos para Liquidaciones
    fun agregarLiquidacion(liquidacion: LiquidacionSueldo) {
        _liquidaciones.add(liquidacion)
    }

    fun obtenerLiquidacionesPorEmpleado(rut: String): List<LiquidacionSueldo> {
        return _liquidaciones.filter { it.empleado.rut == rut }
    }

    // Inicializar datos de ejemplo
    fun inicializarDatos() {
        // Agregar algunas AFPs
        agregarAFP(AFP("Cuprum", 0.0144))
        agregarAFP(AFP("Habitat", 0.0127))
        agregarAFP(AFP("PlanVital", 0.0116))
        agregarAFP(AFP("ProVida", 0.0145))
        agregarAFP(AFP("Capital", 0.0144))
        agregarAFP(AFP("Uno", 0.0049))
        agregarAFP(AFP("Modelo", 0.0058))

        // Crear algunas direcciones
        val direccion1 = Direccion("Av. Siempre Viva", 742, "Springfield", "Springfield")
        val direccion2 = Direccion("Calle Falsa", 123, "Santiago", "Providencia")
        val direccion3 = Direccion("Pasaje Central", 456, "Valparaíso", "Viña del Mar")

        // Crear algunos empleados
        val afp1 = obtenerAFP("Cuprum")!!
        val afp2 = obtenerAFP("Habitat")!!
        val afp3 = obtenerAFP("PlanVital")!!

        agregarEmpleado(Empleado("11.111.111-1", "Juan", "Perez", 1500000.0, direccion1, afp1))
        agregarEmpleado(Empleado("22.222.222-2", "María", "Gonzalez", 1800000.0, direccion2, afp2))
        agregarEmpleado(Empleado("33.333.333-3", "Carlos", "Rodriguez", 2000000.0, direccion3, afp3))
    }
}