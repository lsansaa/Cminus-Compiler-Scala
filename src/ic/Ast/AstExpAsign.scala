package Ast

import GraphVisitor.GrapherVisitor
import scala.collection.mutable._
import Visitor.AstVisitor

class AstExpAsign(value : AstNode, exp: AstNode, tip: Int,
    kind:String,
    linea:Int) extends AstNode {
  
  this.setKind(kind)

  this.setLinea(linea)

  //this.setNNodo(GrapherVisitor.identNodo + 1)

  this.setValor(exp.getValor)

  if (value != null) {
    this.hijos += value
    value.setPadre(this)
    var hermano = value.getHermano
    while (hermano != null) {
      this.hijos += hermano
      hermano.setPadre(this)
      hermano = hermano.getHermano
    }
  }

  if (exp != null) {
    this.hijos += exp
    exp.setPadre(this)
    var hermano = exp.getHermano
    while (hermano != null) {
      this.hijos += hermano
      hermano.setPadre(this)
      hermano = hermano.getHermano
    }
  }

  override def accept(visitor: AstVisitor) {
    visitor.visit(this)
  }
}