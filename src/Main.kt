import java.time.LocalDate

fun main() {

    Repositorio.inicializarDatos()

    var opcion: Int
    do {
        mostrarMenu()
        opcion = ConsoleUtils.leerEntero("Seleccione una opción")

        when (opcion) {
            1 -> listarEmpleados()
            2 -> agregarEmpleado()
            3 -> generarLiquidacionPorRUT()
            4 -> listarLiquidaciones()
            5 -> filtrarEmpleadosPorAFP()
            6 -> eliminarEmpleado()
            7 -> ordenarEmpleadosPorSueldoLiquido()
            8 -> mostrarTotalDescuentosNomina()
            0 -> println("Saliendo del sistema...")
            else -> println("Opción no válida. Por favor intente nuevamente.")
        }

        if (opcion != 0) {
            ConsoleUtils.pausar()
            ConsoleUtils.limpiarPantalla()
        }
    } while (opcion != 0)
}

fun mostrarMenu() {
    println("===== SISTEMA DE LIQUIDACIÓN DE SUELDOS =====")
    println("1. Listar empleados")
    println("2. Agregar empleado")
    println("3. Generar liquidación por RUT")
    println("4. Listar liquidaciones")
    println("5. Filtrar empleados por AFP")
    println("6. Eliminar empleado")
    println("7. Ordenar empleados por sueldo líquido (mayor a menor)")
    println("8. Mostrar total de descuentos de la nómina")
    println("0. Salir")
}

fun listarEmpleados() {
    println("\n===== LISTA DE EMPLEADOS =====")
    val empleados = Repositorio.empleados

    if (empleados.isEmpty()) {
        println("No hay empleados registrados.")
        return
    }

    empleados.forEach { empleado ->
        println(empleado)
        println("----------------------")
    }
}

fun agregarEmpleado() {
    println("\n===== AGREGAR NUEVO EMPLEADO =====")

    val rut = ConsoleUtils.leerLinea("Ingrese RUT del empleado")
    val nombre = ConsoleUtils.leerLinea("Ingrese nombre del empleado")
    val apellido = ConsoleUtils.leerLinea("Ingrese apellido del empleado")
    val sueldoBase = ConsoleUtils.leerDouble("Ingrese sueldo base del empleado")

    println("\nIngrese dirección del empleado:")
    val calle = ConsoleUtils.leerLinea("Calle")
    val numero = ConsoleUtils.leerEntero("Número")
    val ciudad = ConsoleUtils.leerLinea("Ciudad")
    val comuna = ConsoleUtils.leerLinea("Comuna")
    val direccion = Direccion(calle, numero, ciudad, comuna)

    println("\nListado de AFPs disponibles:")
    Repositorio.afps.forEachIndexed { index, afp ->
        println("${index + 1}. ${afp.nombre} (Tasa: ${afp.tasa})")
    }

    val afpIndex = ConsoleUtils.leerEntero("Seleccione una AFP") - 1
    if (afpIndex < 0 || afpIndex >= Repositorio.afps.size) {
        println("Índice de AFP no válido.")
        return
    }

    val afp = Repositorio.afps[afpIndex]
    val empleado = Empleado(rut, nombre, apellido, sueldoBase, direccion, afp)
    Repositorio.agregarEmpleado(empleado)

    println("\nEmpleado agregado exitosamente:")
    println(empleado)
}

fun generarLiquidacionPorRUT() {
    println("\n===== GENERAR LIQUIDACIÓN POR RUT =====")
    val rut = ConsoleUtils.leerLinea("Ingrese RUT del empleado")

    val empleado = Repositorio.obtenerEmpleado(rut)
    if (empleado == null) {
        println("No se encontró un empleado con el RUT ingresado.")
        return
    }

    val periodo = LocalDate.now()
    val liquidacion = LiquidacionSueldo(periodo, empleado)
    Repositorio.agregarLiquidacion(liquidacion)

    val detalle = liquidacion.calcular()
    println("\n===== LIQUIDACIÓN DE SUELDO =====")
    println("Periodo: ${detalle.periodo}")
    println("Empleado: ${detalle.nombreEmpleado} (RUT: ${detalle.rutEmpleado})")
    println("Sueldo Base: $${detalle.sueldoBase}")
    println("Descuento AFP (${empleado.afp.nombre} - ${empleado.afp.tasa * 100}%): $${detalle.descuentoAFP}")
    println("Descuento Salud (7%): $${detalle.descuentoSalud}")
    println("Seguro de Cesantía (0.6%): $${detalle.seguroCesantia}")
    println("Total Descuentos: $${detalle.totalDescuentos}")
    println("Sueldo Líquido: $${detalle.sueldoLiquido}")
}

