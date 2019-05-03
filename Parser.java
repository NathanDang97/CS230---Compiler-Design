/* Generated By:JavaCC: Do not edit this line. Parser.java */
import java.io.*;
import java.util.*;

public class Parser implements ParserConstants {
   public static void main(String[] args) throws ParseException, FileNotFoundException {
      Parser parser = new Parser(new FileInputStream(args[0]));
      parser.Input();
   }

   // indentation
   public static void indent(int n) {
      for (int i = 0; i < n; ++i) {
         System.out.print(" ");
         System.out.print(" ");
         System.out.print(" ");
      }
   }

    // print the tree
    public static void toString(AST t, int level) {
        // if its left/right leaf is null, print "-"
        if (t == null) {
            indent(level);
            System.out.println("-");
            return;
        }
        indent(level);
        System.out.println(t.value);
        // if it's a leaf, return
        if (t.left == null && t.right == null) return;
        toString(t.left, level + 1);
        toString(t.right, level + 1);
    }

// return the AST after parsing the input file
  static final public AST Input() throws ParseException {
    AST tree = null;
      System.out.println("Parsing the input file...");
      System.out.println("-------------------------");
    tree = program();
    jj_consume_token(0);
      toString(tree, 1);
      {if (true) return tree;}
    throw new Error("Missing return statement in function");
  }

/* 
BEGIN: Specification of language
  - Recursive decent is applied.  
  - The AST will be built during recursive decent
  - Each operator should have 2 children except for "program" and "!" which can have one child
  - The empty child will be printed as "-"
*/

// <program> → void main () <block>
  static final public AST program() throws ParseException {
    AST tree;
    jj_consume_token(KEYWORD);
    jj_consume_token(KEYWORD);
    jj_consume_token(OPENPAREN);
    jj_consume_token(CLOSEPAREN);
    tree = block();
      {if (true) return tree;}
    throw new Error("Missing return statement in function");
  }

// <block> → { <declarations> <optional_statements>? }
  static final public AST block() throws ParseException {
    AST tree = null, op, l = null, r;
    jj_consume_token(BEGIN);
    l = declarations();
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case KEYWORD:
    case BEGIN:
    case IDENTIFIER:
      r = optional_statements();
        op = new Node("block", l, r);
        tree = op;
      break;
    default:
      jj_la1[0] = jj_gen;
      ;
    }
    jj_consume_token(END);
      {if (true) return tree;}
    throw new Error("Missing return statement in function");
  }

// <declarations>  →  (<declaration>)*
  static final public AST declarations() throws ParseException {
    AST tleft = null, tright = null;
    label_1:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case TYPE:
        ;
        break;
      default:
        jj_la1[1] = jj_gen;
        break label_1;
      }
      tright = declaration();
        if (tleft != null)
            tleft = new Node(";", tleft, tright);
        else
            tleft = tright;
    }
      {if (true) return tleft;}
    throw new Error("Missing return statement in function");
  }

// <declaration>  →  <type> <identifier_list>; 
  static final public AST declaration() throws ParseException {
    Token t;
    AST tree, l, op;
    t = jj_consume_token(TYPE);
                 l = new Leaf(t.image);
    tree = identifier_list();
        op = new Node(":", l, tree);
        tree = op;
    jj_consume_token(SEMICOLON);
      {if (true) return tree;}
    throw new Error("Missing return statement in function");
  }

// <identifier_list>  →  <id>  ( , <id> )*
  static final public AST identifier_list() throws ParseException {
    Token t;
    String comma;
    AST tree, op, l, r;
    t = jj_consume_token(IDENTIFIER);
                       tree = new Leaf(t.image);
    label_2:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case COMMA:
        ;
        break;
      default:
        jj_la1[2] = jj_gen;
        break label_2;
      }
      t = jj_consume_token(COMMA);
                   comma = t.image;
      t = jj_consume_token(IDENTIFIER);
        r = new Leaf(t.image);
        op = new Node(comma, tree, r);
        tree = op;
    }
      {if (true) return tree;}
    throw new Error("Missing return statement in function");
  }

