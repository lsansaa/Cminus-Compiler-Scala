#
# Makefile for build IC Compiler.  
#
# Requires SCALA_HOME to be set correctly (via the setup434 script)
#
# To build the source files, simply type "make" on the command line.
#
# It supports the following modes:
#
#   - source:    run JFlex on ic.flex and then build java files
#   - dump:    same as "source" but dump out JavaCup info
#   - clean:     removes all class files and ~ files
#   - all:       all of the above.
#

source: flexcup
	mkdir -p bin
	fsc -d bin -classpath .:tools/java-cup-11a.jar `find src -name "*.java"` `find src -name "*.scala"`
	javac -d bin -classpath .:bin:tools/java-cup-11a.jar:${SCALA_HOME}/lib/scala-library.jar `find src -name "*.java"` 

flexcup:
	java -jar tools/java-cup-11a.jar -destdir src/ic/parser src/ic/parser/ic.cup
	java -jar tools/JFlex.jar src/ic/lex/ic.flex

dump:
	mkdir -p bin
	java -jar tools/java-cup-11a.jar -dump -destdir src/ic/parser src/ic/parser/ic.cup
	java -jar tools/JFlex.jar src/ic/lex/ic.flex
	fsc -d bin -classpath .:tools/java-cup-11a.jar `find src -name "*.java"` `find src -name "*.scala"`
	javac -d bin -classpath .:bin:tools/java-cup-11a.jar:${SCALA_HOME}/lib/scala-library.jar `find src -name "*.java"` 


clean:
	fsc -shutdown
	rm -f src/ic/lex/Lexer.java
	rm -f src/ic/parser/parser.java
	rm -f src/ic/parser/sym.java
	rm -rf bin/*

all:	clean source 
