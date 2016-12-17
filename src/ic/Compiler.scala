package ic

import ic.lex.Lexer;
import ic.lex.Token;
import ic.error.LexicalError;
import Parser._;
import GraphVisitor._;
import java_cup.runtime._;
import semanticCheck._;
import Ast._;


import java.io._;
import scala.io.Source

	
/**
 * The main class for the IC Compiler.  The Compiler
 * expects the name of the file to process on the command line,
 * as in:
 * <pre>
 *   scala -classpath bin ic.Compiler test.ic
 * </pre>
 * An optional flag <tt>-d<tt> can be provided, as in:
 * <pre>
 *   scala -classpath bin ic.Compiler -d test.ic
 * </pre>
 * In this case, messages created by calling {@link ic.Util#debug(String)}
 * or {@link ic.Util#debug(String, Object...)} will be printed to the
 * terminal.  If <tt>-d</tt> is not provided, these messages will
 * be silently ignored.
 */
object Compiler {

/*	def printFunct(lex:Lexer):Unit={
		val NextToken: Token = lex.next_token()
		if(NextToken.id == sym.EOF){
			println("End of File")
			return;
		}
		if(NextToken.valor !=null){
			println(NextToken.id+  " valor =>" + NextToken.valor)
		}
		else{
			println(NextToken.id)
		}
		if(NextToken !=null){
			printFunct(lex)
		}
	}*/
	def main(args : Array[String]) = {

		var n = 0;

		// If first command line argument is -d, turn on debugging
		if (args.length > 0 && args(n).equals("-d")) {
			Util.debug = true;
			n = 1;
		}

		// Get name of file
		if (args.length == n) {
			System.out.println("No file given.");			
		} else {
			val file = args(n);

			// example of debug message: This message will only be printed if
			// you provide "-d" on the command line.
			Util.debug("Processing %s...", file);
			// TODO: finish me:
			val lex : Lexer = new Lexer(new BufferedReader(new FileReader(file)));
			val programParser : parser = new parser(lex);
			//val resultValue = programParser.parse().value;
			
			try{
				if(programParser!=null){
					programParser.parse();
	    			//Se crea la estructura visitor.
	    			var vist : GrapherVisitor = new GrapherVisitor();
	    			//Se obtiene el nodo raiz.	
	    			if(programParser.raiz!=null){
	    				vist.visit(programParser.raiz.asInstanceOf[AstProgram]);
	    				var concatenar:String = vist.getConcatenar();
						var cadena:String = vist.getCadena();
	    				//var cadenaGraph : String = vist.retornaCadenaGraph(); //Cadena que contiene el grapher.
	    				generarArchivoDot(cadena,concatenar); //Metodo que genera el archivo .dot.
	    				var nomArch :String = "result.txt";
		    			generarArchivoSalida(programParser.produc,nomArch); //Traspasa las producciones a un archivo.
		    			//invertirArchivo(nomArch); //Invierte el archivo.

				    	/*Analisis Semantico*/
				        //se crea un visitor para realizar el analisis de alcance
				        var visitAlcance:VisitorAlcance = new VisitorAlcance();
				        //se le entrega la raiz del arbol para realizar el analisis de alcance y se recorre el arbol
				        programParser.raiz.revisarAlcance(visitAlcance);
				        System.out.println("NO SE ENCONTRARON ERRORES EN LA FASE DE ANALISIS DE ALCANCE");
				        //ahora se cera un visitor para realizar el chequeo de tipos
				        var chequeaTipos:VisitorTipo = new VisitorTipo();
				        //se le entrega el root para realizar el chequeo de tipo
				        programParser.raiz.revisarTipos(chequeaTipos);
				        System.out.println("NO SE ENCONTRARON ERRORES EN LA FASE DE CHEQUEO DE TIPOS");
		    			
	            	}else{
	            		println("No hay raiz");	
	            	}
            	}else{
            		println("No se crea el parser");
            	}
			}catch{
				case le: LexicalError => {println(le.message + ", line "+le.getLine())}
				case e: Exception => {println("Error no esperado: " +e)}
			}
		}

	}

    def generarArchivoDot(cadena: String, concatenar:String)={
        try {
        	var cadenaPathDot:String = "digraph g{" + concatenar + cadena + "}";
            val rutaResult = new File(System.getProperty("user.dir")+ "/" + "prueba.dot");
            var wr = new BufferedWriter(new FileWriter(rutaResult));
            wr.write(cadenaPathDot);
            wr.close();
            println("Archivo .DOT generado correctamente.");
       
        } catch {
        	case e: IOException => {println("Error al generar ArchivoDOT: "+e)}
        }
    }

     def generarArchivoSalida(resultado: String, archivo: String) ={
     	try{
	        val rutaResult = new File(System.getProperty("user.dir")+ "/" + archivo);
	    	val writer = new BufferedWriter(new FileWriter(archivo));
	    	writer.write(resultado);
	    	writer.close();
	    	println("Archivo generado de producciones correctamente.");
    	}
    	catch{
    		case io: IOException=> {println ("Error en creación archivo de salida: "+io)}	
    	}
    }	

}
