package ic

import scala.collection._;

/**
 * These utility methods create Scala lists and option values.
 * Use them in your parser to create these objects easily.
 * 
 * Your AST should not store any Java collection objects. If you 
 * wish to use other types of collections, simply add similar
 * construction methods to this file. 
 * 
 * Usage:
 * 
 * In Java code or CUP file, use these methods as follows, after importing
 * the Scala classes:
 * 
 *    import scala.collection.immutable.*;
 * 
 *    ...
 * 
 *    List<String> l = ParserUtil.empty();
 *    l = ParserUtil.cons(l, "cow");
 *    l = ParserUtil.append(l, "moo");
 *    
 *    l.length() // or any other Scala list method
 *    
 *    Option<String> s = ParserUtil.none();
 *    s = ParserUtil.some("moo");
 *  
 *    s.isDefined() // or any other Scala Option method
 * 
 */
object ParserUtil {

	/**
	 * Create an empty list.
	 */
	def empty[A](): List[A] = List();

	/**
	 * Insert element a onto the front of list l.
	 */
	def cons[A,T <: A](a: T, l: List[A]): List[A] =  a :: l;

	/**
	 * Append element a onto the list l.
	 */
	def append[A,T <: A](l: List[A], a: T): List[A] = l :+ a;

	/**
	 * Create the empty value for Option[A].
	 */
	def none[A]() : Option[A] = None;

	/**
	 * Create the value Some(a) for type Option[A].
	 */
	def some[A](a : A) : Option[A] = Some(a);

}

