//### This file created by BYACC 1.8(/Java extension  1.13)
//### Java capabilities added 7 Jan 97, Bob Jamison
//### Updated : 27 Nov 97  -- Bob Jamison, Joe Nieten
//###           01 Jan 98  -- Bob Jamison -- fixed generic semantic constructor
//###           01 Jun 99  -- Bob Jamison -- added Runnable support
//###           06 Aug 00  -- Bob Jamison -- made state variables class-global
//###           03 Jan 01  -- Bob Jamison -- improved flags, tracing
//###           16 May 01  -- Bob Jamison -- added custom stack sizing
//###           04 Mar 02  -- Yuval Oren  -- improved java performance, added options
//###           14 Mar 02  -- Tomas Hurka -- -d support, static initializer workaround
//###           14 Sep 06  -- Keltin Leung-- ReduceListener support, eliminate underflow report in error recovery
//### Please send bug reports to tom@hukatronic.cz
//### static char yysccsid[] = "@(#)yaccpar	1.8 (Berkeley) 01/20/90";






//#line 11 "Parser.y"
package decaf.frontend;

import decaf.tree.Tree;
import decaf.tree.Tree.*;
import decaf.error.*;
import java.util.*;
//#line 25 "Parser.java"
interface ReduceListener {
  public boolean onReduce(String rule);
}




