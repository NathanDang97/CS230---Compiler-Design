/* Generated By:JavaCC: Do not edit this line. Lexer.java */
import java.io.*;
import java.util.*;

public class Lexer implements LexerConstants {

   public static void main(String[] args) throws ParseException, FileNotFoundException {
      Lexer parser = new Lexer(new FileInputStream(args[0]));
      parser.Input();
   }

   public static void printToken(Token t, String type) {
      if (type.equals("<KEYWORD>"))
        System.out.println("\u005ct" + type + "\u005ct\u005ct" + t.image);
      else if (type.equals("<BEGIN>"))
        System.out.println("\u005ct" + type + "\u005ct\u005ct\u005ct" + t.image);
      else if (type.equals("<END>"))
        System.out.println("\u005ct" + type + "\u005ct\u005ct\u005ct" + t.image);
      else if (type.equals("<ADDOP>"))
        System.out.println("\u005ct" + type + "\u005ct\u005ct\u005ct" + t.image);
      else if (type.equals("<MULTOP>"))
        System.out.println("\u005ct" + type + "\u005ct\u005ct" + t.image);
      else if (type.equals("<ASSIGNOP>"))
        System.out.println("\u005ct" + type + "\u005ct\u005ct" + t.image);
      else if (type.equals("<SEMICOLON>"))
        System.out.println("\u005ct" + type + "\u005ct\u005ct" + t.image);
      else if (type.equals("<OPENPAREN>"))
        System.out.println("\u005ct" + type + "\u005ct\u005ct" + t.image);
      else if (type.equals("<CLOSEPAREN>"))
        System.out.println("\u005ct" + type + "\u005ct\u005ct" + t.image);
      else if (type.equals("<IDENTIFIER>"))
        System.out.println("\u005ct" + type + "\u005ct\u005ct" + t.image);
   }

/* END: Specification of tokens */

/* BEGIN: Specification of Language */
  static final public void Input() throws ParseException {
     System.out.println("\u005ct" + "Type\u005ct\u005ct\u005ct" + "Lexeme");
    Program();
    jj_consume_token(0);
  }

// grammar: <program> --> (<token expression>)*
  static final public void Program() throws ParseException {
  Token t;
    label_1:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case KEYWORD:
      case ADDOP:
      case MULTOP:
      case ASSIGNOP:
      case SEMICOLON:
      case OPENPAREN:
      case CLOSEPAREN:
      case BEGIN:
      case END:
      case IDENTIFIER:
        ;
        break;
      default:
        jj_la1[0] = jj_gen;
        break label_1;
      }
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case BEGIN:
        t = jj_consume_token(BEGIN);
                        printToken(t, "<BEGIN>");
        break;
      case END:
        t = jj_consume_token(END);
                        printToken(t, "<END>");
        break;
      case KEYWORD:
        t = jj_consume_token(KEYWORD);
                        printToken(t, "<KEYWORD>");
        break;
      case MULTOP:
        t = jj_consume_token(MULTOP);
                        printToken(t, "<MULTOP>");
        break;
      case ADDOP:
        t = jj_consume_token(ADDOP);
                        printToken(t, "<ADDOP>");
        break;
      case ASSIGNOP:
        t = jj_consume_token(ASSIGNOP);
                        printToken(t, "<ASSIGNOP>");
        break;
      case SEMICOLON:
        t = jj_consume_token(SEMICOLON);
                        printToken(t, "<SEMICOLON>");
        break;
      case OPENPAREN:
        t = jj_consume_token(OPENPAREN);
                        printToken(t, "<OPENPAREN>");
        break;
      case CLOSEPAREN:
        t = jj_consume_token(CLOSEPAREN);
                        printToken(t, "<CLOSEPAREN>");
        break;
      case IDENTIFIER:
        t = jj_consume_token(IDENTIFIER);
                        printToken(t, "<IDENTIFIER>");
        break;
      default:
        jj_la1[1] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    }
  }

  static private boolean jj_initialized_once = false;
  /** Generated Token Manager. */
  static public LexerTokenManager token_source;
  static SimpleCharStream jj_input_stream;
  /** Current token. */
  static public Token token;
  /** Next token. */
  static public Token jj_nt;
  static private int jj_ntk;
  static private int jj_gen;
  static final private int[] jj_la1 = new int[2];
  static private int[] jj_la1_0;
  static {
      jj_la1_init_0();
   }
   private static void jj_la1_init_0() {
      jj_la1_0 = new int[] {0x7fe0,0x7fe0,};
   }

  /** Constructor with InputStream. */
  public Lexer(java.io.InputStream stream) {
     this(stream, null);
  }
  /** Constructor with InputStream and supplied encoding */
  public Lexer(java.io.InputStream stream, String encoding) {
    if (jj_initialized_once) {
      System.out.println("ERROR: Second call to constructor of static parser.  ");
      System.out.println("       You must either use ReInit() or set the JavaCC option STATIC to false");
      System.out.println("       during parser generation.");
      throw new Error();
    }
    jj_initialized_once = true;
    try { jj_input_stream = new SimpleCharStream(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
    token_source = new LexerTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 2; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  static public void ReInit(java.io.InputStream stream) {
     ReInit(stream, null);
  }
  /** Reinitialise. */
  static public void ReInit(java.io.InputStream stream, String encoding) {
    try { jj_input_stream.ReInit(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 2; i++) jj_la1[i] = -1;
  }

  /** Constructor. */
  public Lexer(java.io.Reader stream) {
    if (jj_initialized_once) {
      System.out.println("ERROR: Second call to constructor of static parser. ");
      System.out.println("       You must either use ReInit() or set the JavaCC option STATIC to false");
      System.out.println("       during parser generation.");
      throw new Error();
    }
    jj_initialized_once = true;
    jj_input_stream = new SimpleCharStream(stream, 1, 1);
    token_source = new LexerTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 2; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  static public void ReInit(java.io.Reader stream) {
    jj_input_stream.ReInit(stream, 1, 1);
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 2; i++) jj_la1[i] = -1;
  }

  /** Constructor with generated Token Manager. */
  public Lexer(LexerTokenManager tm) {
    if (jj_initialized_once) {
      System.out.println("ERROR: Second call to constructor of static parser. ");
      System.out.println("       You must either use ReInit() or set the JavaCC option STATIC to false");
      System.out.println("       during parser generation.");
      throw new Error();
    }
    jj_initialized_once = true;
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 2; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  public void ReInit(LexerTokenManager tm) {
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 2; i++) jj_la1[i] = -1;
  }

  static private Token jj_consume_token(int kind) throws ParseException {
    Token oldToken;
    if ((oldToken = token).next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    if (token.kind == kind) {
      jj_gen++;
      return token;
    }
    token = oldToken;
    jj_kind = kind;
    throw generateParseException();
  }


/** Get the next Token. */
  static final public Token getNextToken() {
    if (token.next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    jj_gen++;
    return token;
  }

/** Get the specific Token. */
  static final public Token getToken(int index) {
    Token t = token;
    for (int i = 0; i < index; i++) {
      if (t.next != null) t = t.next;
      else t = t.next = token_source.getNextToken();
    }
    return t;
  }

  static private int jj_ntk() {
    if ((jj_nt=token.next) == null)
      return (jj_ntk = (token.next=token_source.getNextToken()).kind);
    else
      return (jj_ntk = jj_nt.kind);
  }

  static private java.util.List<int[]> jj_expentries = new java.util.ArrayList<int[]>();
  static private int[] jj_expentry;
  static private int jj_kind = -1;

  /** Generate ParseException. */
  static public ParseException generateParseException() {
    jj_expentries.clear();
    boolean[] la1tokens = new boolean[17];
    if (jj_kind >= 0) {
      la1tokens[jj_kind] = true;
      jj_kind = -1;
    }
    for (int i = 0; i < 2; i++) {
      if (jj_la1[i] == jj_gen) {
        for (int j = 0; j < 32; j++) {
          if ((jj_la1_0[i] & (1<<j)) != 0) {
            la1tokens[j] = true;
          }
        }
      }
    }
    for (int i = 0; i < 17; i++) {
      if (la1tokens[i]) {
        jj_expentry = new int[1];
        jj_expentry[0] = i;
        jj_expentries.add(jj_expentry);
      }
    }
    int[][] exptokseq = new int[jj_expentries.size()][];
    for (int i = 0; i < jj_expentries.size(); i++) {
      exptokseq[i] = jj_expentries.get(i);
    }
    return new ParseException(token, exptokseq, tokenImage);
  }

  /** Enable tracing. */
  static final public void enable_tracing() {
  }

  /** Disable tracing. */
  static final public void disable_tracing() {
  }

}