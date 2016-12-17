package Ast

import scala.collection.mutable._
import GraphVisitor.GrapherVisitor
import Visitor.AstVisitor

class AstProgram(kind: String,dl: AstNode, linea: Int) extends AstNode {
  this.setKind(kind)
  this.setLinea(linea)
 // this.nNodo = GrapherVisitor.identNodo + 1

  if (dl != null  ) {
    this.hijos += dl
    dl.setPadre(this)
    var hermano = dl.getHermano
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
}