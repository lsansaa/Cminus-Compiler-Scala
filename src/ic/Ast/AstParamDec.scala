package Ast

import GraphVisitor.GrapherVisitor
import Parser.sym
import Visitor.AstVisitor


class AstParamDec(tipoLlega: Int, id: String, corch: Boolean,
    kind:String,
    linea:Int) extends AstNode {

  this.setKind(kind)

  this.setLinea(linea)

  var ident: String = id

  var corchetes: Boolean = corch

  this.valor = ""

  var tipos: Int = tipoLlega

  //this.nNodo = GrapherVisitor.identNodo +  1

  this.tipo = if (this.corchetes == false) if (this.tipos == sym.VOID) "void" else "int" else if (this.tipos == sym.VOID) "void[]" else "int[]"

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

  override def getValor(): String = valor

  override def setValor(valor: String) {
    this.valor = valor
  }

  def getTipos(): Int = tipos

  def setTipos(tipos: Int) {
    this.tipos = tipos
  }
}
