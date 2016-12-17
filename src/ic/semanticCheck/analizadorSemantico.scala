package semanticCheck

import Ast._
import semanticCheck._

object analizadorSemantico {

  var tabsim: TablaSimbolos = new TablaSimbolos()

  def construirTabSimbol(nodo: AstNode) {
    var funinfo: FunInfo = null
    val tipo = ""
    while (nodo != null) nodo.getKind match {
      case "varDec" => tabsim.insertEntrada(nodo.getValor, nodo.getKind, nodo.getIndice)
      case "paramDecl" => tabsim.insertEntrada(nodo.getValor, nodo.getKind, nodo.getIndice)
      case "funDecl" => 
        funinfo = tabsim.lookupFunInfo(nodo.getValor)
        if (funinfo != null) {
          System.err.append("ERROR SEMANTICO, La función: " + nodo.getValor + " esta duplicada")
        }
        nodo.setTabSimbol(tabsim.nuevaTabSimbol("paramDecl"))
        tabsim.pushTabSimbol(nodo.getTabSimbol)
        funinfo = tabsim.nuevaFunInfo(nodo.getValor, nodo.getKind, nodo.getTabSimbol)
        tabsim.pushFunInfo(funinfo)
        construirTabSimbol(nodo.getHijoByIndex(0))
        construirTabSimbol(nodo.getHijoByIndex(1))
        tabsim.popTabSimbol()

    }
  }
}

class analizadorSemantico {

  var tabsim: TablaSimbolos = new TablaSimbolos()
  
  var arregloSinIndice: Boolean = false

  def revisarTabSim(nodo: AstNode): String = {
    assert((nodo != null))
    var entrada: Entrada = null
    var tipo = ""
    nodo.getKind match {
      case "varDec" | "paramDecl" | "funDecl" => System.err.append("ERROR, las declaraciones no pueden ser chequeadas en la symtab")
      case "compSt" | "selec" | "ciclo" | "retorno" | "vacio" | "expstmt" => System.err.append("ERROR, los estamentos no pueden ser chequeados en la symtab")
      case "expreAsig" => 
        tipo = revisarTabSim(nodo.getHijoByIndex(0))
        if (tipo != "noArray") {
          System.err.append("ERROR DE TIPO , el valor de la izquierda en la asignacion debe ser int")
        }
        tipo = revisarTabSim(nodo.getHijoByIndex(1))
        if (tipo != "noArray") {
          System.err.append("ERROR DE TIPO , el valor de la derecha en la asignacion debe ser int")
        }
        return "int"

      case "operador" => 
        tipo = revisarTabSim(nodo.getHijoByIndex(0))
        if (tipo != "noArray") {
          System.err.append("ERROR DE TIPO, la exprecion a la izquierda del operador: " + 
            nodo.getTipo + 
            " debe ser un int")
        }
        tipo = revisarTabSim(nodo.getHijoByIndex(1))
        if (tipo != "noArray") {
          System.err.append("ERROR DE TIPO, la exprecion a la derecha del operador: " + 
            nodo.getTipo + 
            " debe ser un int")
        }
        nodo.getTipo match {
          case "suma" | "resta" | "multiplicacion" | "division" => return "int"
          case "Menor igual" | "Mayor" | "Menor" | "Mayor igual" | "Igual igual" | "Distinto" => return "bool"
          case _ => System.err.append("ERROR, operador desconocido")
        }

      case "expfun" => 

        var funinfo = tabsim.lookupFunInfo(nodo.getValor)
        if (funinfo == null) {
          System.err.append("ERROR SEMANTICO, la funcion: " + nodo.getValor + " no esta definida")
        }
        entrada = funinfo.tabSimbol.root
        var param = nodo.getHijoByIndex(0)
        while (entrada != null && param != null) {
          if (entrada.tipo == "intArray" && param.getKind == "expreVar") {
            arregloSinIndice = true
          }
          tipo = revisarTabSim(param)
          arregloSinIndice = false
          if (tipo != entrada.tipo) {
            System.err.append("ERROR SEMANTICO, no hubo match entre el parametro: " + 
              param.getValor + 
              " y el parametro formal: " + 
              nodo.getValor + 
              " de la  funcion" + 
              entrada.name)
          }
          entrada = entrada.next
          param = param.getHermano
        }
        if (entrada != null || param != null) {
          System.err.append("ERROR SEMANTICO, no se pudo hacer match entre el paramentro " + 
            nodo.getValor + 
            " y la funcion ")
        }
        return funinfo.tipo

      case "expreVar" => 
        entrada = tabsim.lookupEntrada(nodo.getValor)
        if (entrada == null) {
          System.err.append("ERROR SEMANTICO, la variable: " + nodo.getValor + " no esta definida")
        }
        if (entrada.tipo == "intArray" && nodo.getKind == "intArray") {
          tipo = revisarTabSim(nodo.getHijoByIndex(0))
          if (tipo != "noArray") {
            System.err.append("ERROR DE TIPO, El indice del arreglo: " + nodo.getValor + 
              " debe ser int")
          }
          return "int"
        } else if (entrada.tipo == "intArray" && nodo.getKind == "noArray") {
          if (arregloSinIndice) {
            return "intArray"
          } else {
            System.err.append("ERROR DE TIPO, El arreglo: " + nodo.getValor + " deberia estar seguido por los corchetes []")
          }
        } else if (entrada.tipo == "noArray" && nodo.getKind == "intArray") {
          System.err.append("ERROR De TIPO, la variable: " + nodo.getValor + 
            " que no es arreglo no debe estar seguida por corchetes []")
        } else {
          assert((entrada.tipo == "noArray" && nodo.getKind == "noArray"))
          return "int"
        }

      case "constante" => return "int"
      case _ => System.err.append("ERROR, tipo de nodo desconocido")
    }
    "null"
  }
}
