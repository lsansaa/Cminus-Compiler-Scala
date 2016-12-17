package semanticCheck
  
import Ast._
import Visitor.AstVisitor
import java.util.ArrayList
import semanticCheck._
import GraphVisitor.GrapherVisitor


class TablaSimbolos {
  
  var indiceArreglo: Boolean = false

  var funList: FunInfo = null

  var simbolTab: TabSimbol = null

  def pushFunInfo(finfo: FunInfo) {
    finfo.next = funList
    funList = finfo
  }

  def topFunInfo(): FunInfo = funList

  def reverse() {
    var funI = new FunInfo()
    funList = null
    while (funI != null) {
      val temp = funI
      funI = funI.next
      pushFunInfo(temp)
    }
  }

  def pushTabSimbol(tSimbol: TabSimbol) {
    tSimbol.next = simbolTab
    simbolTab = tSimbol
  }

  def popTabSimbol() {
    if (simbolTab == null) {
      System.err.append("ERROR, La tabla de simbolos esta vacia no se puede hacer pop")
    }
    simbolTab = simbolTab.next
  }

  def topTabSimbol(): TabSimbol = {
    if (simbolTab == null) {
      System.err.append("ERROR, La tabla de simbolos esta vacia no se puede hacer top")
    }
    simbolTab
  }

  def nuevaEntrada(name: String, 
      tipo: String, 
      num: Int, 
      nodo: String, 
      next: Entrada): Entrada = {
    val entrada = new Entrada()
    entrada.name = name
    entrada.tipo = tipo
    entrada.indice = num
    entrada.tipoNodo = nodo
    entrada.next = next
    entrada
  }

  def nuevaTabSimbol(nodo: String): TabSimbol = {
    val newTab = new TabSimbol()
    newTab.next = null
    newTab.tipoNodo = nodo
    newTab.root = null
    newTab.tamaño = 0
    newTab
  }

  def nuevaFunInfo(name: String, tipo: String, tsim: TabSimbol): FunInfo = {
    val newFun = new FunInfo()
    newFun.direccion = -1
    newFun.name = name
    newFun.next = null
    newFun.tabSimbol = tsim
    newFun.tamañoParm = 0
    newFun.tamañoVar = 0
    newFun.tipo = tipo
    newFun
  }

  def insertEntrada(name: String, tipo: String, size: Int) {
    val tsimbol = topTabSimbol()
    var p = tsimbol.root
    while (p != null && p != p.name) {
      p = p.next
    }
    if (p != null) {
      System.err.println("ERROR Semantico, Variable duplicada" + name)
      System.exit(1)
    }
    var offSet = -1
    val tipoNodo = tsimbol.tipoNodo
    tipoNodo match {
      case "VarGloval" | "paramDecl" => offSet = tsimbol.tamaño
      case "compSt" => offSet = topFunInfo().tamañoVar
      case _ => 
        System.err.println("ERROR, tipo de tabla desconocido")
        System.exit(1)

    }
    val entrada = nuevaEntrada(name, tipo, offSet, tipoNodo, tsimbol.root)
    tsimbol.root = entrada
    if (tipoNodo != "paramDecl" && tipo == "int[]") {
      tsimbol.tamaño = tsimbol.tamaño + size
    } else {
      tsimbol.tamaño += 1
    }
    tsimbol.tipoNodo match {
      case "VarGloval" => 
      case "paramDecl" => topFunInfo().tamañoParm += 1
      case "compSt" => if (tipo == "int[]") {
        topFunInfo().tamañoVar = topFunInfo().tamañoVar + size
      } else {
        assert((tipo == "int"))
        topFunInfo().tamañoVar += 1
      }
      case _ => System.err.append("ERROR, tipo de tabla desconocido")
    }
  }

  def lookupEntrada(name: String): Entrada = {
    var tsimbol = topTabSimbol()
    while (tsimbol != null) {
      var entrada = tsimbol.root
      while (entrada != null) {
        if ((entrada.name) == name) {
          return entrada
        }
        entrada = entrada.next
      }
      tsimbol = tsimbol.next
    }
    null
  }

  def lookEntradaEnParametro(nombreVar: String): Entrada = {
    var tsimbol = funList.tabSimbol
    while (tsimbol != null) {
      var entrada = tsimbol.root
      while (entrada != null) {
        if ((entrada.name) == nombreVar) {
          return entrada
        }
        entrada = entrada.next
      }
      tsimbol = tsimbol.next
    }
    null
  }

  def lookupFunInfo(name: String): FunInfo = {
    var finfo = topFunInfo()
    while (finfo != null) {
      if ((finfo.name) == name) {
        return finfo
      }
      finfo = finfo.next
    }
    null
  }

  def preInPutFunInfo(): FunInfo = {
    val tsimbol = nuevaTabSimbol("paramDecl")
    val funinfo = nuevaFunInfo("input", "int", tsimbol)
    funinfo
  }

  def preOutPutFunInfo(): FunInfo = {
    val entrada = nuevaEntrada("x", "int", 0, "paramDecl", null)
    val tsimbol = nuevaTabSimbol("paramDecl")
    tsimbol.root = entrada
    tsimbol.tamaño = 1
    val funinfo = nuevaFunInfo("output", "void", tsimbol)
    funinfo.tamañoParm = 1
    funinfo
  }

  def imprimirEntrada(entrada: Entrada) {
    println("Alcance: nombre = " + entrada.name + ", tipo = " + entrada.tipo + 
      ", tipo de nodo = " + 
      entrada.tipoNodo + 
      ", posicion = " + 
      entrada.indice)
  }

  def imprimirTabSimbol(tsimbol: TabSimbol) {
    println("Tabla de simbolos, tipo nodo = " + tsimbol.tipoNodo + 
      ", tamaño = " + 
      tsimbol.tamaño)
    var entrada = tsimbol.root
    while (entrada != null) {
      imprimirEntrada(entrada)
      entrada = entrada.next
    }
  }

  def imprimirNodoTabSimbol(nodo: AstNode) {
    while (nodo != null) nodo.getKind match {
      case "funDecl" => 
        imprimirTabSimbol(nodo.getTabSimbol)
        imprimirNodoTabSimbol(nodo.getHijoByIndex(0))
        imprimirNodoTabSimbol(nodo.getHijoByIndex(1))

      case "compSt" => 
        imprimirTabSimbol(nodo.getTabSimbol)
        imprimirNodoTabSimbol(nodo.getHijoByIndex(0))

      case "selec" => 
        imprimirNodoTabSimbol(nodo.getHijoByIndex(1))
        if (nodo.tieneElse) {
          imprimirNodoTabSimbol(nodo.getHijoByIndex(2))
        }

      case "ciclo" => imprimirNodoTabSimbol(nodo.getHijoByIndex(1))
      case _ => 
    }
  }

  def imprimirRoot(root: AstNode) {
    imprimirTabSimbol(topTabSimbol())
    imprimirTabSimbol(lookupFunInfo("input").tabSimbol)
    imprimirTabSimbol(lookupFunInfo("output").tabSimbol)
    imprimirNodoTabSimbol(root)
  }

  def imprimirFunList() {
    var finfo = topFunInfo()
    while (finfo != null) {
      println("FunInfo: nombre = " + finfo.name + "tipo = " + finfo.tipo + 
        "tamaño de parametros = " + 
        finfo.tamañoParm + 
        "tamaño de variables = " + 
        finfo.tamañoVar)
      finfo = finfo.next
    }
  }
}