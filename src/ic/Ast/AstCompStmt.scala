package Ast

import scala.collection.mutable._
import GraphVisitor.GrapherVisitor
import Visitor.AstVisitor

class AstCompStmt(locDec: AstNode, stmtList: AstNode,
  kind:String,
  linea:Int) extends AstNode {

  this.setKind(kind)

  this.setLinea(linea)

  if (stmtList != null) {
    this.hijos += stmtList
    stmtList.setPadre(this)
    var hermano = stmtList.getHermano
    while (hermano != null) {
      this.hijos += hermano
      hermano.setPadre(this)
      hermano = hermano.getHermano
    }
  }

  if (locDec != null) {
    this.hijos += locDec
    locDec.setPadre(this)
    var hermano = locDec.getHermano
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
