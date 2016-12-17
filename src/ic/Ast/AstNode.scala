package Ast

import scala.collection.mutable._
import Visitor.AstVisitor
import GraphVisitor.GrapherVisitor
import semanticCheck._

abstract class AstNode {

  var nNodo: Int = 0

  var padre: AstNode = null

  var hermano: AstNode = null
  
  var hijos: ListBuffer[AstNode] = new ListBuffer()

  var tipo: String = ""

  var valor: String = ""

  var tipoRevisado: String = ""

  var linea: Int = 0

  var tipoAST: String = ""

  var tsim: TabSimbol = null

  var tieneElse : Boolean = false

  var etiqueta : Int = 0

  def getNNodo(): Int = this.nNodo

  def getPadre(): AstNode = this.padre

  def getHermano(): AstNode = this.hermano

  def getHijos(): ListBuffer[AstNode] = this.hijos

  def getLinea(): Int = this.linea

  def getTipo(): String = this.tipo

  def getValor(): String = valor

  def getIndice(): Int = this.nNodo

  def getKind(): String = this.tipoAST

  def getTipoRevisado(): String = this.tipoRevisado

  def getTabSimbol(): TabSimbol = this.tsim

  def getEtiqueta(): Int = this.etiqueta

  def setNNodo(nodoN: Int) {
    this.nNodo = nodoN
  }

  def setPadre(pad: AstNode) {
    this.padre = pad
  }

  def setHermano(her: AstNode) {
    this.hermano = her
  }

  def setHijos(hij: ListBuffer[AstNode]) {
    this.hijos = hij
  }

  def setTipo(tip: String) {
    this.tipo = tip
  }

  def setValor(valor: String) {
    this.valor = valor
  }

  def setIndice(indice: Int){
  	this.nNodo = indice
  }

  def setKind(kind: String) {
  	this.tipoAST = kind
  }

  def setTipoRevisado(tipoRevisado: String){
  	this.tipoRevisado = tipoRevisado
  }

  def setTabSimbol(tabSimbol: TabSimbol){
  	this.tsim = tabSimbol
  }
  def setLinea(linea: Int){
  	this.linea = linea
  }

  def setEtiqueta(etiqueta: Int){
    this.etiqueta = etiqueta
  }

/*  def recorrerArbol(visitor: AstVisitor) {
    if(visitor!=null){
      if (GrapherVisitor.nodoVisitados.contains(this.nNodo)) {
        return
      }
      GrapherVisitor.nodoVisitados.add(this.nNodo)
      accept(visitor)
      var i: Int = 0
      while(i < this.hijos.size()) {
        var nodo = this.hijos.get(i)
        nodo.recorrerArbol(visitor)
        i = i +1  
      }
    }
  }*/

  def accept(visitor: AstVisitor) {
  }

  def acceptnodo(v: GrapherVisitor) {
    
  }

  def revisarAlcance(visitante:VisitorAlcance) {
        //si el nodo ya esta en el arreglo
        if (VisitorAlcance.nodosRevisados.contains(this.nNodo)) {
            //no se hace nada asi se evita que se repita
            return;
        }
        //En caso contrario como el nodo no esta aun es necesario agregarlo
        VisitorAlcance.nodosRevisados.add(this.nNodo);
        accept(visitante);
        //Se procede recorriendo el arbol
        var i :Int = 0
        var size:Int = this.hijos.length
        while (i < size) {
        	var nodo :AstNode = getHijoByIndex(i)
            if (nodo != null) {
                nodo.revisarAlcance(visitante)
            }
            i = i+1
        }
    }

   def revisarTipos(visitante: VisitorTipo) {
        //si el nodo ya esta en el arreglo
        if (VisitorTipo.nodosRevisados.contains(this.nNodo)) {
            //no se hace nada asi se evita que se repita
            return;
        }
        //En caso contrario como el nodo no esta aun es necesario agregarlo
        VisitorTipo.nodosRevisados.add(this.nNodo);
        accept(visitante);
        //Se procede recorriendo el arbol
        var i :Int = 0
        var size:Int = this.hijos.length
        while (i < size) {
        	var nodo :AstNode = getHijoByIndex(i)
            if (nodo != null) {
                nodo.revisarTipos(visitante)
            }
            i = i+1
        }
    } 
  def isTieneElse(): Boolean = this.tieneElse

  def getHijoByIndex (index:Int): AstNode ={
    this.hijos(index)
  }


}