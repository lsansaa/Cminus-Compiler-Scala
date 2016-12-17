package Ast

import scala.collection.mutable._
import GraphVisitor.GrapherVisitor
import Visitor.AstVisitor


class AstExpFun(id: String, arg: AstNode,
    kind:String,
    linea:Int) extends AstNode {
  
  this.setKind(kind)

  this.setLinea(linea)

  var ident: String = id

 // this.nNodo = GrapherVisitor.identNodo + 1

  this.tipo = "id"

  if (arg != null) {
    this.setValor(arg.getValor)
    this.hijos += arg
    arg.setPadre(this)
    var hermano = arg.getHermano
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

  def getIdent(): String = this.ident

  def setIdent(ident: String) {
    this.ident = ident
  }
  
}