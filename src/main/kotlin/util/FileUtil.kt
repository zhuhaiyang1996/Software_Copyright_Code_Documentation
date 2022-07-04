package util

import java.io.File
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.util.stream.Collectors

object FileUtil {

    // https://stackoverflow.com/questions/49419971/kotlin-get-list-of-all-files-in-resource-folder
    @JvmStatic
    fun getFileLists(path:String,extension: ArrayList<String>):List<File>{
        // val extensions = arrayListOf<String>(".kt",".kt1")
        val filesInFolder = Files.walk(Paths.get(path))
            .filter {
                // https://stackoverflow.com/questions/48096204/in-kotlin-how-to-check-contains-one-or-another-value
                    item -> extension.any() {item.toString().endsWith(it)}
            }
            .map(Path::toFile)
            .collect(Collectors.toList());
        return filesInFolder
    }
}