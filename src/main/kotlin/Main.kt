import kotlin.system.exitProcess

val pathList = System.getenv("PATH").split(":")

fun main() {
    while (true) {
        print("$ ")

        val command = readln()
        val parts = command.split(" ", limit = 2)

        when (parts[0]) {
            "exit" -> exitProcess(0)
            "echo" -> println(parts[1])
            "type" -> handleType(parts[1])
            else -> println("$command: command not found")
        }
    }
}

fun handleType(arg: String) {
    val validCommands = setOf("echo", "exit", "type")
    if (validCommands.contains(arg)) {
        println("$arg is a shell builtin")
        return
    }
    val match = pathList.find { it.contains(arg) }
    if (match.toBoolean()) {
        println("$arg is $match}")
        return
    }
    println("${arg}: not found")
}
