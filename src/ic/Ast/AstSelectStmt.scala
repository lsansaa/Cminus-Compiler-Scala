package Ast

import scala.collection.mutable._
import GraphVisitor.GrapherVisitor
import Parser.sym
import Visitor.AstVisitor


class AstSelectStmt(exp: AstNode, 
    stmtIf: AstNode, 
    stmtElse: AstNode, 
    tendraElse: Boolean,
    kind:String,
    linea:Int) extends AstNode {
  
  this.setKind(kind)

  this.setLinea(linea)

  this.tieneElse = tendraElse

    //this.nNodo = GrapherVisitor.identNodo + 1

  this.tipo = if (tieneElse == true) "else" else "if"

  if (exp != null) {
    this.hijos += exp
    exp.setPadre(this)
  }

  if (stmtIf != null) {
    this.hijos += stmtIf
    stmtIf.setPadre(this)
    var hermano = stmtIf.getHermano
    while (hermano!=null) {
      this.hijos += hermano
      hermano.setPadre(this)
      hermano = hermano.getHermano
    }
  }

  if (stmtElse != null) {
    this.hijos += stmtElse
    stmtElse.setPadre(this)
    var hermano = stmtElse.getHermano
    while (hermano!=null) {
      this.hijos += hermano
      hermano.setPadre(this)
      hermano = hermano.getHermano
    }
  }

  //Collections.reverse(this.hijos)

  override def accept(visitor: AstVisitor) {
    visitor.visit(this)
  }

  override def isTieneElse(): Boolean = tieneElse

  def setTieneElse(tieneElse: Boolean) {
    this.tieneElse = tieneElse
  }
}
