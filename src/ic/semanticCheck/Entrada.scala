package semanticCheck

import Ast._
import Visitor.AstVisitor
import java.util.ArrayList
import semanticCheck._
import GraphVisitor.GrapherVisitor

class Entrada {

    var name: String = _

    var tipo: String = _

    var indice: Int = _

    var tipoNodo: String = _

    var next: Entrada = _

}