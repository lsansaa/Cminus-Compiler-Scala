package semanticCheck

import Ast._
import Visitor.AstVisitor
import java.util.ArrayList
import semanticCheck._

object VisitorTipo {

  var indiNodo: Int = _

  var nodosRevisados: ArrayList[Integer] = new ArrayList()

  var tabsim: TablaSimbolos = new TablaSimbolos()
}

class VisitorTipo extends AstVisitor {

  var tabsim: TablaSimbolos = new TablaSimbolos()

  override def visit(visitor: AstProgram) {

  }

  override def visit(visitor: AstVarDec) {

  }

  override def visit(visitor: AstFunDec) {

  }

  override def visit(visitor: AstParamDec) {

  }

  override def visit(visitor: AstCompStmt) {

  }

  override def visit(visitor: AstVaciaStmt) {

  }

  override def visit(visitor: AstSelectStmt) {
    visitor.getHijoByIndex(0).revisarTipos(this)
    val tipo = visitor.getHijoByIndex(0).getTipoRevisado
    if (tipo != "Bool") {
      System.err.println("ERROR DE TIPO, la condicion del if en la linea: " + visitor.getLinea + 
        " debe ser un booleano")
      System.exit(1)
    }
    visitor.getHijoByIndex(1).revisarTipos(this)
    visitor.getHijoByIndex(2).revisarTipos(this)
  }

  override def visit(visitor: AstIterStmt) {
    visitor.getHijoByIndex(0).revisarTipos(this)
    val tipo = visitor.getHijoByIndex(0).getTipoRevisado
    if (!(tipo == "Bool")) {
      System.err.println("ERROR DE TIPO, en la linea: " + visitor.getLinea + " la condicion del ciclo debe ser del tipo bool")
      System.exit(1)
    }
    visitor.getHijoByIndex(1).revisarTipos(this)
  }

  override def visit(visitor: AstReturnStmt) {
    val funinfo = this.tabsim.topFunInfo()
    if (((funinfo.tipo) == "int") && ((visitor.getTipo) == "int")) {
      visitor.getHijoByIndex(0).revisarTipos(this)
      val tipo = visitor.getHijoByIndex(0).getTipoRevisado
      if (!(tipo == "int")) {
        System.err.println("ERROR DE TIPO, el retorno en la linea: " + visitor.getLinea + 
          " debe ser un valor entero")
        System.exit(1)
      }
    } else if (funinfo.tipo == "Void" && visitor.getTipo == "Void") {

    } else {
      assert(((((funinfo.tipo) == "int") && (visitor.getTipo == "void")) || 
        (((funinfo.tipo) == "void") && ((visitor.getTipo) == "int"))))
      System.err.println("ERROR DE TIPO, en la linea: " + visitor.getLinea + 
        "no se puede hacer match entre un retorno y una funcion")
      System.exit(1)
    }
  }

  override def visit(visitor: AstExpAsign) {
    visitor.getHijoByIndex(0).revisarTipos(this)
    val tipo = visitor.getHijoByIndex(0).getTipoRevisado
    if (!(tipo == "int")) {
      System.err.append("ERROR DE TIPO , en la linea: " + visitor.getLinea + 
        "el valor de la izquierda en la asignacion debe ser int")
      System.exit(1)
    }
    visitor.getHijoByIndex(1).revisarTipos(this)
    val tipoHijo1 = visitor.getHijoByIndex(1).getTipoRevisado
    if (!(tipoHijo1 == "int")) {
      System.err.append("ERROR DE TIPO ,en la linea: " + visitor.getLinea + 
        " el valor de la derecha en la asignacion debe ser int")
      System.exit(1)
    }
    visitor.setTipoRevisado("int")
  }

