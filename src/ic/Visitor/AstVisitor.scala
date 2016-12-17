package Visitor

import Ast._

trait AstVisitor {

  def visit(visitor: AstProgram): Unit

  def visit(visitor: AstVarDec): Unit

  def visit(visitor: AstFunDec): Unit

  def visit(visitor: AstParamDec): Unit

  def visit(visitor: AstCompStmt): Unit

  def visit(visitor: AstVaciaStmt): Unit

  def visit(visitor: AstSelectStmt): Unit

  def visit(visitor: AstIterStmt): Unit

  def visit(visitor: AstReturnStmt): Unit

  def visit(visitor: AstExpAsign): Unit

  def visit(visitor: AstExpVar): Unit

  def visit(visitor: AstExpBin): Unit

  def visit(visitor: AstExpConst): Unit

  def visit(visitor: AstExpFun): Unit
}
