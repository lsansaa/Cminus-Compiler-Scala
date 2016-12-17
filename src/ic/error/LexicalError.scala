package ic.error;


class LexicalError(val line : Int, val message : String) extends Error(message) {
	
	/**
	 * Return the line number of where the error occurred.
	 */
	def getLine() : Integer = line;
  
}