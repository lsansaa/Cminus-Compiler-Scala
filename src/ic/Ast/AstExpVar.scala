package Ast

import scala.collection.mutable._
import GraphVisitor.GrapherVisitor
import Parser.sym
import Visitor.AstVisitor

class AstExpVar(exp: AstNode, id: String,  corch: Boolean,
    kind:String,
    linea:Int) extends AstNode {
  
  this.setKind(kind)

  this.setLinea(linea)

  var ident: String = id

  var corchetes: Boolean = corch

 // this.nNodo = GrapherVisitor.identNodo + 1

  this.tipo = if (this.corchetes == true) "conOperacion" else "sinOperacion"

  if (exp != null) {
    this.valor = exp.getValor
    this.hijos += exp
    exp.setPadre(this)
    var hermano = exp.getHermano
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

  def getIdent(): String = ident

  def setIdent(ident: String) {
    this.ident = ident
  }

  def isCorchetes(): Boolean = corchetes

  def setCorchetes(corchetes: Boolean) {
    this.corchetes = corchetes
  }
  
}
