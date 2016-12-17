package GraphVisitor;

import Ast._
import Visitor.AstVisitor
import scala.collection.mutable._

class GrapherVisitor  extends AstVisitor {

  var concatenar: String = ""
  var prueba: String = ""
  var cadena: String = ""
  var contador: Int = 0

  def getConcatenar(): java.lang.String = {
    return concatenar
  }
  def getCadena(): java.lang.String = {
    return cadena
  }

  def Visitas(lista: ListBuffer[AstNode], nodo1: AstNode) {
    var i: Int = 0
    while (i < lista.size) {

      if (lista.apply(i).isInstanceOf[AstExpAsign]) {
        var n1: AstExpAsign = lista.apply(i).asInstanceOf[AstExpAsign]
        n1.accept(this)
      }
      if (lista.apply(i).isInstanceOf[AstExpFun]) {
        var n1: AstExpFun = lista.apply(i).asInstanceOf[AstExpFun]
        n1.accept(this)
      }
      if (lista.apply(i).isInstanceOf[AstCompStmt]) {
        var n1: AstCompStmt = lista.apply(i).asInstanceOf[AstCompStmt]
        n1.accept(this)
      }
      if (lista.apply(i).isInstanceOf[AstExpBin]) {
        var n1: AstExpBin = lista.apply(i).asInstanceOf[AstExpBin]
        n1.accept(this)
      }
      if (lista.apply(i).isInstanceOf[AstVaciaStmt]) {
        var n1: AstVaciaStmt = lista.apply(i).asInstanceOf[AstVaciaStmt]
        n1.accept(this)
      }
      if (lista.apply(i).isInstanceOf[AstFunDec]) {
        var n1: AstFunDec = lista.apply(i).asInstanceOf[AstFunDec]
        n1.accept(this)
      }
      if (lista.apply(i).isInstanceOf[AstExpConst]) {
        var n1: AstExpConst = lista.apply(i).asInstanceOf[AstExpConst]
        n1.accept(this)
      }
      if (lista.apply(i).isInstanceOf[AstParamDec]) {
        var n1: AstParamDec = lista.apply(i).asInstanceOf[AstParamDec]
        n1.accept(this)
      }
      if (lista.apply(i).isInstanceOf[AstReturnStmt]) {
        var n1: AstReturnStmt = lista.apply(i).asInstanceOf[AstReturnStmt]
        n1.accept(this)
      }
      if (lista.apply(i).isInstanceOf[AstSelectStmt]) {
        var n1: AstSelectStmt = lista.apply(i).asInstanceOf[AstSelectStmt]
        n1.accept(this)
      }
      if (lista.apply(i).isInstanceOf[AstVarDec]) {
        var n1: AstVarDec = lista.apply(i).asInstanceOf[AstVarDec]
        n1.accept(this)
      }
      if (lista.apply(i).isInstanceOf[AstExpVar]) {
        var n1: AstExpVar = lista.apply(i).asInstanceOf[AstExpVar]
        n1.accept(this)
      }
      if (lista.apply(i).isInstanceOf[AstIterStmt]) {
        var n1: AstIterStmt = lista.apply(i).asInstanceOf[AstIterStmt]
        n1.accept(this)
      }

      i = i + 1
    }
  }

  def visit(nodo: AstProgram) {
    try {
      nodo.getHermano().acceptnodo(this);
    } catch {
      case e: Exception => System.out.println("NODO NULL grapvhiz en PROGRAM")
    }
    contador += 1
    nodo.setEtiqueta(contador);
    cadena += nodo.getEtiqueta.toString() + "[label=\"" + "Program" + "\"]\n"
    try {
      var lista: ListBuffer[AstNode] = nodo.getHijos()

      Visitas(lista, nodo)
    } catch {
      case e: Exception => System.out.println("NODO NULL grapvhiz en Visitas()")
    }
    var lista: ListBuffer[AstNode] = nodo.getHijos()
  }

