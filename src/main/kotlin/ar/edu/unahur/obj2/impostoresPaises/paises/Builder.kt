package ar.edu.unahur.obj2.impostoresPaises.paises

interface Builder {
    fun reset()
    fun setNombre(value: String)
    fun setCodigoIso3(value: String)
    fun setPoblacion(value: Int)
    fun setSuperficie(value: Double)
    fun setContinente(value: String)
    fun setCodigoMoneda(value: String)
    fun setCotizacionDelDolar(value: Double)
    fun setPaisesLimitrofes(value: MutableSet<Pais>)
    fun setBloquesRegionales(value: MutableSet<String>)
    fun setIdiomasOficiales(value: MutableSet<String>)
    fun getResult(): Pais
}