  override def visit(visitor: AstExpVar) {
    var entrada: Entrada = null
    tabsim.pushTabSimbol(visitor.getTabSimbol)
    entrada = tabsim.lookupEntrada(visitor.getIdent)
    tabsim.popTabSimbol()
    if (entrada == null) {
      entrada = tabsim.lookEntradaEnParametro(visitor.getIdent)
    }
    if (((entrada.tipo) == "int[]") && (visitor.getTipo == "int[]")) {
      visitor.getHijoByIndex(0).revisarTipos(this)
      val tipo = visitor.getHijoByIndex(0).getTipoRevisado
      if (!(tipo == "int")) {
        System.err.println("ERROR DE TIPO, El indice del arreglo: " + visitor.getIdent + 
          " en la linea: " + 
          visitor.getLinea + 
          "debe ser un int")
        System.exit(1)
      }
      visitor.setTipoRevisado("int")
    } else if (((entrada.tipo) == "int[]") && (visitor.getTipo == "int")) {
      val indiceArreglo = tabsim.indiceArreglo
      if (indiceArreglo) {
        visitor.setTipoRevisado("int[]")
      } else {
        System.err.println("ERROR DE TIPO, El arreglo: " + visitor.getIdent + " en la linea " + 
          visitor.getLinea + 
          " deberia estar seguido por los corchetes []")
        System.exit(1)
      }
    } else if (((entrada.tipo) == "int") && (visitor.getKind == "int[]")) {
      System.err.println("ERROR De TIPO, la variable: " + visitor.getIdent + " en la linea: " + 
        visitor.getLinea + 
        "que no es arreglo no debe estar seguida por corchetes []")
      System.exit(1)
    } else {
      assert((((entrada.tipo) == "int") && ((visitor.getKind) == "int")))
      visitor.setTipoRevisado("int")
    }
  }

  override def visit(visitor: AstExpBin) {
    visitor.getHijoByIndex(0).revisarTipos(this)
    val tipo = visitor.getHijoByIndex(0).getTipoRevisado
    if (!(tipo == "int")) {
      System.err.println("ERROR DE TIPO, en la linea: " + visitor.getLinea + "la exprecion a la izquierda del operador: " + 
        visitor.getValor2 + 
        " debe ser un int")
      System.exit(1)
    }
    visitor.getHijoByIndex(1).revisarTipos(this)
    val tipohijo1 = visitor.getHijoByIndex(1).getTipoRevisado
    if (!(tipohijo1 == "int")) {
      System.err.println("ERROR DE TIPO, en la linea: " + visitor.getLinea + "la exprecion a la derecha del operador: " + 
        visitor.getValor2 + 
        " debe ser un int")
      System.exit(1)
    }
    visitor.getTipo match {
      case "suma" | "resta" | "multiplicacion" | "division" => visitor.setTipoRevisado("int")
      case "Menor igual" | "Mayor" | "Menor" | "Mayor igual" | "Igual igual" | "Distinto" => visitor.setTipoRevisado("Bool")
      case _ => 
        System.err.println("ERROR, operador desconocido")
        System.exit(1)

    }
  }

  override def visit(visitor: AstExpConst) {
    visitor.setTipoRevisado("int")
  }

  override def visit(visitor: AstExpFun) {
    val funinfo = tabsim.lookupFunInfo(visitor.getIdent)
    var entrada = funinfo.tabSimbol.root
    if (visitor.getHijoByIndex(0).isInstanceOf[AstExpVar]) {
      var param = visitor.getHijoByIndex(0).asInstanceOf[AstExpVar]
      while (entrada != null && param != null) {
        if (((entrada.tipo) == "int[]") && ((param.getKind) == "expreVar")) {
          tabsim.indiceArreglo = true
        }
        param.revisarTipos(this)
        val tipo = param.getTipoRevisado
        tabsim.indiceArreglo = false
        if (tipo == entrada.tipo) {
          System.err.append("ERROR DE TIPO, no hubo match entre el parametro: " + 
            param.getIdent + 
            " y el parametro: " + 
            visitor.getIdent + 
            " de la  funcion: " + 
            entrada.name + 
            " en la linea: " + 
            visitor.getLinea)
          System.exit(1)
        }
        entrada = entrada.next
        param = param.getHermano.asInstanceOf[AstExpVar]
      }
      if (entrada != null || param != null) {
        System.err.append("ERROR DE TIPO, no se pudo hacer match entre el paramentro " + 
          visitor.getValor + 
          " y la funcion en la linea: " + 
          visitor.getLinea + 
          " faltan o sobran parametros")
        System.exit(1)
      }
    }
    visitor.setTipoRevisado(funinfo.tipo)
  }

 /* override def visit(visitor: AstNode) {
    visit(visitor)
  }*/
}