// <optional_statements>  → <statement_list>
  static final public AST optional_statements() throws ParseException {
    AST tree;
    tree = statement_list();
      {if (true) return tree;}
    throw new Error("Missing return statement in function");
  }

// <statement_list>  → <statement> ( <statement> )* 
  static final public AST statement_list() throws ParseException {
    AST tree, op, r;
    tree = statement();
    label_3:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case KEYWORD:
      case BEGIN:
      case IDENTIFIER:
        ;
        break;
      default:
        jj_la1[3] = jj_gen;
        break label_3;
      }
      r = statement();
        op = new Node(";", tree, r);
        tree = op;
    }
      {if (true) return tree;}
    throw new Error("Missing return statement in function");
  }

/* <statement>  → <variable> <assignop> <expression>;
                | <block>
                | if (<expression>) <statement> <else_clause>
                | while (<expression>) <statement> */
  static final public AST statement() throws ParseException {
    Token t;
    AST tree, op, l, r;
    AST r1, r2;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case IDENTIFIER:
      tree = variable();
      t = jj_consume_token(ASSIGNOP);
      r = expression();
      jj_consume_token(SEMICOLON);
        op = new Node(t.image, tree, r);
        tree = op;
      {if (true) return tree;}
      break;
    case BEGIN:
      tree = block();
      {if (true) return tree;}
      break;
    default:
      jj_la1[4] = jj_gen;
      if (jj_2_1(3)) {
        t = jj_consume_token(KEYWORD);
        jj_consume_token(OPENPAREN);
        l = expression();
        jj_consume_token(CLOSEPAREN);
        r1 = statement();
        r2 = else_clause(r1);
        /* if the else clause is not null, attach it to the right of "if" op
           the left child of else will be the block executing when the condition of "if" is true (r1)
           the right child of else will be the block executing when that codition is false (r2) 
           the else_clause() method take r1 as an argument so that r1 can be used to attach to "else" op */
        if (r2 != null) {
            op = new Node(t.image, l, r2);
            tree = op;
        }
        // if else clause is null, the right child of "if" op is the block r1
        else {
            op = new Node(t.image, l, r1);
            tree = op;
        }
      {if (true) return tree;}
      } else {
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case KEYWORD:
          // "while" op is analogous to second case of "if" op
              t = jj_consume_token(KEYWORD);
          jj_consume_token(OPENPAREN);
          l = expression();
          jj_consume_token(CLOSEPAREN);
          r1 = statement();
        op = new Node(t.image, l, r1);
        tree = op;
      {if (true) return tree;}
          break;
        default:
          jj_la1[5] = jj_gen;
          jj_consume_token(-1);
          throw new ParseException();
        }
      }
    }
    throw new Error("Missing return statement in function");
  }

// <else_clause>  →  ( else <statement> )? 
  static final public AST else_clause(AST left) throws ParseException {
    Token t;
    AST tree = null, op, r;
    if (jj_2_2(2)) {
      t = jj_consume_token(KEYWORD);
      r = statement();
        op = new Node(t.image, left, r); // attach r1 (above) to the left of "else" op
        tree = op;
    } else {
      ;
    }
      {if (true) return tree;}
    throw new Error("Missing return statement in function");
  }

// <variable> → <id> 
  static final public AST variable() throws ParseException {
    Token t;
    t = jj_consume_token(IDENTIFIER);
                       {if (true) return new Leaf(t.image);}
    throw new Error("Missing return statement in function");
  }

// <expression> -> <simple_expression> <relopclause>
  static final public AST expression() throws ParseException {
    AST tree, op, r;
    tree = simple_expression();
    r = relopclause(tree);
        if (r != null) tree = r;
      {if (true) return tree;}
    throw new Error("Missing return statement in function");
  }

// <relopclause> -> <relop> <simple_expression> | ε  
  static final public AST relopclause(AST left) throws ParseException {
    Token t;
    AST tree = null, op;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case RELOP:
      t = jj_consume_token(RELOP);
      tree = simple_expression();
      op = new Node(t.image, left, tree);
      tree = op;
      break;
    default:
      jj_la1[6] = jj_gen;
      ;
    }
     {if (true) return tree;}
    throw new Error("Missing return statement in function");
  }

