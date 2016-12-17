package ic.lex

import java_cup.runtime.Symbol;
// TODO: Write me.
class Token(id : Int, value : Object, line : Int) extends Symbol(id,line,line,value) {
	
	override def toString() = {
		"[" + this.sym + "," + this.value + "," + this.left + "]";
	}
}
