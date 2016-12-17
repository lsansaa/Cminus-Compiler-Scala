package Ast

import GraphVisitor.GrapherVisitor
import Parser.sym
import Visitor.AstVisitor

class AstVaciaStmt(kind:String,linea:Int) extends AstNode {

  //this.nNodo = GrapherVisitor.identNodo + 1

  this.setKind(kind)

  this.setLinea(linea)
  
  var void: Int = sym.VOID

  this.tipo = void.toString

  override def accept(visitor: AstVisitor) {
    visitor.visit(this)
  }
}