/* <simple_expression>  → <term> ( <addop> <term> ) * 
                        | <sign> <term> */
  static final public AST simple_expression() throws ParseException {
    Token t;
    AST tree, op, r;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case OPENPAREN:
    case IDENTIFIER:
    case DIGIT:
    case NOT:
      tree = term();
      label_4:
      while (true) {
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case ADDOP:
          ;
          break;
        default:
          jj_la1[7] = jj_gen;
          break label_4;
        }
        t = jj_consume_token(ADDOP);
        r = term();
        op = new Node(t.image, tree, r);
        tree = op;
      }
      {if (true) return tree;}
      break;
    case SIGN:
      jj_consume_token(SIGN);
      tree = term();
      {if (true) return null;}
      break;
    default:
      jj_la1[8] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    throw new Error("Missing return statement in function");
  }

// <term> →   <factor> (<mulop> <factor>) * 
  static final public AST term() throws ParseException {
    Token t;
    AST tree, op, r;
    tree = factor();
    label_5:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case MULTOP:
        ;
        break;
      default:
        jj_la1[9] = jj_gen;
        break label_5;
      }
      t = jj_consume_token(MULTOP);
      r = factor();
        op = new Node(t.image, tree, r);
        tree = op;
    }
      {if (true) return tree;}
    throw new Error("Missing return statement in function");
  }

