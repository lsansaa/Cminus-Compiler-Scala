package Ast

import scala.collection.mutable._
import GraphVisitor.GrapherVisitor
import Parser.sym
import Visitor.AstVisitor

class AstExpBin(tipo2: Int, exp1: AstNode, exp2: AstNode,
    kind:String,
    linea:Int) extends AstNode {
  
  this.setKind(kind)

  this.setLinea(linea)

  var valor2: String = _

  //this.nNodo = GrapherVisitor.identNodo + 1

  tipo2 match {
    case sym.MINEQ => this.tipo = "Menor igual"
    case sym.MINOR => this.tipo = "Menor"
    case sym.MAYOR => this.tipo = "Mayor"
    case sym.MAYEQ => this.tipo = "Mayor igual"
    case sym.EQ => this.tipo = "Igual igual"
    case sym.NEQ => this.tipo = "Distinto"
    case sym.ADD => this.tipo = "Suma"
    case sym.SUBT => this.tipo = "Resta"
    case sym.MULT => this.tipo = "Multiplicacion"
    case sym.DIV => this.tipo = "Division"
    case _ => this.tipo = ""
  }

  if (exp1 != null) {
    this.hijos += exp1
    exp1.setPadre(this)
    this.setValor(exp1.getValor)
    var hermano = exp1.getHermano
    while (hermano != null) {
      this.hijos += hermano
      hermano.setPadre(this)
      hermano = hermano.getHermano
    }
  }

  if (exp2 != null) {
    this.hijos += exp2
    exp2.setPadre(this)
    this.valor2 = exp2.getValor
    var hermano = exp2.getHermano
    while (hermano != null) {
      this.hijos += hermano
      hermano.setPadre(this)
      hermano = hermano.getHermano
    }
  }

  //Collections.reverse(this.hijos)

  override def accept(visitor: AstVisitor) {
    visitor.visit(this)
  }

  def getValor2(): String = valor2

  def setValor2(valor2: String) {
    this.valor2 = valor2
  }
}
