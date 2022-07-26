package ar.edu.unahur.obj2.impostoresPaises.paises

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.collections.shouldContainAll
import io.kotest.matchers.shouldBe

class PaisTest: DescribeSpec({
    val builder = PaisConcreteBuilder()

    builder.reset()
    builder.setPoblacion(47327407)
    builder.setIdiomasOficiales(mutableSetOf("Español"))
    builder.setCotizacionDelDolar(190.0)
    builder.setBloquesRegionales(mutableSetOf("SELA"))
    val argentina = builder.getResult()

    builder.reset()
    builder.setPoblacion(213993441)
    builder.setIdiomasOficiales(mutableSetOf("Portugues"))
    builder.setBloquesRegionales(mutableSetOf("SELA"))
    val brasil = builder.getResult()

    builder.reset()
    builder.setPoblacion(7333562)
    builder.setIdiomasOficiales(mutableSetOf("Español", "Guarani"))
    builder.setBloquesRegionales(mutableSetOf("SELA"))
    val paraguay = builder.getResult()

    builder.reset()
    builder.setCotizacionDelDolar(6.89)
    var bolivia = builder.getResult()

    builder.reset()
    builder.setBloquesRegionales(mutableSetOf("AL"))
    builder.setIdiomasOficiales(mutableSetOf("Español"))
    val chile = builder.getResult()

    val peru = Pais()


    brasil.añadirPaisLimitrofe(argentina)
    brasil.añadirPaisLimitrofe(paraguay)

    argentina.añadirPaisLimitrofe(brasil)
    argentina.añadirPaisLimitrofe(paraguay)

    paraguay.añadirPaisLimitrofe(brasil)
    paraguay.añadirPaisLimitrofe(argentina)


    describe("Un país") {
        describe("se crea uno nuevo") {
            it("lo inicializamos") { builder.reset() }

            builder.reset()

            builder.setNombre("Bolivia")
            builder.setCodigoIso3("BOL")
            builder.setContinente("América")
            builder.setPoblacion(10985059)
            builder.setSuperficie(1098581.0)
            builder.setCodigoMoneda("BOB")
            builder.setCotizacionDelDolar(6.89)
            builder.setPaisesLimitrofes(mutableSetOf(argentina, brasil, chile, paraguay, peru))
            builder.setBloquesRegionales(mutableSetOf("UNASUR"))
            builder.setIdiomasOficiales(mutableSetOf("Español", "Quechua", "Aymara"))

            bolivia = builder.getResult()

            describe("y comprobamos los datos"){
                it("su nombre") { bolivia.nombre.shouldBe("Bolivia") }
                it("su código ISO 3") { bolivia.codigoIso3.shouldBe("BOL") }
                it("su continente") { bolivia.continente.shouldBe("América") }
                it("su población") { bolivia.poblacion.shouldBe(10985059) }
                it("su superficie") { bolivia.superficie.shouldBe(1098581.0) }
                it("su código de moneda") { bolivia.codigoMoneda.shouldBe("BOB") }
                it("su cotización del dolar") { bolivia.cotizacionDolar.shouldBe(6.89) }
                it("sus paises limítrofes") { bolivia.paisesLimitrofes.shouldContainAll(argentina, brasil, chile, paraguay, peru) }
                it("su bloque regional") { bolivia.bloquesRegionales.shouldContain("UNASUR") }
                it("sus idiomas oficiales") { bolivia.idiomasOficiales.shouldContainAll("Español", "Quechua", "Aymara") }
            }
        }

        describe("es plurinacional"){
            it("con un solo idioma oficial"){ argentina.esPlurinacional().shouldBeFalse() }
            it("con más de un idioma oficial"){ paraguay.esPlurinacional().shouldBeTrue()}
        }

        describe("es insular (una isla)") {
            val cuba = Pais()
            val colombia = Pais()
            colombia.añadirPaisLimitrofe(peru)

            it("sin paises limitrofes") { cuba.esUnaIsla().shouldBeTrue() }
            it("con paises limitrofes") { colombia.esUnaIsla().shouldBeFalse()}
        }

        describe("calcula su densidad poblacional") {
            builder.reset()
            builder.setPoblacion(10985059)
            builder.setSuperficie(1098581.0)
            bolivia = builder.getResult()
            bolivia.densidadPoblacional().shouldBe(10)
        }

        describe("devuelve su vecino más poblado") {
            it("probando con el país más poblado"){
                brasil.vecinoMasPoblado().shouldBe(brasil)
            }
            it("probando con sus paises limitrofes"){
                argentina.vecinoMasPoblado().shouldBe(brasil)
                paraguay.vecinoMasPoblado().shouldBe(brasil)
            }
        }
    }

    describe("Dos paises") {
        describe("son limitrofes"){
            it("con paises que si se tienen en las listas"){
                brasil.esLimitrofeCon(argentina).shouldBeTrue()
                argentina.esLimitrofeCon(brasil).shouldBeTrue()
            }
            it("con paises que no se tienen"){
                peru.esLimitrofeCon(argentina).shouldBeFalse()
                argentina.esLimitrofeCon(peru).shouldBeFalse()
            }
        }

        describe("necesitan traducción"){
            it("con paises que hablan el mismo idioma"){
                argentina.necesitanTraduccion(paraguay).shouldBeFalse()
                paraguay.necesitanTraduccion(argentina).shouldBeFalse()
            }
            it("con paises que no lo hacen"){
                brasil.necesitanTraduccion(paraguay).shouldBeTrue()
                paraguay.necesitanTraduccion(brasil).shouldBeTrue()
            }
        }

        describe("son potenciales aliados"){
            it("con ambos requisitos cumplidos"){
                argentina.sonPotencialesAliados(paraguay).shouldBeTrue()
            }
            it("con solo un requisito cumplido"){
                chile.sonPotencialesAliados(paraguay).shouldBeFalse()
                brasil.sonPotencialesAliados(argentina).shouldBeFalse()
            }
            it("con ningún requisito cumplido"){
                brasil.sonPotencialesAliados(chile).shouldBeFalse()
            }
        }

        describe("conviene ir de compras"){
            it("con una cotización mayor"){
                argentina.convieneIrDeCompras(bolivia).shouldBeFalse()
            }
            it("con una cotización menor"){
                bolivia.convieneIrDeCompras(argentina).shouldBeTrue()
            }
        }

        describe("a cuanto equivale el dinero"){
            bolivia.aCuantoEquivale(689.0, argentina).shouldBe(19000.0)

            argentina.aCuantoEquivale(190.0, bolivia).shouldBe(6.89)
        }
    }
})