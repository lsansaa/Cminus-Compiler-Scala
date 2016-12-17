package ic;

/**
 * 
 * A few useful utility messages for doing the following:
 * <ul>
 * <li> assert that conditions are true.  Use this liberally.
 * 
 * <li> print debugging messages when the program is running,
 *   provided that Util.debug has been set to true.  
 * </ul>  
 * 
 * Feel free to add other utility routines to this class as well.
 */
object Util {

	/*****************************************************************/

	/**
	 * Test whether a condition is true, and fail with a message
	 * if it is not.  Alternatively, use the Java 1.5 <tt>assert</tt>
	 * statement and run Java wit the <tt>-ea</tt> option.
	 */
	def assertTrue(test : Boolean, message : String) : Unit = {
		assertTrue(test, "%s", message);
	}


	/**
	 * Test whether a condition is true, and fail with a message
	 * if it is not.  Alternatively, use the Java 1.5 <tt>assert</tt>
	 * statement and run Java wit the <tt>-ea</tt> option. 
	 * <p>
	 * This version takes printf-style arguments to make formatting easier.
	 * See {@link java.io.PrintStream#printf(String, Object...)} for more details.
	 */
	def assertTrue(test : Boolean, format : String, args : Object*) : Unit = {
		if (!test) {
			System.err.printf(format, args.toArray:_*);
			System.err.println();
			new Throwable().printStackTrace(System.err);
			System.exit(1);
		}
	}


	/******************************************************************/


	/**
	 * Set this to true to print out messages with the debug() methods.
	 * Otherwise, those methods do nothing.
	 */
	var debug = false;

	/**
	 * Print a message to the terminal, if debugging
	 * messages has been enabled by setting {@link Util#debug}.
	 */
	def debug(message : String) : Unit = {
		debug("%s", message);
	}

	/**
	 * Print a message to the terminal using printf-style arguments, 
	 * if debugging messages has been enabled by setting
	 * {@link Util#debug}.
	 * <p>
	 * See {@link java.io.PrintStream#printf(String, Object...)} for more details.
	 */
	def debug(format : String, args : Object*) : Unit = {
		if (debug) {
			System.out.printf("[" + format + "]\n", args.toArray:_*);
		}
	}


	/******************************************************************/


}
