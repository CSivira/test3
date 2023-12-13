package concurrencia

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.nio.file.Files
import java.nio.file.Path
import java.util.concurrent.Executors
import java.util.concurrent.atomic.AtomicInteger

class Directorio {
    var contador : AtomicInteger = AtomicInteger(0);

    // Faster
    suspend fun contarArchivosEnDirectorioCorrutina(directorio: Path) : AtomicInteger = coroutineScope {
        Files.walk(directorio, 1).forEach { path ->
            if (Files.isDirectory(path) && path != directorio) launch { contarArchivosEnDirectorioCorrutina(path) }
            else if (Files.isRegularFile(path)) launch { contador.incrementAndGet() }
        }
        contador
    }

    // Very bad but using threads
    fun contarArchivosEnDirectorioHilo(directorio: Path) : AtomicInteger {
        val executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors())

        Files.walk(directorio, 1).forEach { path ->
            if (Files.isDirectory(path) && path != directorio) executor.submit { contarArchivosEnDirectorioHilo(path) }
            else if (Files.isRegularFile(path)) executor.submit { contador.incrementAndGet() }
        }

        executor.shutdown()
        while (!executor.isTerminated) {}
        return contador
    }
}
