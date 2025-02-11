package ar.edu.unahur.obj2.impostoresPaises.paises

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.collections.shouldContainAll
import io.kotest.matchers.collections.shouldNotContain
import io.kotest.matchers.shouldBe

class ObservatorioTest: DescribeSpec ({
    Observatorio.conjuntoPaises.clear()

    val builder = PaisConcreteBuilder()

    builder.reset()
    builder.setNombre("Argentina")
    builder.setCodigoIso3("ARG")
    builder.setPoblacion(47327407)
    builder.setSuperficie(3761274.0)
    builder.setBloquesRegionales(mutableSetOf("SELA"))
    builder.setIdiomasOficiales(mutableSetOf("Español"))
    builder.setCotizacionDelDolar(190.0)
    val argentina = builder.getResult()

    builder.reset()
    builder.setNombre("Brasil")
    builder.setCodigoIso3("BRA")
    builder.setPoblacion(213993441)
    builder.setSuperficie(8515770.0)
    builder.setBloquesRegionales(mutableSetOf("SELA"))
    builder.setIdiomasOficiales(mutableSetOf("Portugues"))
    val brasil = builder.getResult()

    builder.reset()
    builder.setNombre("Paraguay")
    builder.setCodigoIso3("PRY")
    builder.setPoblacion(7333562)
    builder.setSuperficie(406752.0)
    builder.setBloquesRegionales(mutableSetOf("SELA"))
    builder.setIdiomasOficiales(mutableSetOf("Español", "Guarani"))
    val paraguay = builder.getResult()

    builder.reset()
    builder.setNombre("Chile")
    builder.setCodigoIso3("CHL")
    builder.setPoblacion(19212362)
    builder.setSuperficie(756700.0)
    builder.setBloquesRegionales(mutableSetOf("AL"))
    builder.setIdiomasOficiales(mutableSetOf("Español"))
    val chile = builder.getResult()

    builder.reset()
    builder.setNombre("Peru")
    builder.setCodigoIso3("PER")
    builder.setPoblacion(33359416)
    builder.setSuperficie(1285220.0)
    val peru = builder.getResult()

    builder.reset()
    builder.setNombre("Bolivia")
    builder.setCodigoIso3("BOL")
    builder.setPoblacion(10985059)
    builder.setSuperficie(1098581.0)
    builder.setIdiomasOficiales(mutableSetOf("Español"))
    builder.setCotizacionDelDolar(6.89)
    val bolivia = builder.getResult()

    builder.reset()
    builder.setNombre("Irlanda")
    builder.setContinente("Europa")
    builder.setIdiomasOficiales(mutableSetOf("Irlandes", "Ingles"))
    val irlanda = builder.getResult()

    builder.reset()
    builder.setNombre("Suiza")
    builder.setContinente("Europa")
    builder.setIdiomasOficiales(mutableSetOf("Aleman", "Frances", "Italiano", "Romanche"))
    val suiza = builder.getResult()

    builder.reset()
    builder.setNombre("Japon")
    builder.setContinente("Asia")
    builder.setPoblacion(125681593) // 333
    builder.setSuperficie(377974.0)
    builder.setIdiomasOficiales(mutableSetOf("Japones"))
    val japon = builder.getResult()

    builder.reset()
    builder.setNombre("Madagascar")
    builder.setContinente("Africa")
    builder.setPoblacion(28427333) // 48
    builder.setSuperficie(587295.0)
    builder.setIdiomasOficiales(mutableSetOf("Malgache", "Frances"))
    val madagascar = builder.getResult()

    builder.reset()
    builder.setNombre("Liberia")
    builder.setContinente("Africa")
    builder.setIdiomasOficiales(mutableSetOf("Ingles"))
    val liberia = builder.getResult()

    builder.reset()
    builder.setNombre("Islas Salomon")
    builder.setContinente("Oceania")
    builder.setPoblacion(703995) // 24
    builder.setSuperficie(28900.0)
    builder.setIdiomasOficiales(mutableSetOf("Ingles"))
    val islasSalomon = builder.getResult()

    val irlandaDelNorte = Pais()
    val francia = Pais()
    val costaDeMarfil = Pais()


    argentina.añadirPaisLimitrofe(brasil)
    argentina.añadirPaisLimitrofe(chile)
    argentina.añadirPaisLimitrofe(paraguay)
    brasil.añadirPaisLimitrofe(argentina)
    peru.añadirPaisLimitrofe(brasil)
    peru.añadirPaisLimitrofe(chile)
    bolivia.añadirPaisLimitrofe(paraguay)
    chile.añadirPaisLimitrofe(argentina)
    paraguay.añadirPaisLimitrofe(bolivia)
    irlanda.añadirPaisLimitrofe(irlandaDelNorte)
    suiza.añadirPaisLimitrofe(francia)
    liberia.añadirPaisLimitrofe(costaDeMarfil)


    Observatorio.añadirPais(argentina)
    Observatorio.añadirPais(brasil)
    Observatorio.añadirPais(paraguay)
    Observatorio.añadirPais(bolivia)
    Observatorio.añadirPais(chile)
    Observatorio.añadirPais(peru)



    describe("Probamos en el observatorio"){
        describe("son limitrofes"){
            it("con paises que si se tienen en las listas"){
                Observatorio.sonLimitrofes("Argentina", "Brasil").shouldBeTrue()
            }
            it("con paises que no se tienen"){
                Observatorio.sonLimitrofes("Argentina","Peru").shouldBeFalse()
            }
        }

        describe("necesitan traducción"){
            it("con paises que hablan el mismo idioma"){
                Observatorio.necesitanTraduccion("Argentina", "Paraguay").shouldBeFalse()
            }
            it("con paises que no lo hacen"){
                Observatorio.necesitanTraduccion("Brasil", "Paraguay").shouldBeTrue()
            }
        }

        describe("son potenciales aliados") {
            it("con ambos requisitos cumplidos"){
                Observatorio.sonPotencialesAliados("Argentina", "Paraguay").shouldBeTrue()
            }
            it("con solo un requisito cumplido"){
                Observatorio.sonPotencialesAliados("Chile", "Paraguay").shouldBeFalse()
                Observatorio.sonPotencialesAliados("Argentina", "Brasil").shouldBeFalse()
            }
            it("con ningún requisito cumplido"){
                Observatorio.sonPotencialesAliados("Brasil", "Chile").shouldBeFalse()
            }
        }

        describe("conviene ir de compras"){
            it("con una cotización mayor"){
                Observatorio.convieneIrDeCompras("Argentina", "Bolivia").shouldBeFalse()
            }
            it("con una cotización menor"){
                Observatorio.convieneIrDeCompras("Bolivia","Argentina").shouldBeTrue()
            }
        }

        describe("a cuanto equivale el dinero"){
            Observatorio.aCuantoEquivale(689.0, "Bolivia", "Argentina").shouldBe(19000.0)

            Observatorio.aCuantoEquivale(190.0, "Argentina", "Bolivia").shouldBe(6.89)
        }

        describe("la lista de los paises con mayor densidad poblacional") {
            Observatorio.cincoElementosConMayorDensPob().shouldContainAll("ARG", "BRA", "PRY", "CHL", "PER")
            Observatorio.cincoElementosConMayorDensPob().shouldNotContain("BOL")
        }

        Observatorio.añadirPais(irlanda)
        Observatorio.añadirPais(suiza)
        Observatorio.añadirPais(japon)
        Observatorio.añadirPais(madagascar)
        Observatorio.añadirPais(liberia)
        Observatorio.añadirPais(islasSalomon)

        describe("continente con mas paises plurinacionales") {
            Observatorio.continenteConMasPaisesPlurinacionales().shouldBe("Europa")
        }

        describe("densidad poblacional de paises insulares") {

            Observatorio.promedioDensPobPaisesInsulares().shouldBe(135)
        }
    }
})