package Ast

import scala.collection.mutable._
import GraphVisitor.GrapherVisitor
import Parser.sym
import Visitor.AstVisitor


	class AstIterStmt(exp: AstNode,
    kind:String,
    linea:Int) extends AstNode {
  
  this.setKind(kind)

  this.setLinea(linea)

 // this.nNodo = GrapherVisitor.identNodo + 1
  
  var tieneDoWhile = false    

  def this(exp: AstNode, stmt: AstNode, tendraDoWhile: Boolean,kind:String,
    linea:Int) {

      this(exp,kind,linea)
    
    this.tipo = if (this.tieneDoWhile == true) "do" else "while"

    if (exp != null) {
      this.hijos += exp
      exp.setPadre(this)
    }

    if (stmt != null) {
      this.hijos += stmt
      stmt.setPadre(this)
      var hermano = stmt.getHermano
      while (hermano != null) {
        this.hijos += hermano
        hermano.setPadre(this)
        hermano = hermano.getHermano  
      }
    }
    //Collections.reverse(this.hijos) 
  }

  def this(exp: AstNode, 
      exp2: AstNode, 
      exp3: AstNode, 
      stmt: AstNode,
      kind:String,
      linea:Int) = {
    
    this(exp,kind,linea)
    this.tipo = "for"
    if (exp != null) {
      this.hijos += exp
      exp.setPadre(this)
    }
    if (exp2 != null) {
      this.hijos += exp2
      exp2.setPadre(this)
    }
    if (exp3 != null) {
      this.hijos += exp3
      exp3.setPadre(this)
    }
    if (stmt != null) {
      this.hijos += stmt
      stmt.setPadre(this)
      var hermano = stmt.getHermano
      while (hermano != null) {
        this.hijos += hermano
        hermano.setPadre(this)
        hermano = hermano.getHermano
      }
    }
    //Collections.reverse(this.hijos)
  }

  override def accept(visitor: AstVisitor) {
    visitor.visit(this)
  }

  def isTieneDoWhile(): Boolean = tieneDoWhile

  def setTieneDoWhile(tieneDoWhile: Boolean) {
    this.tieneDoWhile = tieneDoWhile
  }
  
}