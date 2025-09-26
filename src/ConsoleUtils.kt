import java.util.Scanner

object ConsoleUtils {
    private val scanner = Scanner(System.`in`)

    fun leerLinea(mensaje: String = ""): String {
        if (mensaje.isNotEmpty()) {
            print("$mensaje: ")
        }
        return scanner.nextLine()
    }

    fun leerEntero(mensaje: String = ""): Int {
        while (true) {
            try {
                if (mensaje.isNotEmpty()) {
                    print("$mensaje: ")
                }
                return scanner.nextLine().toInt()
            } catch (e: NumberFormatException) {
                println("Por favor ingrese un número entero válido.")
            }
        }
    }

    fun leerDouble(mensaje: String = ""): Double {
        while (true) {
            try {
                if (mensaje.isNotEmpty()) {
                    print("$mensaje: ")
                }
                return scanner.nextLine().toDouble()
            } catch (e: NumberFormatException) {
                println("Por favor ingrese un número decimal válido.")
            }
        }
    }

    fun leerLineaToVocal(mensaje: String = ""): String {
        while (true) {
            val input = leerLinea(mensaje)
            if (input.lowercase() in listOf("a", "e", "i", "o", "u")) {
                return input.lowercase()
            }
            println("Por favor ingrese una vocal (a, e, i, o, u).")
        }
    }

    fun limpiarPantalla() {
        print("\u001b[H\u001b[2J")
        System.out.flush()
    }

    fun pausar(mensaje: String = "Presione Enter para continuar...") {
        print(mensaje)
        scanner.nextLine()
    }
}