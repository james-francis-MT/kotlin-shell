import kotlin.system.exitProcess

fun main() {
    while (true) {
        print("$ ")

        val input = readln()
        if (input == "exit 0") {
            exitProcess(0)
        }
        println("$input: command not found")
    }
}
