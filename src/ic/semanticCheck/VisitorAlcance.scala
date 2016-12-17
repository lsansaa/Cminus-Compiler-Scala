package semanticCheck

import Ast._
import Visitor.AstVisitor
import java.util.ArrayList
import semanticCheck._

object VisitorAlcance {

  var indiNodo: Int = _

  var nodosRevisados: ArrayList[Integer] = new ArrayList()

  var tabsim: TablaSimbolos = new TablaSimbolos()
}

class VisitorAlcance extends AstVisitor {

  var indiNodo: Int = _

  var nodosRevisados: ArrayList[Integer] = new ArrayList()

  var tabsim: TablaSimbolos = new TablaSimbolos()
  
  override def visit(visitor: AstProgram) {

  }

  override def visit(visitor: AstVarDec) {
    tabsim.insertEntrada(visitor.getIdent, visitor.getTipo, visitor.getIndice)
    if (visitor.getPadre.isInstanceOf[AstCompStmt]) {
      val padre = visitor.getPadre.asInstanceOf[AstCompStmt]
      for (i <- 0 until padre.getHijos.size) {
        val hermano = padre.getHijoByIndex(i)
        hermano.revisarAlcance(this)
      }
    }
  }

  override def visit(visitor: AstFunDec) {
    var funinfo: FunInfo = null
    funinfo = tabsim.lookupFunInfo(visitor.getIdent)
    if (funinfo != null) {
      System.err.println("ERROR SEMANTICO, La función: " + visitor.getIdent + " en la linea: " + 
        visitor.getLinea + 
        " esta duplicada ")
      System.exit(1)
    }
    visitor.setTabSimbol(tabsim.nuevaTabSimbol("paramDecl"))
    tabsim.pushTabSimbol(visitor.getTabSimbol)
    funinfo = tabsim.nuevaFunInfo(visitor.getIdent, visitor.getTipo, visitor.getTabSimbol)
    tabsim.pushFunInfo(funinfo)
    for (i <- 0 until visitor.getHijos.size) {
      if (visitor.getHijoByIndex(i).isInstanceOf[AstParamDec]) {
        val parametro = visitor.getHijoByIndex(i).asInstanceOf[AstParamDec]
        parametro.revisarAlcance(this)
      } else if (visitor.getHijoByIndex(i).isInstanceOf[AstCompStmt]) {
        val cuerpo = visitor.getHijoByIndex(i).asInstanceOf[AstCompStmt]
        cuerpo.revisarAlcance(this)
      }
    }
    tabsim.popTabSimbol()
  }

  override def visit(visitor: AstParamDec) {
    tabsim.insertEntrada(visitor.getIdent, visitor.getTipo, visitor.getIndice)
  }

  override def visit(visitor: AstCompStmt) {
    visitor.setTabSimbol(tabsim.nuevaTabSimbol(visitor.getKind))
    tabsim.pushTabSimbol(visitor.getTabSimbol)
    visitor.getHijoByIndex(0).revisarAlcance(this)
    tabsim.popTabSimbol()
  }

  override def visit(visitor: AstVaciaStmt) {

  }

  override def visit(visitor: AstSelectStmt) {

  }

  override def visit(visitor: AstIterStmt) {

  }

  override def visit(visitor: AstReturnStmt) {

  }

  override def visit(visitor: AstExpAsign) {

  }

  override def visit(visitor: AstExpVar) {
    var entrada: Entrada = null
    entrada = tabsim.lookupEntrada(visitor.getIdent)
    if (entrada == null) {
      entrada = tabsim.lookEntradaEnParametro(visitor.getIdent)
      if (entrada == null) {
        System.err.println("ERROR SEMANTICO, la variable: " + visitor.getIdent + 
          " en la linea: " + 
          visitor.getLinea + 
          " no esta declarada")
        System.exit(1)
      }
    }
    visitor.setTabSimbol(tabsim.topTabSimbol())
  }

  override def visit(visitor: AstExpBin) {

  }

  override def visit(visitor: AstExpConst) {

  }

  override def visit(visitor: AstExpFun) {
    val funinfo = tabsim.lookupFunInfo(visitor.getIdent)
    if (funinfo == null) {
      System.err.println("ERROR SEMANTICO, la funcion: " + visitor.getIdent + " en la linea: " + 
        visitor.getLinea + 
        " no esta definida")
      System.exit(1)
    }
  }

/*  override def visit(visitor: AstNode) {
    visit(visitor)
  }*/
}