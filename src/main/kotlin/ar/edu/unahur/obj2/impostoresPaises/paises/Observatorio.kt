package ar.edu.unahur.obj2.impostoresPaises.paises

object Observatorio {
    val conjuntoPaises = mutableSetOf<Pais>()

    fun sonLimitrofes(paisA: String, paisB: String): Boolean {
        val primerPais = conjuntoPaises.find { it.nombre == paisA }
        val segundoPais = conjuntoPaises.find { it.nombre == paisB }
        return if (primerPais!=null && segundoPais!=null) primerPais.esLimitrofeCon(segundoPais)
                else error("No se encontró pais/es con ese/os nombre/s")
    }

    fun necesitanTraduccion(paisA: String, paisB: String): Boolean {
        val primerPais = conjuntoPaises.find { it.nombre == paisA }
        val segundoPais = conjuntoPaises.find { it.nombre == paisB }
        return if (primerPais!=null && segundoPais!=null) primerPais.necesitanTraduccion(segundoPais)
                else error("No se encontró pais/es con ese/os nombre/s")
    }

    fun sonPotencialesAliados(paisA: String, paisB: String): Boolean {
        val primerPais = conjuntoPaises.find { it.nombre == paisA }
        val segundoPais = conjuntoPaises.find { it.nombre == paisB }
        return if (primerPais!=null && segundoPais!=null) primerPais.sonPotencialesAliados(segundoPais)
                else error("No se encontró pais/es con ese/os nombre/s")
    }

    fun convieneIrDeCompras(paisA: String, paisB: String): Boolean {
        val primerPais = conjuntoPaises.find { it.nombre == paisA }
        val segundoPais = conjuntoPaises.find { it.nombre == paisB }
        return if (primerPais!=null && segundoPais!=null) primerPais.convieneIrDeCompras(segundoPais)
                else error("No se encontró pais/es con ese/os nombre/s")
    }

    fun aCuantoEquivale(valor: Double, paisA: String, paisB: String): Double {
        val primerPais = conjuntoPaises.find { it.nombre == paisA }
        val segundoPais = conjuntoPaises.find { it.nombre == paisB }
        return if (primerPais!=null && segundoPais!=null) primerPais.aCuantoEquivale(valor, segundoPais)
                else error("No se encontró pais/es con ese/os nombre/s")
    }

    fun cincoElementosConMayorDensPob(): Set<String> {
        val listaPaises = conjuntoPaises.sortedByDescending { it.densidadPoblacional() }.toMutableList()
        while (listaPaises.size > 5) {
            listaPaises.removeLast()
        }
        return  listaPaises.map{ it.codigoIso3 }.toSet()
    }

    fun continenteConMasPaisesPlurinacionales(): String {
        var resultado = ""
        var numReferencia = 0
        val continentes = listOf("America", "Europa", "Asia", "Africa", "Oceania")
        continentes.forEach { cont ->
            val numPaisesPlurinacionales = conjuntoPaises.count { pais -> pais.continente == cont && pais.esPlurinacional() }
            if ( numPaisesPlurinacionales > numReferencia ) {
                resultado = cont
                numReferencia = numPaisesPlurinacionales
            }
        }
        return resultado
    }

    fun promedioDensPobPaisesInsulares(): Int {
        var totalDensPob = conjuntoPaises.sumBy { if (it.esUnaIsla()) it.densidadPoblacional() else 0 }
        var numPaisesInsulares = conjuntoPaises.count { it.esUnaIsla() }

        return totalDensPob / numPaisesInsulares
    }

    fun añadirPais(pais: Pais) {
        conjuntoPaises.add(pais)
    }
}