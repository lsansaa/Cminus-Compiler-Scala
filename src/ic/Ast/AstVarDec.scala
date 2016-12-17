package Ast

import java_cup.runtime.Symbol
import GraphVisitor.GrapherVisitor
import Parser.sym
import Visitor.AstVisitor

class AstVarDec(tipoLlega: Int, 
    id: String,
    kind:String,
    linea:Int) extends AstNode {

  var ident: String = id

  this.setKind(kind)

  this.setLinea(linea)

  this.valor = ""

  //this.nNodo = GrapherVisitor.identNodo + 1


  if (tipoLlega == sym.VOID) {
    this.setTipo("void")
  } else {
    this.setTipo("int")
  }

def this(tipoLlega: Int, 
    id: String, 
    num: String,
    kind:String,
    linea:Int)={
  
  this(tipoLlega,id,kind,linea)

  this.setValor(num)
  
  if (tipoLlega == sym.VOID) {
      this.setTipo("void[" + this.getValor + "]")
  } else {
    this.setTipo("int[" + this.getValor + "]")
  }

}


  override def accept(visitor: AstVisitor) {
    visitor.visit(this)
  }

  def getIdent(): String = this.ident

  def setIdent(ident: String) {
    this.ident = ident
  }

 override def getValor(): String = this.valor

 override def setValor(valor: String) {
    this.valor = valor
  }
}