// <factor>  → <id> |  (<expression>) |  <num> | !<factor>
  static final public AST factor() throws ParseException {
    Token t;
    AST tree, op;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case IDENTIFIER:
      t = jj_consume_token(IDENTIFIER);
                       {if (true) return new Leaf(t.image);}
      break;
    case OPENPAREN:
      jj_consume_token(OPENPAREN);
      tree = expression();
      jj_consume_token(CLOSEPAREN);
      {if (true) return tree;}
      break;
    case DIGIT:
      t = jj_consume_token(DIGIT);
                  {if (true) return new Leaf(t.image);}
      break;
    case NOT:
      t = jj_consume_token(NOT);
      tree = factor();
        op = new Node(t.image, null, tree);
        tree = op;
      {if (true) return tree;}
      break;
    default:
      jj_la1[10] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    throw new Error("Missing return statement in function");
  }

  static private boolean jj_2_1(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_1(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(0, xla); }
  }

  static private boolean jj_2_2(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_2(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(1, xla); }
  }

  static private boolean jj_3R_6() {
    if (jj_3R_8()) return true;
    return false;
  }

  static private boolean jj_3R_16() {
    if (jj_3R_17()) return true;
    return false;
  }

  static private boolean jj_3R_14() {
    if (jj_scan_token(IDENTIFIER)) return true;
    return false;
  }

  static private boolean jj_3_1() {
    if (jj_scan_token(KEYWORD)) return true;
    if (jj_scan_token(OPENPAREN)) return true;
    if (jj_3R_6()) return true;
    return false;
  }

  static private boolean jj_3R_13() {
    if (jj_scan_token(SIGN)) return true;
    return false;
  }

  static private boolean jj_3R_10() {
    if (jj_3R_15()) return true;
    return false;
  }

  static private boolean jj_3R_15() {
    if (jj_scan_token(BEGIN)) return true;
    return false;
  }

  static private boolean jj_3R_8() {
    Token xsp;
    xsp = jj_scanpos;
    if (jj_3R_12()) {
    jj_scanpos = xsp;
    if (jj_3R_13()) return true;
    }
    return false;
  }

  static private boolean jj_3R_12() {
    if (jj_3R_16()) return true;
    return false;
  }

  static private boolean jj_3R_9() {
    if (jj_3R_14()) return true;
    return false;
  }

  static private boolean jj_3R_7() {
    Token xsp;
    xsp = jj_scanpos;
    if (jj_3R_9()) {
    jj_scanpos = xsp;
    if (jj_3R_10()) {
    jj_scanpos = xsp;
    if (jj_3_1()) {
    jj_scanpos = xsp;
    if (jj_3R_11()) return true;
    }
    }
    }
    return false;
  }

  static private boolean jj_3_2() {
    if (jj_scan_token(KEYWORD)) return true;
    if (jj_3R_7()) return true;
    return false;
  }

  static private boolean jj_3R_21() {
    if (jj_scan_token(NOT)) return true;
    return false;
  }

  static private boolean jj_3R_20() {
    if (jj_scan_token(DIGIT)) return true;
    return false;
  }

  static private boolean jj_3R_19() {
    if (jj_scan_token(OPENPAREN)) return true;
    return false;
  }

  static private boolean jj_3R_17() {
    Token xsp;
    xsp = jj_scanpos;
    if (jj_3R_18()) {
    jj_scanpos = xsp;
    if (jj_3R_19()) {
    jj_scanpos = xsp;
    if (jj_3R_20()) {
    jj_scanpos = xsp;
    if (jj_3R_21()) return true;
    }
    }
    }
    return false;
  }

  static private boolean jj_3R_18() {
    if (jj_scan_token(IDENTIFIER)) return true;
    return false;
  }

  static private boolean jj_3R_11() {
    if (jj_scan_token(KEYWORD)) return true;
    return false;
  }

  static private boolean jj_initialized_once = false;
  /** Generated Token Manager. */
  static public ParserTokenManager token_source;
  static SimpleCharStream jj_input_stream;
  /** Current token. */
  static public Token token;
  /** Next token. */
  static public Token jj_nt;
  static private int jj_ntk;
  static private Token jj_scanpos, jj_lastpos;
  static private int jj_la;
  static private int jj_gen;
  static final private int[] jj_la1 = new int[11];
  static private int[] jj_la1_0;
  static {
      jj_la1_init_0();
   }
   private static void jj_la1_init_0() {
      jj_la1_0 = new int[] {0x28020,0x40,0x100000,0x28020,0x28000,0x20,0x80,0x100,0x2a2200,0x400,0x2a2000,};
   }
  static final private JJCalls[] jj_2_rtns = new JJCalls[2];
  static private boolean jj_rescan = false;
  static private int jj_gc = 0;

  /** Constructor with InputStream. */
  public Parser(java.io.InputStream stream) {
     this(stream, null);
  }
  /** Constructor with InputStream and supplied encoding */
  public Parser(java.io.InputStream stream, String encoding) {
    if (jj_initialized_once) {
      System.out.println("ERROR: Second call to constructor of static parser.  ");
      System.out.println("       You must either use ReInit() or set the JavaCC option STATIC to false");
      System.out.println("       during parser generation.");
      throw new Error();
    }
    jj_initialized_once = true;
    try { jj_input_stream = new SimpleCharStream(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
    token_source = new ParserTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 11; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
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
    for (int i = 0; i < 11; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  /** Constructor. */
  public Parser(java.io.Reader stream) {
    if (jj_initialized_once) {
      System.out.println("ERROR: Second call to constructor of static parser. ");
      System.out.println("       You must either use ReInit() or set the JavaCC option STATIC to false");
      System.out.println("       during parser generation.");
      throw new Error();
    }
    jj_initialized_once = true;
    jj_input_stream = new SimpleCharStream(stream, 1, 1);
    token_source = new ParserTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 11; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  /** Reinitialise. */
  static public void ReInit(java.io.Reader stream) {
    jj_input_stream.ReInit(stream, 1, 1);
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 11; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  /** Constructor with generated Token Manager. */
  public Parser(ParserTokenManager tm) {
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
    for (int i = 0; i < 11; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  /** Reinitialise. */
  public void ReInit(ParserTokenManager tm) {
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 11; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  static private Token jj_consume_token(int kind) throws ParseException {
    Token oldToken;
    if ((oldToken = token).next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    if (token.kind == kind) {
      jj_gen++;
      if (++jj_gc > 100) {
        jj_gc = 0;
        for (int i = 0; i < jj_2_rtns.length; i++) {
          JJCalls c = jj_2_rtns[i];
          while (c != null) {
            if (c.gen < jj_gen) c.first = null;
            c = c.next;
          }
        }
      }
      return token;
    }
    token = oldToken;
    jj_kind = kind;
    throw generateParseException();
  }

  static private final class LookaheadSuccess extends java.lang.Error { }
  static final private LookaheadSuccess jj_ls = new LookaheadSuccess();
  static private boolean jj_scan_token(int kind) {
    if (jj_scanpos == jj_lastpos) {
      jj_la--;
      if (jj_scanpos.next == null) {
        jj_lastpos = jj_scanpos = jj_scanpos.next = token_source.getNextToken();
      } else {
        jj_lastpos = jj_scanpos = jj_scanpos.next;
      }
    } else {
      jj_scanpos = jj_scanpos.next;
    }
    if (jj_rescan) {
      int i = 0; Token tok = token;
      while (tok != null && tok != jj_scanpos) { i++; tok = tok.next; }
      if (tok != null) jj_add_error_token(kind, i);
    }
    if (jj_scanpos.kind != kind) return true;
    if (jj_la == 0 && jj_scanpos == jj_lastpos) throw jj_ls;
    return false;
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
  static private int[] jj_lasttokens = new int[100];
  static private int jj_endpos;

  static private void jj_add_error_token(int kind, int pos) {
    if (pos >= 100) return;
    if (pos == jj_endpos + 1) {
      jj_lasttokens[jj_endpos++] = kind;
    } else if (jj_endpos != 0) {
      jj_expentry = new int[jj_endpos];
      for (int i = 0; i < jj_endpos; i++) {
        jj_expentry[i] = jj_lasttokens[i];
      }
      boolean exists = false;
      for (java.util.Iterator<?> it = jj_expentries.iterator(); it.hasNext();) {
        exists = true;
        int[] oldentry = (int[])(it.next());
        if (oldentry.length == jj_expentry.length) {
          for (int i = 0; i < jj_expentry.length; i++) {
            if (oldentry[i] != jj_expentry[i]) {
              exists = false;
              break;
            }
          }
          if (exists) break;
        }
      }
      if (!exists) jj_expentries.add(jj_expentry);
      if (pos != 0) jj_lasttokens[(jj_endpos = pos) - 1] = kind;
    }
  }

  /** Generate ParseException. */
  static public ParseException generateParseException() {
    jj_expentries.clear();
    boolean[] la1tokens = new boolean[22];
    if (jj_kind >= 0) {
      la1tokens[jj_kind] = true;
      jj_kind = -1;
    }
    for (int i = 0; i < 11; i++) {
      if (jj_la1[i] == jj_gen) {
        for (int j = 0; j < 32; j++) {
          if ((jj_la1_0[i] & (1<<j)) != 0) {
            la1tokens[j] = true;
          }
        }
      }
    }
    for (int i = 0; i < 22; i++) {
      if (la1tokens[i]) {
        jj_expentry = new int[1];
        jj_expentry[0] = i;
        jj_expentries.add(jj_expentry);
      }
    }
    jj_endpos = 0;
    jj_rescan_token();
    jj_add_error_token(0, 0);
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

  static private void jj_rescan_token() {
    jj_rescan = true;
    for (int i = 0; i < 2; i++) {
    try {
      JJCalls p = jj_2_rtns[i];
      do {
        if (p.gen > jj_gen) {
          jj_la = p.arg; jj_lastpos = jj_scanpos = p.first;
          switch (i) {
            case 0: jj_3_1(); break;
            case 1: jj_3_2(); break;
          }
        }
        p = p.next;
      } while (p != null);
      } catch(LookaheadSuccess ls) { }
    }
    jj_rescan = false;
  }

  static private void jj_save(int index, int xla) {
    JJCalls p = jj_2_rtns[index];
    while (p.gen > jj_gen) {
      if (p.next == null) { p = p.next = new JJCalls(); break; }
      p = p.next;
    }
    p.gen = jj_gen + xla - jj_la; p.first = token; p.arg = xla;
  }

  static final class JJCalls {
    int gen;
    Token first;
    int arg;
    JJCalls next;
  }

}

abstract class AST {
    String value;
    AST left, right;
}
class Leaf extends AST {
    Leaf(String val) {
        this.value = val;
        this.left = null;
        this.right = null;
    }
}
class Node extends AST {
    Node(String val, AST l, AST r) {
        this.value = val;
        this.left = l;
        this.right = r;
    }
}
