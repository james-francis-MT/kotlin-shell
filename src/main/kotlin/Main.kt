import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import kotlin.io.path.Path
import kotlin.io.path.listDirectoryEntries
import kotlin.io.path.name
import kotlin.system.exitProcess

val pathList = System.getenv("PATH").split(":").filter { Files.exists(Path(it)) }

fun main() {
    while (true) {
        print("$ ")

        val command = readln()
        val parts = command.split(" ", limit = 2)
        val cmd = parts[0]
        val args = if (parts.count() == 2) parts[1] else null

        when (cmd) {
            "exit" -> exitProcess(0)
            "echo" -> println(args)
            "type" -> handleType(args)
            "pwd" -> println(Paths.get("").toAbsolutePath().toString())
            else -> handleUnknown(cmd, args)
        }
    }
}

fun handleType(arg: String?) {
    if (arg == null) {
        println("Type command requires 1 argument")
        return
    }
    val validCommands = setOf("echo", "exit", "type", "pwd")
    if (validCommands.contains(arg)) {
        println("$arg is a shell builtin")
        return
    }
    when (val match = pathMatch(arg)) {
        null -> println("${arg}: not found")
        else -> println("$arg is ${match.toString()}")
    }
}

fun handleUnknown(command: String, args: String?) {
    val match = pathMatch(command)
    if (match != null) {
        ProcessBuilder(command, args)
            .redirectOutput(ProcessBuilder.Redirect.INHERIT)
            .redirectError(ProcessBuilder.Redirect.INHERIT)
            .start().waitFor()
        return
    }
    println("$command: command not found")
}

fun pathMatch(arg: String): Path? {
    for (path in pathList) {
        val filesAtPath = Path(path).listDirectoryEntries()
        val match = filesAtPath.find { it.name == arg }
        if (match != null) {
            return match
        }
    }
    return null
}
