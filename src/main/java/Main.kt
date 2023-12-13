import concurrencia.Directorio
import kotlinx.coroutines.runBlocking
import java.nio.file.Paths

object Main {
    @JvmStatic
    fun main(args: Array<String>) = runBlocking {
        val path = "/home/carlos/Data/docs"
        val directorio = Directorio()

        val numPruebas = 30
        val tiemposHilo : MutableList<Long> = mutableListOf(0)
        val tiemposCorrutina : MutableList<Long> = mutableListOf(0)

        var inicio : Long
        var fin : Long
        for (i in 1..numPruebas) {
            directorio.contador.set(0)
            inicio = System.nanoTime()
            directorio.contarArchivosEnDirectorioHilo(Paths.get(path))
            fin = System.nanoTime()
            tiemposHilo.add(fin - inicio)

            directorio.contador.set(0)
            inicio = System.nanoTime()
            directorio.contarArchivosEnDirectorioCorrutina(Paths.get(path))
            fin = System.nanoTime()
            tiemposCorrutina.add(fin - inicio)
        }

        println("MEAN HILO -> ${tiemposHilo.reduce{ ac, next -> ac + next } / numPruebas}")
        println("MEAN CORRUTINA -> ${tiemposCorrutina.reduce{ ac, next -> ac + next } / numPruebas}")
    }
}