  def visit(nodo: AstVarDec) {
    //empty(nodo);
    contador += 1
    nodo.setEtiqueta(contador)
    cadena += contador + " [label=\"" + "Variable, " + nodo.getIdent + " " + nodo.getTipo + "\"];\n"
    try {
      var lista: ListBuffer[AstNode] = nodo.getHijos()
      concatenar += nodo.getPadre.getEtiqueta.toString() + "->" + nodo.getEtiqueta.toString() + "\n"
      Visitas(lista, nodo)
    } catch {
      case e: Exception => System.out.println("NODO NULL grapvhiz en VOID Declaration")
    }
  }

  def visit(nodo: AstFunDec) {
    //empty(nodo);
    contador += 1
    nodo.setEtiqueta(contador);
    cadena += contador + "[label=\"" + "Funcion[" + nodo.getIdent + "," + nodo.getTipo + "]" + "\"]\n";
    if (nodo.getHijos() != null) {
      var lista: ListBuffer[AstNode] = nodo.getHijos();
      concatenar += nodo.getPadre.getEtiqueta.toString() + "->" + nodo.getEtiqueta.toString() + "\n";
      Visitas(lista, nodo);
    }
  }

  def visit(nodo: AstExpBin) {
    //empty(nodo);
    contador += 1
    nodo.setEtiqueta(contador);
    nodo.getTipo match {
      case "Menor igual" =>  cadena += contador + "[label=\"" + "MENORIGUAL" + "\"]\n"
      case "Menor" => cadena += contador + "[label=\"" + "MENOR" + "\"]\n";
      case "Mayor igual" => cadena += contador + "[label=\"" + "MAYORIGUAL" + "\"]\n";
      case "Mayor" => cadena += contador + "[label=\"" + "MAYOR" + "\"]\n";
      case "Igual igual" => cadena += contador + "[label=\"" + "IGUAL" + "\"]\n";
      case "Distinto" => cadena += contador + "[label=\"" + "DISTINTO" + "\"]\n";
      case "Suma" => cadena += contador + "[label=\"" + "SUMA" + "\"]\n";
      case "Resta" => cadena += contador + "[label=\"" + "RESTA" + "\"]\n";
      case "Multiplicacion" => cadena += contador + "[label=\"" + "MULTIPLICACION" + "\"]\n";
      case "Division" => cadena += contador + "[label=\"" + "DIVISION" + "\"]\n";
      case _ => cadena += contador + "[label=\"" + "UNKNOW" + "\"]\n";
       
    }

    if (nodo.getHijos() != null) {
      var lista: ListBuffer[AstNode] = nodo.getHijos();
      concatenar += nodo.getPadre.getEtiqueta.toString() + "->" + nodo.getEtiqueta.toString() + "\n";
      Visitas(lista, nodo);
    }
  }

  def visit(nodo: AstParamDec) {
   // empty(nodo);
    if (nodo.getTipo == "int") {
      contador += 1
      nodo.setEtiqueta(contador);
      var eti = nodo.getEtiqueta.toString()
      cadena += contador + "[label=\"" + "ParamDec[" + nodo.getIdent + ",INT]" + "\"]\n";
      try {
        var lista: ListBuffer[AstNode] = nodo.getHijos();
        var eti2 = nodo.getPadre.getEtiqueta.toString()
        concatenar += nodo.getPadre.getEtiqueta.toString() + "->" + eti + "\n";
        Visitas(lista, nodo);
      } catch {
        case e: Exception => System.out.println("NODO NULL grapvhiz en PARAMDEC")
      }
    } else {
      contador += 1
      nodo.setEtiqueta(contador);
      cadena += contador + "[label=\"" + "ParamDec[" + nodo.getIdent + ",VOID]" + "\"]\n";
      if (nodo.getHijos() != null) {
        var lista: ListBuffer[AstNode] = nodo.getHijos();
        concatenar += nodo.getPadre.getEtiqueta.toString() + "->" + nodo.getEtiqueta.toString() + "\n";
        Visitas(lista, nodo);
      }
    }

  }

