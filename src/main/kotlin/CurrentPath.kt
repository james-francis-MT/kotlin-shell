import java.nio.file.Path
import java.nio.file.Paths
import java.util.regex.Pattern
import kotlin.io.path.Path
import kotlin.io.path.exists

class CurrentPath {
    private var currentPath: Path = Paths.get("").toAbsolutePath()

    fun setPath(inputPath: String?) {
        if (inputPath == null) {
            println("Invalid command")
            return
        }
        if (inputPath == "~") {
            this.currentPath = Path(HOME)
            return
        }
        var newPath: String
        val backPaths = Pattern.compile(Regex.escape(".."))
            .matcher(inputPath)
            .results()
            .count()
        if (backPaths > 0) {
            newPath = this.currentPath.toString()
            for (i in 0..<backPaths) {
                newPath = newPath.substringBeforeLast("/")
            }
        } else {
            newPath = inputPath.replace(".", this.currentPath.toString())
        }
        val path = Path(newPath)
        if (path.exists()) {
            this.currentPath = path.toAbsolutePath()
        } else {
            println("cd: $newPath: No such file or directory")
        }
    }

    fun getPathString(): String {
        return this.currentPath.toString()
    }
}