public class Parser
             extends BaseParser
             implements ReduceListener
{

boolean yydebug;        //do I want debug output?
int yynerrs;            //number of errors so far
int yyerrflag;          //was there an error?
int yychar;             //the current working character

ReduceListener reduceListener = null;
void yyclearin ()       {yychar = (-1);}
void yyerrok ()         {yyerrflag=0;}
void addReduceListener(ReduceListener l) {
  reduceListener = l;}


//########## MESSAGES ##########
//###############################################################
// method: debug
//###############################################################
void debug(String msg)
{
  if (yydebug)
    System.out.println(msg);
}

//########## STATE STACK ##########
final static int YYSTACKSIZE = 500;  //maximum stack size
int statestk[] = new int[YYSTACKSIZE]; //state stack
int stateptr;
int stateptrmax;                     //highest index of stackptr
int statemax;                        //state when highest index reached
//###############################################################
// methods: state stack push,pop,drop,peek
//###############################################################
final void state_push(int state)
{
  try {
		stateptr++;
		statestk[stateptr]=state;
	 }
	 catch (ArrayIndexOutOfBoundsException e) {
     int oldsize = statestk.length;
     int newsize = oldsize * 2;
     int[] newstack = new int[newsize];
     System.arraycopy(statestk,0,newstack,0,oldsize);
     statestk = newstack;
     statestk[stateptr]=state;
  }
}
final int state_pop()
{
  return statestk[stateptr--];
}
final void state_drop(int cnt)
{
  stateptr -= cnt; 
}
final int state_peek(int relative)
{
  return statestk[stateptr-relative];
}
//###############################################################
// method: init_stacks : allocate and prepare stacks
//###############################################################
final boolean init_stacks()
{
  stateptr = -1;
  val_init();
  return true;
}
//###############################################################
// method: dump_stacks : show n levels of the stacks
//###############################################################
void dump_stacks(int count)
{
int i;
  System.out.println("=index==state====value=     s:"+stateptr+"  v:"+valptr);
  for (i=0;i<count;i++)
    System.out.println(" "+i+"    "+statestk[i]+"      "+valstk[i]);
  System.out.println("======================");
}


//########## SEMANTIC VALUES ##########
//## **user defined:SemValue
String   yytext;//user variable to return contextual strings
SemValue yyval; //used to return semantic vals from action routines
SemValue yylval;//the 'lval' (result) I got from yylex()
SemValue valstk[] = new SemValue[YYSTACKSIZE];
int valptr;
//###############################################################
// methods: value stack push,pop,drop,peek.
//###############################################################
final void val_init()
{
  yyval=new SemValue();
  yylval=new SemValue();
  valptr=-1;
}
final void val_push(SemValue val)
{
  try {
    valptr++;
    valstk[valptr]=val;
  }
  catch (ArrayIndexOutOfBoundsException e) {
    int oldsize = valstk.length;
    int newsize = oldsize*2;
    SemValue[] newstack = new SemValue[newsize];
    System.arraycopy(valstk,0,newstack,0,oldsize);
    valstk = newstack;
    valstk[valptr]=val;
  }
}
final SemValue val_pop()
{
  return valstk[valptr--];
}
final void val_drop(int cnt)
{
  valptr -= cnt;
}
final SemValue val_peek(int relative)
{
  return valstk[valptr-relative];
}
//#### end semantic value section ####
public final static short VOID=257;
public final static short BOOL=258;
public final static short INT=259;
public final static short STRING=260;
public final static short CLASS=261;
public final static short NULL=262;
public final static short EXTENDS=263;
public final static short THIS=264;
public final static short WHILE=265;
public final static short FOR=266;
public final static short IF=267;
public final static short ELSE=268;
public final static short RETURN=269;
public final static short BREAK=270;
public final static short NEW=271;
public final static short PRINT=272;
public final static short READ_INTEGER=273;
public final static short READ_LINE=274;
public final static short LITERAL=275;
public final static short IDENTIFIER=276;
public final static short AND=277;
public final static short OR=278;
public final static short STATIC=279;
public final static short INSTANCEOF=280;
public final static short LESS_EQUAL=281;
public final static short GREATER_EQUAL=282;
public final static short EQUAL=283;
public final static short NOT_EQUAL=284;
public final static short INC=285;
public final static short DEC=286;
public final static short NUMINSTANCES=287;
public final static short GUARDSEP=288;
public final static short FI=289;
public final static short OD=290;
public final static short DO=291;
public final static short UMINUS=292;
public final static short EMPTY=293;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    1,    1,    3,    4,    5,    5,    5,    5,    5,
    5,    2,    6,    6,    7,    7,    7,    9,    9,   10,
   10,    8,    8,   11,   12,   12,   13,   13,   13,   13,
   13,   13,   13,   13,   13,   13,   13,   21,   22,   23,
   23,   24,   14,   14,   14,   28,   28,   26,   26,   27,
   25,   25,   25,   25,   25,   25,   25,   25,   25,   25,
   25,   25,   25,   25,   25,   25,   25,   25,   25,   25,
   25,   25,   25,   25,   25,   25,   25,   25,   25,   25,
   25,   25,   30,   30,   29,   29,   31,   31,   16,   17,
   20,   15,   32,   32,   18,   18,   19,
};
final static short yylen[] = {                            2,
    1,    2,    1,    2,    2,    1,    1,    1,    1,    2,
    3,    6,    2,    0,    2,    2,    0,    1,    0,    3,
    1,    7,    6,    3,    2,    0,    1,    2,    1,    1,
    1,    2,    2,    2,    1,    1,    1,    3,    3,    3,
    1,    3,    3,    1,    0,    2,    0,    2,    4,    5,
    2,    2,    2,    2,    1,    1,    1,    4,    5,    3,
    3,    3,    3,    3,    3,    3,    3,    3,    3,    3,
    3,    3,    3,    2,    2,    3,    3,    1,    4,    5,
    6,    5,    1,    1,    1,    0,    3,    1,    5,    9,
    1,    6,    2,    0,    2,    1,    4,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    3,    0,    2,    0,    0,   13,   17,
    0,    7,    8,    6,    9,    0,    0,   12,   15,    0,
    0,   16,   10,    0,    4,    0,    0,    0,    0,   11,
    0,   21,    0,    0,    0,    0,    5,    0,    0,    0,
   26,   23,   20,   22,    0,   84,   78,    0,    0,    0,
    0,   91,    0,    0,    0,    0,   83,    0,    0,    0,
    0,   24,    0,    0,    0,    0,   27,   35,   25,    0,
   29,   30,   31,    0,    0,    0,   36,   37,    0,    0,
    0,    0,   57,    0,    0,    0,    0,   41,    0,    0,
   56,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   28,   32,   33,   34,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   46,    0,    0,    0,   51,   54,    0,
    0,    0,    0,    0,   38,    0,    0,    0,    0,    0,
   76,   77,    0,    0,   73,    0,   39,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   40,   42,   79,
    0,    0,   97,    0,    0,   58,   49,    0,    0,    0,
   89,    0,    0,   80,    0,    0,   82,    0,   50,    0,
    0,   92,   81,    0,   93,    0,   90,
};
final static short yydgoto[] = {                          2,
    3,    4,   67,   20,   33,    8,   11,   22,   34,   35,
   68,   45,   69,   70,   71,   72,   73,   74,   75,   76,
   77,   78,   87,   88,   79,   90,   91,   82,  179,   83,
  140,  192,
};
final static short yysindex[] = {                      -232,
 -242,    0, -232,    0, -217,    0, -215,  -55,    0,    0,
 -102,    0,    0,    0,    0, -202, -164,    0,    0,   25,
  -90,    0,    0,  -87,    0,   51,    5,   59, -164,    0,
 -164,    0,  -85,   65,   71,   79,    0,   -4, -164,   -4,
    0,    0,    0,    0,    2,    0,    0,   81,   82,  128,
  136,    0, -150,   83,   84,   88,    0,   89,  136,  136,
  100,    0,  136,  136,   90,  136,    0,    0,    0,   75,
    0,    0,    0,   76,   80,   85,    0,    0,  977,  -41,
    0, -139,    0,  136,  136,  100, -264,    0,  653, -198,
    0,  977,  102,   52,  136,  105,  106,  136,  -29,  -29,
 -126,  695, -198, -198, -124, -247,    0,    0,    0,    0,
  136,  136,  136,  136,  136,  136,  136,  136,  136,  136,
  136,  136,  136,    0,  136,  136,  136,    0,    0,  109,
  719,   95,  806,  136,    0,   37,  121,  108,  977,  -23,
    0,    0,  838,  122,    0,  123,    0, 1063, 1010,   11,
   11,  -32,  -32,  -15,  -15,  -29,  -29,  -29,   11,   11,
  862,  896,  977,  136,   37,  136,   72,    0,    0,    0,
  920,  136,    0, -111,  136,    0,    0,  136,  126,  127,
    0,  947,  -98,    0,  977,  131,    0,  977,    0,  136,
   37,    0,    0,  133,    0,   37,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,  175,    0,   55,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  120,    0,    0,  142,    0,
  142,    0,    0,    0,  143,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  -57,    0,    0,    0,    0,  -91,
  -56,    0,    0,    0,    0,    0,    0,    0,  -91,  -91,
  -91,    0,  -91,  -91,    0,  -91,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  988,
  612,    0,    0,  -91,  -57,  -91,    0,    0,    0,  410,
    0,  129,    0,    0,  -91,    0,    0,  -91,  155,  506,
    0,    0,  446,  470,    0,    0,    0,    0,    0,    0,
  -91,  -91,  -91,  -91,  -91,  -91,  -91,  -91,  -91,  -91,
  -91,  -91,  -91,    0,  -91,  -91,  -91,    0,    0,  383,
    0,    0,    0,  -91,    0,  -57,    0,  -91,   31,    0,
    0,    0,    0,    0,    0,    0,    0,   73,   -8,    8,
  587, 1125, 1148, 1000, 1089,  515,  542,  551, 1033, 1097,
    0,    0,  -22,  -25,  -57,  -91,  579,    0,    0,    0,
    0,  -91,    0,    0,  -91,    0,    0,  -91,    0,  146,
    0,    0,  -33,    0,   39,    0,    0,   45,    0,    3,
  -57,    0,    0,    0,    0,  -57,    0,
};
final static short yygindex[] = {                         0,
    0,  187,  182,   34,   28,    0,    0,    0,  163,    0,
   78,    0, -127,  -77,    0,    0,    0,    0,    0,    0,
    0,    0,  137,   70, 1326,   15,  578,    0,    0,    0,
   43,    0,
};
final static int YYTABLESIZE=1504;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         94,
   27,   45,   96,   27,  121,   27,   94,  132,  169,  119,
  117,   94,  118,  124,  120,   86,  124,  173,   43,  127,
  172,  121,   18,  134,  135,   94,  119,  123,    1,  122,
  124,  120,   72,    5,   60,   72,   43,  181,   21,  183,
  134,   61,  147,   45,   24,    7,   59,  121,   69,   72,
   72,   69,  119,  117,   72,  118,  124,  120,  125,   80,
    9,  125,   32,  195,   32,   69,   69,   10,  197,   60,
   69,   88,   43,   23,   88,  125,   61,  103,  104,   87,
   94,   59,   87,   25,   72,   59,  128,  129,   59,   94,
   29,   94,   12,   13,   14,   15,   16,   30,   31,   80,
   69,  125,   59,   59,   60,   38,   12,   13,   14,   15,
   16,   61,  194,   71,   39,   42,   71,   44,   41,   40,
   84,   85,   95,   96,   41,   93,   62,   97,   98,  105,
   71,   71,   60,  107,  108,   71,  130,   59,  109,   61,
   60,  137,  138,  110,   59,  141,  142,   61,  164,  144,
   80,  146,   59,  166,   12,   13,   14,   15,   16,   41,
   60,  170,  175,  176,  186,   71,  189,   86,   60,  191,
  172,  193,   59,  196,    1,   61,   17,   14,    5,   80,
   59,   80,   19,   18,   47,   26,   85,   95,   28,    6,
   37,   74,   19,   36,   41,   74,   74,   74,   74,   74,
   30,   74,  106,  168,   80,   80,  180,    0,    0,    0,
   80,    0,   74,   74,   74,    0,   74,   74,   47,   47,
    0,    0,    0,   94,   94,   94,   94,   94,   94,    0,
   94,   94,   94,   94,    0,   94,   94,   94,   94,   94,
   94,   94,   94,  128,  129,    0,   94,   74,  113,  114,
   47,   94,   94,   94,   94,   94,   94,   94,   12,   13,
   14,   15,   16,   46,    0,   47,   48,   49,   50,   72,
   51,   52,   53,   54,   55,   56,   57,    0,   47,    0,
    0,   58,    0,    0,   69,   69,   63,   64,   65,    0,
   69,   69,   66,   12,   13,   14,   15,   16,   46,    0,
   47,   48,   49,   50,    0,   51,   52,   53,   54,   55,
   56,   57,    0,    0,    0,    0,   58,    0,    0,    0,
    0,   63,   64,   65,    0,    0,    0,   66,   12,   13,
   14,   15,   16,   46,    0,   47,   48,   49,   50,    0,
   51,   52,   53,   54,   55,   56,   57,    0,    0,   71,
   71,   58,    0,    0,    0,    0,   63,   64,   65,    0,
  101,   46,   66,   47,    0,    0,    0,    0,    0,   46,
   53,   47,   55,   56,   57,    0,    0,    0,   53,   58,
   55,   56,   57,    0,   63,   64,   65,   58,    0,   46,
    0,   47,   63,   64,   65,    0,    0,   46,   53,   47,
   55,   56,   57,    0,    0,    0,   53,   58,   55,   56,
   57,    0,   63,   64,   65,   58,    0,    0,    0,   48,
   63,   64,   65,   48,   48,   48,   48,   48,   48,   48,
    0,   74,   74,    0,    0,   74,   74,   74,   74,    0,
   48,   48,   48,   48,   48,   48,   55,    0,    0,    0,
   55,   55,   55,   55,   55,   55,   55,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   55,   55,   55,
    0,   55,   55,   48,    0,   48,    0,    0,    0,    0,
    0,    0,   52,    0,    0,    0,   52,   52,   52,   52,
   52,   52,   52,    0,    0,    0,    0,    0,    0,    0,
   55,    0,   55,   52,   52,   52,   53,   52,   52,    0,
   53,   53,   53,   53,   53,   53,   53,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   53,   53,   53,
    0,   53,   53,    0,    0,    0,   52,    0,   52,    0,
    0,    0,   75,    0,    0,    0,   75,   75,   75,   75,
   75,   62,   75,    0,    0,   62,   62,   62,   62,   62,
   53,   62,   53,   75,   75,   75,    0,   75,   75,    0,
    0,    0,   62,   62,   62,    0,   62,   62,   63,    0,
    0,    0,   63,   63,   63,   63,   63,   64,   63,    0,
    0,   64,   64,   64,   64,   64,    0,   64,   75,   63,
   63,   63,    0,   63,   63,    0,    0,   62,   64,   64,
   64,    0,   64,   64,    0,   73,    0,    0,    0,    0,
   73,   73,   81,   73,   73,   73,    0,   70,    0,    0,
   70,    0,    0,    0,   63,    0,   73,   45,   73,    0,
   73,   73,    0,   64,   70,   70,    0,    0,   56,   70,
    0,    0,   44,   56,   56,    0,   56,   56,   56,   48,
   48,    0,   81,   48,   48,   48,   48,   48,   48,   73,
   44,   56,    0,   56,   56,    0,    0,    0,    0,   70,
    0,    0,    0,    0,    0,    0,   55,   55,    0,  121,
   55,   55,   55,   55,  119,  117,    0,  118,  124,  120,
    0,    0,   56,    0,    0,    0,    0,    0,    0,    0,
  136,    0,  123,   81,  122,  126,    0,    0,    0,    0,
    0,    0,   52,   52,    0,    0,   52,   52,   52,   52,
    0,  121,    0,    0,    0,  145,  119,  117,    0,  118,
  124,  120,   81,  125,   81,    0,   53,   53,    0,    0,
   53,   53,   53,   53,  123,  121,  122,  126,    0,  165,
  119,  117,    0,  118,  124,  120,    0,   81,   81,    0,
    0,    0,    0,   81,    0,    0,    0,    0,  123,    0,
  122,  126,   75,   75,    0,  125,   75,   75,   75,   75,
    0,   62,   62,    0,    0,   62,   62,   62,   62,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  125,
    0,    0,    0,    0,    0,    0,    0,    0,   63,   63,
    0,    0,   63,   63,   63,   63,    0,   64,   64,    0,
    0,   64,   64,   64,   64,    0,    0,    0,    0,    0,
    0,    0,  121,    0,    0,    0,  167,  119,  117,    0,
  118,  124,  120,    0,   47,   73,   73,    0,    0,   73,
   73,   73,   73,   70,   70,  123,    0,  122,  126,   70,
   70,    0,    0,    0,  121,    0,    0,    0,    0,  119,
  117,  174,  118,  124,  120,    0,    0,    0,   56,   56,
    0,    0,   56,   56,   56,   56,  125,  123,  121,  122,
  126,    0,    0,  119,  117,    0,  118,  124,  120,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  123,    0,  122,  126,    0,    0,    0,  125,  111,
  112,    0,  121,  113,  114,  115,  116,  119,  117,    0,
  118,  124,  120,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  125,  178,  177,  123,  121,  122,  126,    0,
    0,  119,  117,    0,  118,  124,  120,    0,    0,    0,
    0,  111,  112,    0,    0,  113,  114,  115,  116,  123,
    0,  122,  126,  121,    0,    0,  125,    0,  119,  117,
    0,  118,  124,  120,    0,  111,  112,    0,    0,  113,
  114,  115,  116,    0,    0,  190,  123,    0,  122,  126,
  125,    0,  184,  121,    0,    0,    0,    0,  119,  117,
    0,  118,  124,  120,   55,    0,    0,    0,    0,   55,
   55,    0,   55,   55,   55,    0,  123,  125,  122,  126,
   60,    0,   60,   60,   60,    0,  121,   55,    0,   55,
   55,  119,  117,    0,  118,  124,  120,   60,   60,   60,
    0,   60,   60,    0,    0,    0,    0,  125,    0,  123,
    0,  122,    0,   68,    0,    0,   68,    0,   55,    0,
    0,    0,  111,  112,    0,    0,  113,  114,  115,  116,
   68,   68,   60,    0,    0,   68,    0,    0,    0,  121,
  125,    0,    0,    0,  119,  117,    0,  118,  124,  120,
    0,    0,    0,    0,  111,  112,    0,    0,  113,  114,
  115,  116,  123,    0,  122,   68,    0,    0,    0,   61,
    0,   61,   61,   61,    0,    0,    0,   67,  111,  112,
   67,    0,  113,  114,  115,  116,   61,   61,   61,    0,
   61,   61,    0,  125,   67,   67,    0,    0,    0,   67,
    0,    0,    0,    0,    0,   65,    0,    0,   65,    0,
    0,    0,  111,  112,    0,    0,  113,  114,  115,  116,
    0,   61,   65,   65,    0,    0,    0,   65,   66,   67,
    0,   66,    0,    0,    0,    0,  111,  112,    0,    0,
  113,  114,  115,  116,    0,   66,   66,    0,    0,    0,
   66,    0,    0,    0,    0,    0,    0,   65,    0,    0,
    0,    0,    0,  111,  112,    0,    0,  113,  114,  115,
  116,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   66,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  111,  112,    0,    0,  113,  114,  115,
  116,    0,    0,    0,   55,   55,    0,    0,   55,   55,
   55,   55,    0,    0,    0,    0,   60,   60,    0,    0,
   60,   60,   60,   60,    0,    0,  111,    0,    0,    0,
  113,  114,  115,  116,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   68,
   68,    0,    0,    0,    0,   68,   68,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  113,  114,  115,  116,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   61,   61,    0,    0,   61,
   61,   61,   61,   67,   67,   89,   92,    0,    0,   67,
   67,    0,    0,    0,   99,  100,  102,    0,    0,    0,
    0,   89,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   65,   65,    0,    0,    0,    0,    0,    0,  131,
    0,  133,    0,    0,    0,    0,    0,    0,    0,    0,
  139,    0,    0,  143,   66,   66,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  148,  149,  150,  151,
  152,  153,  154,  155,  156,  157,  158,  159,  160,    0,
  161,  162,  163,    0,    0,    0,    0,    0,    0,   89,
    0,    0,    0,  171,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  139,
    0,  182,    0,    0,    0,    0,    0,  185,    0,    0,
  187,    0,    0,  188,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         33,
   91,   59,   59,   91,   37,   91,   40,   85,  136,   42,
   43,   45,   45,   46,   47,   41,   46,   41,   41,   61,
   44,   37,  125,  288,  289,   59,   42,   60,  261,   62,
   46,   47,   41,  276,   33,   44,   59,  165,   11,  167,
  288,   40,  290,   41,   17,  263,   45,   37,   41,   58,
   59,   44,   42,   43,   63,   45,   46,   47,   91,   45,
  276,   91,   29,  191,   31,   58,   59,  123,  196,   33,
   63,   41,   39,  276,   44,   91,   40,   63,   64,   41,
   53,   45,   44,   59,   93,   41,  285,  286,   44,  123,
   40,  125,  257,  258,  259,  260,  261,   93,   40,   85,
   93,   91,   58,   59,   33,   41,  257,  258,  259,  260,
  261,   40,  190,   41,   44,   38,   44,   40,  123,   41,
   40,   40,   40,   40,  123,  276,  125,   40,   40,   40,
   58,   59,   33,   59,   59,   63,  276,   93,   59,   40,
   33,   40,   91,   59,   45,   41,   41,   40,   40,  276,
  136,  276,   45,   59,  257,  258,  259,  260,  261,  123,
   33,   41,   41,   41,  276,   93,   41,   40,   33,  268,
   44,   41,   45,   41,    0,   40,  279,  123,   59,  165,
   45,  167,   41,   41,  276,  276,   41,   59,  276,    3,
  276,   37,   11,   31,  123,   41,   42,   43,   44,   45,
   93,   47,   66,  134,  190,  191,  164,   -1,   -1,   -1,
  196,   -1,   58,   59,   60,   -1,   62,   63,  276,  276,
   -1,   -1,   -1,  257,  258,  259,  260,  261,  262,   -1,
  264,  265,  266,  267,   -1,  269,  270,  271,  272,  273,
  274,  275,  276,  285,  286,   -1,  280,   93,  281,  282,
  276,  285,  286,  287,  288,  289,  290,  291,  257,  258,
  259,  260,  261,  262,   -1,  264,  265,  266,  267,  278,
  269,  270,  271,  272,  273,  274,  275,   -1,  276,   -1,
   -1,  280,   -1,   -1,  277,  278,  285,  286,  287,   -1,
  283,  284,  291,  257,  258,  259,  260,  261,  262,   -1,
  264,  265,  266,  267,   -1,  269,  270,  271,  272,  273,
  274,  275,   -1,   -1,   -1,   -1,  280,   -1,   -1,   -1,
   -1,  285,  286,  287,   -1,   -1,   -1,  291,  257,  258,
  259,  260,  261,  262,   -1,  264,  265,  266,  267,   -1,
  269,  270,  271,  272,  273,  274,  275,   -1,   -1,  277,
  278,  280,   -1,   -1,   -1,   -1,  285,  286,  287,   -1,
  261,  262,  291,  264,   -1,   -1,   -1,   -1,   -1,  262,
  271,  264,  273,  274,  275,   -1,   -1,   -1,  271,  280,
  273,  274,  275,   -1,  285,  286,  287,  280,   -1,  262,
   -1,  264,  285,  286,  287,   -1,   -1,  262,  271,  264,
  273,  274,  275,   -1,   -1,   -1,  271,  280,  273,  274,
  275,   -1,  285,  286,  287,  280,   -1,   -1,   -1,   37,
  285,  286,  287,   41,   42,   43,   44,   45,   46,   47,
   -1,  277,  278,   -1,   -1,  281,  282,  283,  284,   -1,
   58,   59,   60,   61,   62,   63,   37,   -1,   -1,   -1,
   41,   42,   43,   44,   45,   46,   47,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   58,   59,   60,
   -1,   62,   63,   91,   -1,   93,   -1,   -1,   -1,   -1,
   -1,   -1,   37,   -1,   -1,   -1,   41,   42,   43,   44,
   45,   46,   47,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   91,   -1,   93,   58,   59,   60,   37,   62,   63,   -1,
   41,   42,   43,   44,   45,   46,   47,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   58,   59,   60,
   -1,   62,   63,   -1,   -1,   -1,   91,   -1,   93,   -1,
   -1,   -1,   37,   -1,   -1,   -1,   41,   42,   43,   44,
   45,   37,   47,   -1,   -1,   41,   42,   43,   44,   45,
   91,   47,   93,   58,   59,   60,   -1,   62,   63,   -1,
   -1,   -1,   58,   59,   60,   -1,   62,   63,   37,   -1,
   -1,   -1,   41,   42,   43,   44,   45,   37,   47,   -1,
   -1,   41,   42,   43,   44,   45,   -1,   47,   93,   58,
   59,   60,   -1,   62,   63,   -1,   -1,   93,   58,   59,
   60,   -1,   62,   63,   -1,   37,   -1,   -1,   -1,   -1,
   42,   43,   45,   45,   46,   47,   -1,   41,   -1,   -1,
   44,   -1,   -1,   -1,   93,   -1,   58,   59,   60,   -1,
   62,   63,   -1,   93,   58,   59,   -1,   -1,   37,   63,
   -1,   -1,   41,   42,   43,   -1,   45,   46,   47,  277,
  278,   -1,   85,  281,  282,  283,  284,  285,  286,   91,
   59,   60,   -1,   62,   63,   -1,   -1,   -1,   -1,   93,
   -1,   -1,   -1,   -1,   -1,   -1,  277,  278,   -1,   37,
  281,  282,  283,  284,   42,   43,   -1,   45,   46,   47,
   -1,   -1,   91,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   58,   -1,   60,  136,   62,   63,   -1,   -1,   -1,   -1,
   -1,   -1,  277,  278,   -1,   -1,  281,  282,  283,  284,
   -1,   37,   -1,   -1,   -1,   41,   42,   43,   -1,   45,
   46,   47,  165,   91,  167,   -1,  277,  278,   -1,   -1,
  281,  282,  283,  284,   60,   37,   62,   63,   -1,   41,
   42,   43,   -1,   45,   46,   47,   -1,  190,  191,   -1,
   -1,   -1,   -1,  196,   -1,   -1,   -1,   -1,   60,   -1,
   62,   63,  277,  278,   -1,   91,  281,  282,  283,  284,
   -1,  277,  278,   -1,   -1,  281,  282,  283,  284,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   91,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  277,  278,
   -1,   -1,  281,  282,  283,  284,   -1,  277,  278,   -1,
   -1,  281,  282,  283,  284,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   37,   -1,   -1,   -1,   41,   42,   43,   -1,
   45,   46,   47,   -1,  276,  277,  278,   -1,   -1,  281,
  282,  283,  284,  277,  278,   60,   -1,   62,   63,  283,
  284,   -1,   -1,   -1,   37,   -1,   -1,   -1,   -1,   42,
   43,   44,   45,   46,   47,   -1,   -1,   -1,  277,  278,
   -1,   -1,  281,  282,  283,  284,   91,   60,   37,   62,
   63,   -1,   -1,   42,   43,   -1,   45,   46,   47,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   60,   -1,   62,   63,   -1,   -1,   -1,   91,  277,
  278,   -1,   37,  281,  282,  283,  284,   42,   43,   -1,
   45,   46,   47,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   91,   58,   93,   60,   37,   62,   63,   -1,
   -1,   42,   43,   -1,   45,   46,   47,   -1,   -1,   -1,
   -1,  277,  278,   -1,   -1,  281,  282,  283,  284,   60,
   -1,   62,   63,   37,   -1,   -1,   91,   -1,   42,   43,
   -1,   45,   46,   47,   -1,  277,  278,   -1,   -1,  281,
  282,  283,  284,   -1,   -1,   59,   60,   -1,   62,   63,
   91,   -1,   93,   37,   -1,   -1,   -1,   -1,   42,   43,
   -1,   45,   46,   47,   37,   -1,   -1,   -1,   -1,   42,
   43,   -1,   45,   46,   47,   -1,   60,   91,   62,   63,
   41,   -1,   43,   44,   45,   -1,   37,   60,   -1,   62,
   63,   42,   43,   -1,   45,   46,   47,   58,   59,   60,
   -1,   62,   63,   -1,   -1,   -1,   -1,   91,   -1,   60,
   -1,   62,   -1,   41,   -1,   -1,   44,   -1,   91,   -1,
   -1,   -1,  277,  278,   -1,   -1,  281,  282,  283,  284,
   58,   59,   93,   -1,   -1,   63,   -1,   -1,   -1,   37,
   91,   -1,   -1,   -1,   42,   43,   -1,   45,   46,   47,
   -1,   -1,   -1,   -1,  277,  278,   -1,   -1,  281,  282,
  283,  284,   60,   -1,   62,   93,   -1,   -1,   -1,   41,
   -1,   43,   44,   45,   -1,   -1,   -1,   41,  277,  278,
   44,   -1,  281,  282,  283,  284,   58,   59,   60,   -1,
   62,   63,   -1,   91,   58,   59,   -1,   -1,   -1,   63,
   -1,   -1,   -1,   -1,   -1,   41,   -1,   -1,   44,   -1,
   -1,   -1,  277,  278,   -1,   -1,  281,  282,  283,  284,
   -1,   93,   58,   59,   -1,   -1,   -1,   63,   41,   93,
   -1,   44,   -1,   -1,   -1,   -1,  277,  278,   -1,   -1,
  281,  282,  283,  284,   -1,   58,   59,   -1,   -1,   -1,
   63,   -1,   -1,   -1,   -1,   -1,   -1,   93,   -1,   -1,
   -1,   -1,   -1,  277,  278,   -1,   -1,  281,  282,  283,
  284,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   93,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,  277,  278,   -1,   -1,  281,  282,  283,
  284,   -1,   -1,   -1,  277,  278,   -1,   -1,  281,  282,
  283,  284,   -1,   -1,   -1,   -1,  277,  278,   -1,   -1,
  281,  282,  283,  284,   -1,   -1,  277,   -1,   -1,   -1,
  281,  282,  283,  284,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  277,
  278,   -1,   -1,   -1,   -1,  283,  284,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,  281,  282,  283,  284,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,  277,  278,   -1,   -1,  281,
  282,  283,  284,  277,  278,   50,   51,   -1,   -1,  283,
  284,   -1,   -1,   -1,   59,   60,   61,   -1,   -1,   -1,
   -1,   66,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,  277,  278,   -1,   -1,   -1,   -1,   -1,   -1,   84,
   -1,   86,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   95,   -1,   -1,   98,  277,  278,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,  111,  112,  113,  114,
  115,  116,  117,  118,  119,  120,  121,  122,  123,   -1,
  125,  126,  127,   -1,   -1,   -1,   -1,   -1,   -1,  134,
   -1,   -1,   -1,  138,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  164,
   -1,  166,   -1,   -1,   -1,   -1,   -1,  172,   -1,   -1,
  175,   -1,   -1,  178,
};
}
final static short YYFINAL=2;
final static short YYMAXTOKEN=293;
final static String yyname[] = {
"end-of-file",null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,"'!'",null,null,null,"'%'",null,null,"'('","')'","'*'","'+'",
"','","'-'","'.'","'/'",null,null,null,null,null,null,null,null,null,null,"':'",
"';'","'<'","'='","'>'","'?'",null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,"'['",null,"']'",null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,"'{'",null,"'}'",null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,"VOID","BOOL","INT","STRING",
"CLASS","NULL","EXTENDS","THIS","WHILE","FOR","IF","ELSE","RETURN","BREAK",
"NEW","PRINT","READ_INTEGER","READ_LINE","LITERAL","IDENTIFIER","AND","OR",
"STATIC","INSTANCEOF","LESS_EQUAL","GREATER_EQUAL","EQUAL","NOT_EQUAL","INC",
"DEC","NUMINSTANCES","GUARDSEP","FI","OD","DO","UMINUS","EMPTY",
};
final static String yyrule[] = {
"$accept : Program",
"Program : ClassList",
"ClassList : ClassList ClassDef",
"ClassList : ClassDef",
"VariableDef : Variable ';'",
"Variable : Type IDENTIFIER",
"Type : INT",
"Type : VOID",
"Type : BOOL",
"Type : STRING",
"Type : CLASS IDENTIFIER",
"Type : Type '[' ']'",
"ClassDef : CLASS IDENTIFIER ExtendsClause '{' FieldList '}'",
"ExtendsClause : EXTENDS IDENTIFIER",
"ExtendsClause :",
"FieldList : FieldList VariableDef",
"FieldList : FieldList FunctionDef",
"FieldList :",
"Formals : VariableList",
"Formals :",
"VariableList : VariableList ',' Variable",
"VariableList : Variable",
"FunctionDef : STATIC Type IDENTIFIER '(' Formals ')' StmtBlock",
"FunctionDef : Type IDENTIFIER '(' Formals ')' StmtBlock",
"StmtBlock : '{' StmtList '}'",
"StmtList : StmtList Stmt",
"StmtList :",
"Stmt : VariableDef",
"Stmt : SimpleStmt ';'",
"Stmt : IfStmt",
"Stmt : WhileStmt",
"Stmt : ForStmt",
"Stmt : ReturnStmt ';'",
"Stmt : PrintStmt ';'",
"Stmt : BreakStmt ';'",
"Stmt : StmtBlock",
"Stmt : GuardedIfStmt",
"Stmt : GuardedDoStmt",
"GuardedIfStmt : IF GuardedStmtList FI",
"GuardedDoStmt : DO GuardedStmtList OD",
"GuardedStmtList : GuardedStmtList GUARDSEP GuardedStmt",
"GuardedStmtList : GuardedStmt",
"GuardedStmt : Expr ':' Stmt",
"SimpleStmt : LValue '=' Expr",
"SimpleStmt : Call",
"SimpleStmt :",
"Receiver : Expr '.'",
"Receiver :",
"LValue : Receiver IDENTIFIER",
"LValue : Expr '[' Expr ']'",
"Call : Receiver IDENTIFIER '(' Actuals ')'",
"Expr : LValue INC",
"Expr : INC LValue",
"Expr : DEC LValue",
"Expr : LValue DEC",
"Expr : LValue",
"Expr : Call",
"Expr : Constant",
"Expr : NUMINSTANCES '(' IDENTIFIER ')'",
"Expr : Expr '?' Expr ':' Expr",
"Expr : Expr '+' Expr",
"Expr : Expr '-' Expr",
"Expr : Expr '*' Expr",
"Expr : Expr '/' Expr",
"Expr : Expr '%' Expr",
"Expr : Expr EQUAL Expr",
"Expr : Expr NOT_EQUAL Expr",
"Expr : Expr '<' Expr",
"Expr : Expr '>' Expr",
"Expr : Expr LESS_EQUAL Expr",
"Expr : Expr GREATER_EQUAL Expr",
"Expr : Expr AND Expr",
"Expr : Expr OR Expr",
"Expr : '(' Expr ')'",
"Expr : '-' Expr",
"Expr : '!' Expr",
"Expr : READ_INTEGER '(' ')'",
"Expr : READ_LINE '(' ')'",
"Expr : THIS",
"Expr : NEW IDENTIFIER '(' ')'",
"Expr : NEW Type '[' Expr ']'",
"Expr : INSTANCEOF '(' Expr ',' IDENTIFIER ')'",
"Expr : '(' CLASS IDENTIFIER ')' Expr",
"Constant : LITERAL",
"Constant : NULL",
"Actuals : ExprList",
"Actuals :",
"ExprList : ExprList ',' Expr",
"ExprList : Expr",
"WhileStmt : WHILE '(' Expr ')' Stmt",
"ForStmt : FOR '(' SimpleStmt ';' Expr ';' SimpleStmt ')' Stmt",
"BreakStmt : BREAK",
"IfStmt : IF '(' Expr ')' Stmt ElseClause",
"ElseClause : ELSE Stmt",
"ElseClause :",
"ReturnStmt : RETURN Expr",
"ReturnStmt : RETURN",
"PrintStmt : PRINT '(' ExprList ')'",
};