fun listarLiquidaciones() {
    println("\n===== LISTA DE LIQUIDACIONES =====")
    val liquidaciones = Repositorio.liquidaciones

    if (liquidaciones.isEmpty()) {
        println("No hay liquidaciones registradas.")
        return
    }

    liquidaciones.forEach { liquidacion ->
        val detalle = liquidacion.calcular()
        println("Liquidación - Periodo: ${detalle.periodo}, Empleado: ${detalle.nombreEmpleado}, Sueldo Líquido: $${detalle.sueldoLiquido}")
    }
}

fun filtrarEmpleadosPorAFP() {
    println("\n===== FILTRAR EMPLEADOS POR AFP =====")

    println("Listado de AFPs disponibles:")
    Repositorio.afps.forEachIndexed { index, afp ->
        println("${index + 1}. ${afp.nombre}")
    }

    val afpIndex = ConsoleUtils.leerEntero("Seleccione una AFP") - 1
    if (afpIndex < 0 || afpIndex >= Repositorio.afps.size) {
        println("Índice de AFP no válido.")
        return
    }

    val nombreAFP = Repositorio.afps[afpIndex].nombre
    val empleadosFiltrados = Repositorio.filtrarEmpleadosPorAFP(nombreAFP)

    println("\nEmpleados con AFP $nombreAFP:")
    if (empleadosFiltrados.isEmpty()) {
        println("No hay empleados con la AFP seleccionada.")
    } else {
        empleadosFiltrados.forEach { empleado ->
            println(empleado)
            println("----------------------")
        }
    }
}

fun eliminarEmpleado() {
    println("\n===== ELIMINAR EMPLEADO =====")
    val rut = ConsoleUtils.leerLinea("Ingrese RUT del empleado a eliminar")

    if (Repositorio.eliminarEmpleado(rut)) {
        println("Empleado con RUT $rut eliminado exitosamente.")
    } else {
        println("No se encontró un empleado con el RUT ingresado.")
    }
}

fun ordenarEmpleadosPorSueldoLiquido() {
    println("\n===== EMPLEADOS ORDENADOS POR SUELDO LÍQUIDO (MAYOR A MENOR) =====")

    val empleadosConSueldoLiquido = Repositorio.empleados.map { empleado ->
        val liquidacion = LiquidacionSueldo(LocalDate.now(), empleado)
        val detalle = liquidacion.calcular()
        Pair(empleado, detalle.sueldoLiquido)
    }.sortedByDescending { it.second }

    if (empleadosConSueldoLiquido.isEmpty()) {
        println("No hay empleados registrados.")
        return
    }

    empleadosConSueldoLiquido.forEach { (empleado, sueldoLiquido) ->
        println("Empleado: ${empleado.nombre} ${empleado.apellido}, RUT: ${empleado.rut}")
        println("Sueldo Base: $${empleado.sueldoBase}, Sueldo Líquido: $${sueldoLiquido}")
        println("----------------------")
    }
}

fun mostrarTotalDescuentosNomina() {
    println("\n===== TOTAL DE DESCUENTOS DE LA NÓMINA =====")

    if (Repositorio.empleados.isEmpty()) {
        println("No hay empleados registrados.")
        return
    }

    var totalDescuentosAFP = 0.0
    var totalDescuentosSalud = 0.0
    var totalSeguroCesantia = 0.0
    var totalDescuentos = 0.0

    Repositorio.empleados.forEach { empleado ->
        val liquidacion = LiquidacionSueldo(LocalDate.now(), empleado)
        val detalle = liquidacion.calcular()

        totalDescuentosAFP += detalle.descuentoAFP
        totalDescuentosSalud += detalle.descuentoSalud
        totalSeguroCesantia += detalle.seguroCesantia
        totalDescuentos += detalle.totalDescuentos
    }

    println("Total Descuentos AFP: $${totalDescuentosAFP}")
    println("Total Descuentos Salud (7%): $${totalDescuentosSalud}")
    println("Total Seguro de Cesantía (0.6%): $${totalSeguroCesantia}")
    println("Total General de Descuentos: $${totalDescuentos}")
}