  def visit(nodo: AstCompStmt) {
   // empty(nodo);
    contador += 1
    nodo.setEtiqueta(contador);
    cadena += contador + "[label=\"" + "Cuerpo{}" + "\"]\n";
    try {
      var lista: ListBuffer[AstNode] = nodo.getHijos();
      concatenar += nodo.getPadre.getEtiqueta.toString() + "->" + nodo.getEtiqueta.toString() + "\n";
      Visitas(lista, nodo);
    } catch {
      case e: Exception => System.out.println("NODO NULL grapvhiz en COMPOUNDSTMT")
    }
  }

  def visit(nodo: AstSelectStmt) {
    //empty(nodo);
    if (nodo.getTipo != "else") {
      contador += 1
      nodo.setEtiqueta(contador);
      cadena += contador + "[label=\"Else{}\"]\n";
      try {
        var lista: ListBuffer[AstNode] = nodo.getHijos();
        concatenar += nodo.getPadre.getEtiqueta.toString() + "->" + nodo.getEtiqueta.toString() + "\n";
        Visitas(lista, nodo);
      } catch {
        case e: Exception => System.out.println("NODO NULL grapvhiz en SELECTSTMT")
      }

    } else {
      contador += 1
      nodo.setEtiqueta(contador);
      cadena += contador + "[label=\"If{}\"]\n";
      try {
        var lista: ListBuffer[AstNode] = nodo.getHijos();
        concatenar += nodo.getPadre.getEtiqueta.toString() + "->" + nodo.getEtiqueta.toString() + "\n";
        Visitas(lista, nodo);
      } catch {
        case e: Exception => System.out.println("NODO NULL grapvhiz en SELECTSTMT")
      }
    }
  }

  def visit(nodo: AstIterStmt) {
    if (!nodo.isTieneDoWhile) {
      //empty(nodo);
      contador += 1
      nodo.setEtiqueta(contador);
      cadena += contador + "[label=\"While{}\"]\n";
      try {
        var lista: ListBuffer[AstNode] = nodo.getHijos();
        concatenar += nodo.getPadre.getEtiqueta.toString() + "->" + nodo.getEtiqueta.toString() + "\n";
        Visitas(lista, nodo);
      } catch {
        case e: Exception => System.out.println("NODO NULL grapvhiz en ITERATIONSTMT")
      }
    }else{
      //empty(nodo);
      contador += 1
      nodo.setEtiqueta(contador);
      cadena += contador + "[label=\"For{}\"]\n";
      try {
        var lista: ListBuffer[AstNode] = nodo.getHijos();
        concatenar += nodo.getPadre.getEtiqueta.toString() + "->" + nodo.getEtiqueta.toString() + "\n";
        Visitas(lista, nodo);
      } catch {
        case e: Exception => System.out.println("NODO NULL grapvhiz en ITERATIONSTMT")
      }
    }
  }

  def visit(nodo: AstReturnStmt) {
    //empty(nodo);
    contador += 1
    nodo.setEtiqueta(contador);
    cadena += contador + "[label=\"Return\"]\n";
    try {
      var lista: ListBuffer[AstNode] = nodo.getHijos();
      concatenar += nodo.getPadre.getEtiqueta.toString() + "->" + nodo.getEtiqueta.toString() + "\n";
      Visitas(lista, nodo);
    } catch {
      case e: Exception => System.out.println("NODO NULL grapvhiz en RETURNSTMT")
    }
  }

  def visit(nodo: AstExpAsign) {
    if (nodo.getValor == null) {
      //empty(nodo)
      contador += 1
      nodo.setEtiqueta(contador)
      cadena += contador + " [label=\"Asignacion\"];\n"
      try {
        var lista: ListBuffer[AstNode] = nodo.getHijos()
        concatenar += nodo.getPadre.getEtiqueta.toString() + "->" + nodo.getEtiqueta.toString() + "\n"
        Visitas(lista, nodo);
      } catch {
        case e: Exception => System.out.println("NODO NULL grapvhiz en ASIGNACION")
      }
    } else {
     //empty(nodo)
      contador += 1
      nodo.setEtiqueta(contador)
      cadena += contador + " [label=\"Asignacion, Valor: " + nodo.getValor + "\"]\n"
      try {
        var lista: ListBuffer[AstNode] = nodo.getHijos()
        concatenar += nodo.getPadre.getEtiqueta.toString() + "->" + nodo.getEtiqueta.toString() + "\n"
        Visitas(lista, nodo)
      } catch {
        case e: Exception => System.out.println("NODO NULL grapvhiz en ASIGNACION")
      }
    }

  }

