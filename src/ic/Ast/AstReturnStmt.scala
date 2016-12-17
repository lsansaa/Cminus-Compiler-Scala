package Ast

import GraphVisitor._
import Parser.sym
import Visitor.AstVisitor

class AstReturnStmt(exp: AstNode,
    kind:String,
    linea:Int) extends AstNode {
  
  this.setKind(kind)

  this.setLinea(linea)

  var tipos: Int = _

  //this.nNodo = GrapherVisitor.identNodo + 1

  if (exp != null) {
    this.setValor(exp.getTipo)

    if (exp != null) {
      exp.setPadre(this)
      this.hijos += exp
    }
    this.hijos += exp
    this.setTipo(exp.getTipo)
  } else {
    this.setTipo("void")
  }

  override def accept(visitor: AstVisitor) {
    visitor.visit(this)
  }
}