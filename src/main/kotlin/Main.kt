import kotlin.system.exitProcess

fun main() {
    while (true) {
        print("$ ")

        val command = readln()
        val parts = command.split(" ", limit = 2)

        if (command == "exit 0") {
            exitProcess(0)
        } else if (parts[0] == "echo") {
            println(parts[1])
        } else {
            println("$command: command not found")
        }
    }
}