  def visit(nodo: AstExpVar) {
    //empty(nodo);
    if (!nodo.isCorchetes) {
      contador += 1
      nodo.setEtiqueta(contador);
      cadena += contador + "[label=\"" + "Expression[ " + nodo.getIdent + " = " + nodo.getValor + "]" + "\"]\n";
      try {
        var lista: ListBuffer[AstNode] = nodo.getHijos();
        concatenar += nodo.getPadre.getEtiqueta.toString() + "->" + nodo.getEtiqueta.toString() + "\n";
        Visitas(lista, nodo);
      } catch {
        case e: Exception => System.out.println("NODO NULL grapvhiz en EXPRESION")
      }

    } else {
      contador += 1
      nodo.setEtiqueta(contador);
      cadena += contador + "[label=\"" + "Expression " + nodo.getIdent + " = " + nodo.getValor + "\"]\n";
      try {
        var lista: ListBuffer[AstNode] = nodo.getHijos();
        concatenar += nodo.getPadre.getEtiqueta.toString() + "->" + nodo.getEtiqueta.toString() + "\n";
        Visitas(lista, nodo);
      } catch {
        case e: Exception => System.out.println("NODO NULL grapvhiz en EXPRESION")
      }
    }
  }

  def visit(nodo: AstExpConst) {
    //empty(nodo);
    contador += 1
    nodo.setEtiqueta(contador);
    cadena += contador + "[label=\"" + "Constante," + nodo.getValor + "\"]\n";
    try {
      var lista: ListBuffer[AstNode] = nodo.getHijos();
      concatenar += nodo.getPadre.getEtiqueta.toString() + "->" + nodo.getEtiqueta.toString() + "\n";
      Visitas(lista, nodo);
    } catch {
      case e: Exception => System.out.println("NODO NULL grapvhiz en CONSTANTE")
    }
  }

  def visit(nodo: AstExpFun) {
    if (nodo.getTipo == "void") {
      //empty(nodo);
      contador += 1
      nodo.setEtiqueta(contador);
      cadena += contador + "[label=\"" + "Call," + nodo.getIdent +  "(args)"+"\"]\n";
      try {
        var lista: ListBuffer[AstNode] = nodo.getHijos();
        concatenar += nodo.getPadre.getEtiqueta.toString() + "->" + nodo.getEtiqueta.toString() + "\n";
        Visitas(lista, nodo);
      } catch {
        case e: Exception => System.out.println("NODO NULL grapvhiz en CALL")
      }

    } else {
      //empty(nodo);
      contador += 1
      nodo.setEtiqueta(contador);
      cadena += contador + "[label=\"" + "Valor," + nodo.getValor + "\"]\n";
      try {
        var lista: ListBuffer[AstNode] = nodo.getHijos();
        concatenar += nodo.getPadre.getEtiqueta.toString() + "->" + nodo.getEtiqueta.toString() + "\n";
        Visitas(lista, nodo);
      } catch {
        case e: Exception => System.out.println("NODO NULL grapvhiz en CALLVALOR")
      }
    }
  }

  def visit(nodo: AstVaciaStmt) {
    contador += 1
    nodo.setEtiqueta(contador);
    cadena += contador + "[label=\"" + "Vacio," + "\"]\n";
    try {
      var lista: ListBuffer[AstNode] = nodo.getHijos();
      concatenar += nodo.getPadre.getEtiqueta.toString() + "->" + nodo.getEtiqueta.toString() + "\n";
      Visitas(lista, nodo);
    } catch {
      case e: Exception => System.out.println("NODO NULL grapvhiz en VACIOSTMT")
    }
  }
}
