package semanticCheck

import Ast._
import Visitor.AstVisitor
import java.util.ArrayList
import semanticCheck._
import GraphVisitor.GrapherVisitor

class TabSimbol {

    var tipoNodo: String = _

    var tamaño: Int = _

    var root: Entrada = _

    var next: TabSimbol = _
}
