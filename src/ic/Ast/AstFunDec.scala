package Ast

import scala.collection.mutable._
import Parser.sym
import Visitor.AstVisitor
import GraphVisitor.GrapherVisitor


class AstFunDec(tipoLlega: Int, 
    id: String, 
    params: AstNode, 
    comp: AstNode,
    kind:String,
    linea:Int
    ) extends AstNode {

  var ident: String = id

  var tipos: Int = tipoLlega

  this.setKind(kind)

  this.setLinea(linea)

 // this.nNodo = GrapherVisitor.identNodo + 1

  this.tipo = if (this.tipos == sym.VOID) "void" else "int"

  if (params != null) {
    this.hijos += params
    params.setPadre(this)
    var hermano = params.getHermano
    while (hermano != null) {
      this.hijos += hermano
      hermano.setPadre(this)
      hermano = hermano.getHermano
    }
  }

  this.hijos += comp

  if (comp != null) {
    comp.setPadre(this)
  }

  //Collections.reverse(this.hijos)

  override def accept(visitor: AstVisitor) {
    visitor.visit(this)
  }

  def getIdent(): String = ident

  def setIdent(ident: String) {
    this.ident = ident
  }

  def getTipos(): Int = tipos

  def setTipos(tipos: Int) {
    this.tipos = tipos
  }
  
}	