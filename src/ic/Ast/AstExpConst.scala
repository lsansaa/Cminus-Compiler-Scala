package Ast

import GraphVisitor.GrapherVisitor
import Visitor.AstVisitor


class AstExpConst(num: String,
    kind:String,
    linea:Int) extends AstNode {
  
  this.setKind(kind)

  this.setLinea(linea)

  //this.nNodo = GrapherVisitor.identNodo + 1

  this.setTipo("int")

  this.setValor(num)

  override def accept(visitor: AstVisitor) {
    visitor.visit(this)
  }
}
