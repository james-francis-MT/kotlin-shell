import kotlin.system.exitProcess

fun main() {
    while (true) {
        print("$ ")

        val command = readln()
        val parts = command.split(" ", limit = 2)

        when (parts[0]) {
            "exit" -> exitProcess(0)
            "echo" -> println(parts[1])
            "type" -> handleType(parts)
            else -> println("$command: command not found")
        }
    }
}

fun handleType(parts: List<String>) {
    val validCommands = setOf("echo", "exit", "type")
    if (validCommands.contains(parts[1])) {
        println("${parts[1]} is a shell builtin")
        return
    }
    println("${parts[1]}: not found")
}
