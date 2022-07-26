package ar.edu.unahur.obj2.impostoresPaises.paises

import kotlin.math.roundToInt

class Pais(
    var nombre: String = "",
    var codigoIso3: String = "",
    var poblacion: Int? = null,
    var superficie: Double? = null,
    var continente: String = "",
    var codigoMoneda: String = "",
    var cotizacionDolar: Double? = null,
    var paisesLimitrofes: MutableSet<Pais> = mutableSetOf(),
    var bloquesRegionales: MutableSet<String> = mutableSetOf(),
    var idiomasOficiales: MutableSet<String> = mutableSetOf()) {

    fun esPlurinacional(): Boolean {
        return idiomasOficiales.size > 1
    }

    fun esUnaIsla(): Boolean {
        return paisesLimitrofes.isEmpty()
    }

    fun densidadPoblacional(): Int {
        return if (poblacion!=null && superficie!=null) { (poblacion!! / superficie!!).roundToInt() }
                else error("Población o superficie no especificada")
    }

    fun vecinoMasPoblado(): Pais {
        val prueba = paisesLimitrofes.map { it.poblacion }.contains(null)
        return if (poblacion!=null && !prueba){
            val limitrofeMasPoblado =  paisesLimitrofes.maxByOrNull{ it.poblacion!! }
            if (limitrofeMasPoblado!!.poblacion!! > poblacion!!) limitrofeMasPoblado else this
        } else error("Población del país o sus limítrofes no especificada")
    }

    fun esLimitrofeCon(pais: Pais): Boolean {
        return paisesLimitrofes.contains(pais)
    }

    fun necesitanTraduccion(pais: Pais): Boolean {
        return idiomasOficiales.intersect(pais.idiomasOficiales).isEmpty()
    }

    fun sonPotencialesAliados(pais: Pais): Boolean {
        return !this.necesitanTraduccion(pais) && this.compartenBloqueRegional(pais)
    }

    fun convieneIrDeCompras(pais: Pais): Boolean {
        return if (pais.cotizacionDolar!=null && cotizacionDolar!= null) pais.cotizacionDolar!! > cotizacionDolar!!
                else error("Cotización del dolar origen o destino no especificada")
    }

    fun aCuantoEquivale(valor: Double, pais: Pais): Double {
        return if (pais.cotizacionDolar!=null && cotizacionDolar!= null) pais.dolarAMonedaLocal(this.monedaLocalADolar(valor))
                else error("Cotización del dolar origen o destino no especificada")
    }

    fun compartenBloqueRegional(pais: Pais): Boolean {
        return bloquesRegionales.intersect(pais.bloquesRegionales).isNotEmpty()
    }

    fun monedaLocalADolar(valor: Double): Double {
        return valor / this.cotizacionDolar!!
    }

    fun dolarAMonedaLocal(valor: Double): Double {
        return valor * this.cotizacionDolar!!
    }

    fun añadirPaisLimitrofe(pais: Pais) {
        paisesLimitrofes.add(pais)
    }
}