package ic.lex	;
import ic.error.LexicalError;
import java_cup.runtime.*;
import Parser.sym;

%%

%class Lexer
%public
%function next_token
%type Token
%line
%ignorecase
%cup

%{
  /** for use in building string literals in the STRING state */
  StringBuffer stringLiteral = new StringBuffer();
  
  /** Create a token with the given type and the given value */
  Token create(int type, Object value) {
    return new Token(type, value, yyline+1);
  }
  
  /** Create a token with the given type and no value */
  Token create(int type) {
    return create(type, null);
  }
%}


IDENTIFIER = [A-Za-z0-9]*
DIGIT = [0-9]
WHITE=[ \t\r\n]*
ANY=.

%%

{WHITE} {/*Ignore*/}
"String" {return create(sym.STRING);}
"::=" {return create(sym.ASSIGN);}
"==" {return create(sym.EQ);}
"+" {return create(sym.ADD);}
"*" {return create(sym.MULT);}
"-" {return create(sym.SUBT);}
"/" {return create(sym.DIV);}
"**" {return create(sym.ROOT);}
"^" {return create(sym.POT);}
"(" {return create(sym.OPENBRACKET);}
"[" {return create(sym.OPENBRACKETCOR);}
"{" {return create(sym.OPENBRACKETLL);}
")" {return create(sym.CLOSEBRACKET);}
"}" {return create(sym.CLOSEBRACKETLL);}
"]" {return create(sym.CLOSEBRACKETCOR);}
";" {return create(sym.PUNTUACTION);}
"," {return create(sym.COMA);}
"<" {return create(sym.MINOR);}
">" {return create(sym.MAYOR);}
"<=" {return create(sym.MINEQ);}
">=" {return create(sym.MAYEQ);}
"!=" {return create(sym.NEQ);}
[i|I][f|F] {return create(sym.IF);}
[e|E][l|L][s|S][e|E] {return create(sym.ELSE);}
[v|V][o|O][i|I][d|D] {return create(sym.VOID);}
[f|F][o|O][r|R] {return create(sym.FOR);}
[r|R][e|E][t|T][u|U][r|R][n|N] {return create(sym.RETURN);}
[w|W][h|H][i|I][l|L][e|E] {return create(sym.WHILE);}
[i|I][n|N][t|T] {return create(sym.INT);}

"/#"([^*]|[\r\n]|(\*+([^*/]|[\r\n])))*"#/"|[%]{ANY}*[\n]? {return create(sym.COMMENT);}


[a-z]({IDENTIFIER}|{DIGIT})*[_]?({IDENTIFIER}|{DIGIT})* {return create(sym.ID, yytext());}
[1-9]({DIGIT})+|{DIGIT} {return create(sym.NUM, yytext());}

{ANY} {return create (sym.error,"LexicalError '" + yytext() + "'");}

<<EOF>> {return create(sym.EOF);}