//#line 482 "Parser.y"
    
	/**
	 * 打印当前归约所用的语法规则<br>
	 * 请勿修改。
	 */
    public boolean onReduce(String rule) {
		if (rule.startsWith("$$"))
			return false;
		else
			rule = rule.replaceAll(" \\$\\$\\d+", "");

   	    if (rule.endsWith(":"))
    	    System.out.println(rule + " <empty>");
   	    else
			System.out.println(rule);
		return false;
    }
    
    public void diagnose() {
		addReduceListener(this);
		yyparse();
	}
//#line 708 "Parser.java"
//###############################################################
// method: yylexdebug : check lexer state
//###############################################################
void yylexdebug(int state,int ch)
{
String s=null;
  if (ch < 0) ch=0;
  if (ch <= YYMAXTOKEN) //check index bounds
     s = yyname[ch];    //now get it
  if (s==null)
    s = "illegal-symbol";
  debug("state "+state+", reading "+ch+" ("+s+")");
}





//The following are now global, to aid in error reporting
int yyn;       //next next thing to do
int yym;       //
int yystate;   //current parsing state from state table
String yys;    //current token string


//###############################################################
// method: yyparse : parse input and execute indicated items
//###############################################################
int yyparse()
{
boolean doaction;
  init_stacks();
  yynerrs = 0;
  yyerrflag = 0;
  yychar = -1;          //impossible char forces a read
  yystate=0;            //initial state
  state_push(yystate);  //save it
  while (true) //until parsing is done, either correctly, or w/error
    {
    doaction=true;
    //if (yydebug) debug("loop"); 
    //#### NEXT ACTION (from reduction table)
    for (yyn=yydefred[yystate];yyn==0;yyn=yydefred[yystate])
      {
      //if (yydebug) debug("yyn:"+yyn+"  state:"+yystate+"  yychar:"+yychar);
      if (yychar < 0)      //we want a char?
        {
        yychar = yylex();  //get next token
        //if (yydebug) debug(" next yychar:"+yychar);
        //#### ERROR CHECK ####
        //if (yychar < 0)    //it it didn't work/error
        //  {
        //  yychar = 0;      //change it to default string (no -1!)
          //if (yydebug)
          //  yylexdebug(yystate,yychar);
        //  }
        }//yychar<0
      yyn = yysindex[yystate];  //get amount to shift by (shift index)
      if ((yyn != 0) && (yyn += yychar) >= 0 &&
          yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
        {
        //if (yydebug)
          //debug("state "+yystate+", shifting to state "+yytable[yyn]);
        //#### NEXT STATE ####
        yystate = yytable[yyn];//we are in a new state
        state_push(yystate);   //save it
        val_push(yylval);      //push our lval as the input for next rule
        yychar = -1;           //since we have 'eaten' a token, say we need another
        if (yyerrflag > 0)     //have we recovered an error?
           --yyerrflag;        //give ourselves credit
        doaction=false;        //but don't process yet
        break;   //quit the yyn=0 loop
        }

    yyn = yyrindex[yystate];  //reduce
    if ((yyn !=0 ) && (yyn += yychar) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
      {   //we reduced!
      //if (yydebug) debug("reduce");
      yyn = yytable[yyn];
      doaction=true; //get ready to execute
      break;         //drop down to actions
      }
    else //ERROR RECOVERY
      {
      if (yyerrflag==0)
        {
        yyerror("syntax error");
        yynerrs++;
        }
      if (yyerrflag < 3) //low error count?
        {
        yyerrflag = 3;
        while (true)   //do until break
          {
          if (stateptr<0 || valptr<0)   //check for under & overflow here
            {
            return 1;
            }
          yyn = yysindex[state_peek(0)];
          if ((yyn != 0) && (yyn += YYERRCODE) >= 0 &&
                    yyn <= YYTABLESIZE && yycheck[yyn] == YYERRCODE)
            {
            //if (yydebug)
              //debug("state "+state_peek(0)+", error recovery shifting to state "+yytable[yyn]+" ");
            yystate = yytable[yyn];
            state_push(yystate);
            val_push(yylval);
            doaction=false;
            break;
            }
          else
            {
            //if (yydebug)
              //debug("error recovery discarding state "+state_peek(0)+" ");
            if (stateptr<0 || valptr<0)   //check for under & overflow here
              {
              return 1;
              }
            state_pop();
            val_pop();
            }
          }
        }
      else            //discard this token
        {
        if (yychar == 0)
          return 1; //yyabort
        //if (yydebug)
          //{
          //yys = null;
          //if (yychar <= YYMAXTOKEN) yys = yyname[yychar];
          //if (yys == null) yys = "illegal-symbol";
          //debug("state "+yystate+", error recovery discards token "+yychar+" ("+yys+")");
          //}
        yychar = -1;  //read another
        }
      }//end error recovery
    }//yyn=0 loop
    if (!doaction)   //any reason not to proceed?
      continue;      //skip action
    yym = yylen[yyn];          //get count of terminals on rhs
    //if (yydebug)
      //debug("state "+yystate+", reducing "+yym+" by rule "+yyn+" ("+yyrule[yyn]+")");
    if (yym>0)                 //if count of rhs not 'nil'
      yyval = val_peek(yym-1); //get current semantic value
    if (reduceListener == null || reduceListener.onReduce(yyrule[yyn])) // if intercepted!
      switch(yyn)
      {
//########## USER-SUPPLIED ACTIONS ##########
case 1:
//#line 56 "Parser.y"
{
						tree = new Tree.TopLevel(val_peek(0).clist, val_peek(0).loc);
					}
break;
case 2:
//#line 62 "Parser.y"
{
						yyval.clist.add(val_peek(0).cdef);
					}
break;
case 3:
//#line 66 "Parser.y"
{
                		yyval.clist = new ArrayList<Tree.ClassDef>();
                		yyval.clist.add(val_peek(0).cdef);
                	}
break;
case 5:
//#line 76 "Parser.y"
{
						yyval.vdef = new Tree.VarDef(val_peek(0).ident, val_peek(1).type, val_peek(0).loc);
					}
break;
case 6:
//#line 82 "Parser.y"
{
						yyval.type = new Tree.TypeIdent(Tree.INT, val_peek(0).loc);
					}
break;
case 7:
//#line 86 "Parser.y"
{
                		yyval.type = new Tree.TypeIdent(Tree.VOID, val_peek(0).loc);
                	}
break;
case 8:
//#line 90 "Parser.y"
{
                		yyval.type = new Tree.TypeIdent(Tree.BOOL, val_peek(0).loc);
                	}
break;
case 9:
//#line 94 "Parser.y"
{
                		yyval.type = new Tree.TypeIdent(Tree.STRING, val_peek(0).loc);
                	}
break;
case 10:
//#line 98 "Parser.y"
{
                		yyval.type = new Tree.TypeClass(val_peek(0).ident, val_peek(1).loc);
                	}
break;
case 11:
//#line 102 "Parser.y"
{
                		yyval.type = new Tree.TypeArray(val_peek(2).type, val_peek(2).loc);
                	}
break;
case 12:
//#line 108 "Parser.y"
{
						yyval.cdef = new Tree.ClassDef(val_peek(4).ident, val_peek(3).ident, val_peek(1).flist, val_peek(5).loc);
					}
break;
case 13:
//#line 114 "Parser.y"
{
						yyval.ident = val_peek(0).ident;
					}
break;
case 14:
//#line 118 "Parser.y"
{
                		yyval = new SemValue();
                	}
break;
case 15:
//#line 124 "Parser.y"
{
						yyval.flist.add(val_peek(0).vdef);
					}
break;
case 16:
//#line 128 "Parser.y"
{
						yyval.flist.add(val_peek(0).fdef);
					}
break;
case 17:
//#line 132 "Parser.y"
{
                		yyval = new SemValue();
                		yyval.flist = new ArrayList<Tree>();
                	}
break;
case 19:
//#line 140 "Parser.y"
{
                		yyval = new SemValue();
                		yyval.vlist = new ArrayList<Tree.VarDef>(); 
                	}
break;
case 20:
//#line 147 "Parser.y"
{
						yyval.vlist.add(val_peek(0).vdef);
					}
break;
case 21:
//#line 151 "Parser.y"
{
                		yyval.vlist = new ArrayList<Tree.VarDef>();
						yyval.vlist.add(val_peek(0).vdef);
                	}
break;
case 22:
//#line 158 "Parser.y"
{
						yyval.fdef = new MethodDef(true, val_peek(4).ident, val_peek(5).type, val_peek(2).vlist, (Block) val_peek(0).stmt, val_peek(4).loc);
					}
break;
case 23:
//#line 162 "Parser.y"
{
						yyval.fdef = new MethodDef(false, val_peek(4).ident, val_peek(5).type, val_peek(2).vlist, (Block) val_peek(0).stmt, val_peek(4).loc);
					}
break;
case 24:
//#line 168 "Parser.y"
{
						yyval.stmt = new Block(val_peek(1).slist, val_peek(2).loc);
					}
break;
case 25:
//#line 174 "Parser.y"
{
						yyval.slist.add(val_peek(0).stmt);
					}
break;
case 26:
//#line 178 "Parser.y"
{
                		yyval = new SemValue();
                		yyval.slist = new ArrayList<Tree>();
                	}
break;
case 27:
//#line 185 "Parser.y"
{
						yyval.stmt = val_peek(0).vdef;
					}
break;
case 28:
//#line 190 "Parser.y"
{
                		if (yyval.stmt == null) {
                			yyval.stmt = new Tree.Skip(val_peek(0).loc);
                		}
                	}
break;
case 38:
//#line 207 "Parser.y"
{
                        yyval.stmt = new Tree.GuardIf(val_peek(1).glist, val_peek(2).loc); 
                    }
break;
case 39:
//#line 213 "Parser.y"
{
                        yyval.stmt = new Tree.GuardDo(val_peek(1).glist, val_peek(2).loc); 
                    }
break;
case 40:
//#line 219 "Parser.y"
{
                        yyval.glist.add(val_peek(0).guard);
                    }
break;
case 41:
//#line 223 "Parser.y"
{
                        yyval.glist = new ArrayList<Tree.Guard>();
                        yyval.glist.add(val_peek(0).guard);
                    }
break;
case 42:
//#line 230 "Parser.y"
{
                        yyval.guard = new Tree.Guard(val_peek(2).expr, val_peek(0).stmt, val_peek(1).loc);
                    }
break;
case 43:
//#line 236 "Parser.y"
{
						yyval.stmt = new Tree.Assign(val_peek(2).lvalue, val_peek(0).expr, val_peek(1).loc);
					}
break;
case 44:
//#line 240 "Parser.y"
{
                		yyval.stmt = new Tree.Exec(val_peek(0).expr, val_peek(0).loc);
                	}
break;
case 45:
//#line 244 "Parser.y"
{
                		yyval = new SemValue();
                	}
break;
case 47:
//#line 251 "Parser.y"
{
                		yyval = new SemValue();
                	}
break;
case 48:
//#line 257 "Parser.y"
{
						yyval.lvalue = new Tree.Ident(val_peek(1).expr, val_peek(0).ident, val_peek(0).loc);
						if (val_peek(1).loc == null) {
							yyval.loc = val_peek(0).loc;
						}
					}
break;
case 49:
//#line 264 "Parser.y"
{
                		yyval.lvalue = new Tree.Indexed(val_peek(3).expr, val_peek(1).expr, val_peek(3).loc);
                	}
break;
case 50:
//#line 270 "Parser.y"
{
						yyval.expr = new Tree.CallExpr(val_peek(4).expr, val_peek(3).ident, val_peek(1).elist, val_peek(3).loc);
						if (val_peek(4).loc == null){ 
							yyval.loc = val_peek(3).loc;
						}
					}
break;
case 51:
//#line 279 "Parser.y"
{
                        yyval.expr = new Tree.SelfOp(Tree.POSTINC, val_peek(1).lvalue, val_peek(1).loc);   
                    }
break;
case 52:
//#line 283 "Parser.y"
{
                        yyval.expr = new Tree.SelfOp(Tree.PREINC, val_peek(0).lvalue, val_peek(0).loc);    
                    }
break;
case 53:
//#line 287 "Parser.y"
{
                        yyval.expr = new Tree.SelfOp(Tree.PREDEC, val_peek(0).lvalue, val_peek(0).loc);       
                    }
break;
case 54:
//#line 291 "Parser.y"
{
                        yyval.expr = new Tree.SelfOp(Tree.POSTDEC, val_peek(1).lvalue, val_peek(1).loc);   
                    }
break;
case 55:
//#line 295 "Parser.y"
{
						yyval.expr = val_peek(0).lvalue;
					}
break;
case 58:
//#line 301 "Parser.y"
{
                        yyval.expr = new Tree.TypeNum(Tree.NUMINSTANCES, val_peek(1).ident, val_peek(3).loc);
                    }
break;
case 59:
//#line 305 "Parser.y"
{
                        yyval.expr = new Tree.Ternary(Tree.COND, val_peek(4).expr, val_peek(2).expr, val_peek(0).expr, val_peek(4).loc);
                    }
break;
case 60:
//#line 309 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.PLUS, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 61:
//#line 313 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.MINUS, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 62:
//#line 317 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.MUL, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 63:
//#line 321 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.DIV, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 64:
//#line 325 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.MOD, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 65:
//#line 329 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.EQ, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 66:
//#line 333 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.NE, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 67:
//#line 337 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.LT, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 68:
//#line 341 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.GT, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 69:
//#line 345 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.LE, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 70:
//#line 349 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.GE, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 71:
//#line 353 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.AND, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 72:
//#line 357 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.OR, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 73:
//#line 361 "Parser.y"
{
                		yyval = val_peek(1);
                	}
break;
case 74:
//#line 365 "Parser.y"
{
                		yyval.expr = new Tree.Unary(Tree.NEG, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 75:
//#line 369 "Parser.y"
{
                		yyval.expr = new Tree.Unary(Tree.NOT, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 76:
//#line 373 "Parser.y"
{
                		yyval.expr = new Tree.ReadIntExpr(val_peek(2).loc);
                	}
break;
case 77:
//#line 377 "Parser.y"
{
                		yyval.expr = new Tree.ReadLineExpr(val_peek(2).loc);
                	}
break;
case 78:
//#line 381 "Parser.y"
{
                		yyval.expr = new Tree.ThisExpr(val_peek(0).loc);
                	}
break;
case 79:
//#line 385 "Parser.y"
{
                		yyval.expr = new Tree.NewClass(val_peek(2).ident, val_peek(3).loc);
                	}
break;
case 80:
//#line 389 "Parser.y"
{
                		yyval.expr = new Tree.NewArray(val_peek(3).type, val_peek(1).expr, val_peek(4).loc);
                	}
break;
case 81:
//#line 393 "Parser.y"
{
                		yyval.expr = new Tree.TypeTest(val_peek(3).expr, val_peek(1).ident, val_peek(5).loc);
                	}
break;
case 82:
//#line 397 "Parser.y"
{
                		yyval.expr = new Tree.TypeCast(val_peek(2).ident, val_peek(0).expr, val_peek(0).loc);
                	}
break;
case 83:
//#line 403 "Parser.y"
{
						yyval.expr = new Tree.Literal(val_peek(0).typeTag, val_peek(0).literal, val_peek(0).loc);
					}
break;
case 84:
//#line 407 "Parser.y"
{
						yyval.expr = new Null(val_peek(0).loc);
					}
break;
case 86:
//#line 414 "Parser.y"
{
                		yyval = new SemValue();
                		yyval.elist = new ArrayList<Tree.Expr>();
                	}
break;
case 87:
//#line 421 "Parser.y"
{
						yyval.elist.add(val_peek(0).expr);
					}
break;
case 88:
//#line 425 "Parser.y"
{
                		yyval.elist = new ArrayList<Tree.Expr>();
						yyval.elist.add(val_peek(0).expr);
                	}
break;
case 89:
//#line 432 "Parser.y"
{
						yyval.stmt = new Tree.WhileLoop(val_peek(2).expr, val_peek(0).stmt, val_peek(4).loc);
					}
break;
case 90:
//#line 438 "Parser.y"
{
						yyval.stmt = new Tree.ForLoop(val_peek(6).stmt, val_peek(4).expr, val_peek(2).stmt, val_peek(0).stmt, val_peek(8).loc);
					}
break;
case 91:
//#line 444 "Parser.y"
{
						yyval.stmt = new Tree.Break(val_peek(0).loc);
					}
break;
case 92:
//#line 450 "Parser.y"
{
						yyval.stmt = new Tree.If(val_peek(3).expr, val_peek(1).stmt, val_peek(0).stmt, val_peek(5).loc);
					}
break;
case 93:
//#line 456 "Parser.y"
{
						yyval.stmt = val_peek(0).stmt;
					}
break;
case 94:
//#line 460 "Parser.y"
{
						yyval = new SemValue();
					}
break;
case 95:
//#line 466 "Parser.y"
{
						yyval.stmt = new Tree.Return(val_peek(0).expr, val_peek(1).loc);
					}
break;
case 96:
//#line 470 "Parser.y"
{
                		yyval.stmt = new Tree.Return(null, val_peek(0).loc);
                	}
break;
case 97:
//#line 476 "Parser.y"
{
						yyval.stmt = new Print(val_peek(1).elist, val_peek(3).loc);
					}
break;
//#line 1362 "Parser.java"
//########## END OF USER-SUPPLIED ACTIONS ##########
    }//switch
    //#### Now let's reduce... ####
    //if (yydebug) debug("reduce");
    state_drop(yym);             //we just reduced yylen states
    yystate = state_peek(0);     //get new state
    val_drop(yym);               //corresponding value drop
    yym = yylhs[yyn];            //select next TERMINAL(on lhs)
    if (yystate == 0 && yym == 0)//done? 'rest' state and at first TERMINAL
      {
      //if (yydebug) debug("After reduction, shifting from state 0 to state "+YYFINAL+"");
      yystate = YYFINAL;         //explicitly say we're done
      state_push(YYFINAL);       //and save it
      val_push(yyval);           //also save the semantic value of parsing
      if (yychar < 0)            //we want another character?
        {
        yychar = yylex();        //get next character
        //if (yychar<0) yychar=0;  //clean, if necessary
        //if (yydebug)
          //yylexdebug(yystate,yychar);
        }
      if (yychar == 0)          //Good exit (if lex returns 0 ;-)
         break;                 //quit the loop--all DONE
      }//if yystate
    else                        //else not done yet
      {                         //get next state and push, for next yydefred[]
      yyn = yygindex[yym];      //find out where to go
      if ((yyn != 0) && (yyn += yystate) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yystate)
        yystate = yytable[yyn]; //get new state
      else
        yystate = yydgoto[yym]; //else go to new defred
      //if (yydebug) debug("after reduction, shifting from state "+state_peek(0)+" to state "+yystate+"");
      state_push(yystate);     //going again, so push state & val...
      val_push(yyval);         //for next action
      }
    }//main loop
  return 0;//yyaccept!!
}
//## end of method parse() ######################################



//## run() --- for Thread #######################################
//## The -Jnorun option was used ##
//## end of method run() ########################################



//## Constructors ###############################################
//## The -Jnoconstruct option was used ##
//###############################################################



}
//################### END OF CLASS ##############################
