// Name: Ngu (Nathan) Dang

/* 
NOTES ON HOW TO USE THIS FILE:
	- This file will generate MIPS code for a given AST from the Parser, the algorithm used for code generation in this version is
simple and naive (linear IR I think?).
	- The output is a sequence of MIPS code printed on command line terminal, thus, we need to copy and paste the code to a ".a" file,
for example, "cp5.a"
	- The code will be indented nicely but the indentation in this version is handled in a naive way, that is, the sequence of tabs
(i.e. "\t\t\t"), a better version of indenting and printing the code directly to a ".a" file will be developed in the "future"...
	- This file takes a .txt file as an argument, then the parser will parse the given text and produce the MIPS code
	- This version can handle very simple C code including assignment statements and while loops, a better version which can handle
if-else statement will be developed in the "future"...
	- Instruction on running MIPS code produced by this generator using SPIM:
		+ Assuming that the computer has this version of SPIM (if not just download it, super easy):
			SPIM Version 8.0 of January 8, 2010
			Copyright 1990-2010, James R. Larus.
			All Rights Reserved.
		+ First, type the following command: "spim"
		+ Then, type "load" + "the name of the .a file" (i.e. load "cp5.a")
		+ Finally, type "run" and witness the magic...
	- CAUTION: the current version of the parser is having a serious problem with parsing "printf()" statement, therefore,
the generator cannot produce MIPS printing code. As a result, all the printing for .a file MUST be done manually for now.
For convenience, the results of all calculations in the test files are stored in variable named "t" and the following MIPS
code serves the purpose of printing "t":

			# print t
            lw $a0, t
            li $v0, 1
            syscall

            #newline
            la $a0, nline
            li $v0, 4
            syscall 

With that being said, all we need to do is copy and paste those codes in the .a file after the generator has finished its job.
Specifically, after the calculation, the result of t will be stored in a register (i.e. sw $t0, t); then, we just need to copy
those codes right after the "sw ..." line
*/

import java.util.*;
import java.io.*;

public class CodeGen {
	public static Parser parser;
	public static Stack<String> UTS = new Stack<String>(); // used temp stack
	public static Stack<String> FTS = new Stack<String>(); // free temp stack

	// initialize the stack with 7 temporary registers
	public static void stackInit() {
		for (int i = 7; i >= 0; --i)
			FTS.push("$t" + i);
	}

	// indentation for printing AST
	public static void indent(int n) {
		for (int i = 0; i < n; ++i) {
			System.out.print(" ");
			System.out.print(" ");
			System.out.print(" ");
		}
	}

	// print the AST
	public static void toString(AST t, int level) {
		// if its left/right leaf is null, print "--"
		if (t == null) {
			indent(level);
			System.out.println("--");
			return;
		}
		indent(level);
		System.out.println(t.value);
		// if it's a leaf, return
		if (t.left == null && t.right == null)
			return;
		toString(t.left, level + 1);
		toString(t.right, level + 1);
	}

	// move all temps from UTS to FTS
	public static void resetTmp() {
		while (!UTS.empty())
			FTS.push(UTS.pop());
	}

	// check if a leaf's value is a numerical value or a string
	public static boolean isNumeric(String str) {
		try {
			Double.parseDouble(str);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	// code generator for variables declaration
	public static void varGen(AST t) {
		if (t == null)
			return;
		if (t.value == ";") {
			varGen(t.left);
			varGen(t.right);
		} else if (t.value == ":")
			varGen(t.right);
		else if (t.value == ",") {
			varGen(t.left);
			varGen(t.right);
		} else
			System.out.println(t.value + ":" + "\t\t\t" + ".word 0");
	}

	// code generator for statements declaration
	public static void stmGen(AST t) {
		if (t == null)
			return;
		if (t.value.compareTo(";") == 0) {
			stmGen(t.left);
			stmGen(t.right);
		}
		// emitting code for addition stms
		else if (t.value.compareTo("+") == 0) {
			stmGen(t.left);
			stmGen(t.right);
			String tmp1 = UTS.pop();
			String tmp2 = UTS.pop();
			System.out.println("\t\t\t" + "add " + tmp1 + ", " + tmp1 + ", " + tmp2);
			FTS.push(tmp2);
			UTS.push(tmp1);
		}
		// emitting code for substraction stms
		else if (t.value.compareTo("-") == 0) {
			stmGen(t.left);
			stmGen(t.right);
			String tmp1 = UTS.pop();
			String tmp2 = UTS.pop();
			System.out.println("\t\t\t" + "sub " + tmp2 + ", " + tmp2 + ", " + tmp1);
			FTS.push(tmp1);
			UTS.push(tmp2);
		}
		// emitting code for multiplication stms
		else if (t.value.compareTo("*") == 0) {
			stmGen(t.left);
			stmGen(t.right);
			String tmp1 = UTS.pop();
			String tmp2 = UTS.pop();
			System.out.println("\t\t\t" + "mul " + tmp1 + ", " + tmp1 + ", " + tmp2);
			FTS.push(tmp2);
			UTS.push(tmp1);
		}
		// emitting code for division stms
		else if (t.value.compareTo("/") == 0) {
			stmGen(t.left);
			stmGen(t.right);
			String tmp1 = UTS.pop();
			String tmp2 = UTS.pop();
			System.out.println("\t\t\t" + "div " + tmp2 + ", " + tmp2 + ", " + tmp1);
			FTS.push(tmp1);
			UTS.push(tmp2);
		}
		// emmiting code for storing value into registers
		else if (t.value.compareTo("=") == 0) {
			stmGen(t.right);
			System.out.println("\t\t\t" + "sw " + UTS.peek() + ", " + t.left.value);
			resetTmp();
		}
		// emitting printing code
		else if (t.value.compareTo("()") == 0) {
			// print the result of t.left.value
			System.out.println("\t\t\t" + "lw $a0, " + t.left.value);
			System.out.println("\t\t\t" + "li $v0, 1");
			System.out.println("\t\t\t" + "syscall");
			// print new line
			System.out.println("\t\t\t" + "lw $a0, nline");
			System.out.println("\t\t\t" + "li $v0, 4");
			System.out.println("\t\t\t" + "syscall");
		}
		// emitting code for != relop
		else if (t.value.compareTo("!=") == 0) {
			stmGen(t.left);
			stmGen(t.right);
			String tmp1 = UTS.pop();
			String tmp2 = UTS.pop();
			System.out.println("\t\t\t" + "seq " + tmp2 + ", " + tmp2 + ", " + tmp1);
			System.out.println("\t\t\t" + "li " + tmp1 + ", 1");
			System.out.println("\t\t\t" + "beq " + tmp2 + ", " + tmp1 + ", out");
			System.out.println("\t\t\t" + "bnez " + tmp2 + ", in");
			FTS.push(tmp1);
			FTS.push(tmp2);
		}
		// emitting code for == relop
		else if (t.value.compareTo("==") == 0) {
			stmGen(t.left);
			stmGen(t.right);
			String tmp1 = UTS.pop();
			String tmp2 = UTS.pop();
			System.out.println("\t\t\t" + "seq " + tmp2 + ", " + tmp2 + ", " + tmp1);
			System.out.println("\t\t\t" + "li " + tmp1 + ", 1");
			System.out.println("\t\t\t" + "beq " + tmp2 + ", " + tmp1 + ", in");
			System.out.println("\t\t\t" + "bnez " + tmp2 + ", out");
			FTS.push(tmp1);
			UTS.push(tmp2);
		}
		// emitting code for while loop
		else if (t.value.compareTo("while") == 0) {
			System.out.println("loop:");
			stmGen(t.left);
			System.out.println("in:");
			generator(t.right, 1); // parse the block of the while loop with all "headers" skipped
			System.out.println("\t\t\t" + "b loop"); // end of loop
			System.out.println("out");
		}
		// leaf case
		else {
			String tmp = FTS.pop();
			if (isNumeric(t.value))
				System.out.println("\t\t\t" + "li " + tmp + ", " + t.value);
			else
				System.out.println("\t\t\t" + "lw " + tmp + ", " + t.value);
			UTS.push(tmp);
		}
	}

	/*
	 * code generator: since the parser parse a block of code (i.e. { ... }), and a
	 * while loop can also have a block of code for itself besides the main(), a
	 * flag is needed in order to tell the generator when to print some "headers"
	 * like the ones trigger main function for MIPS. Specifically, these headers
	 * only need to be printed once, for the block of a loop, they MUST be skipped.
	 */
	public static void generator(AST tree, int flag) {
		if (flag == 0)
			System.out.println("\t\t\t" + ".data");
		// generating code for variable delaration
		varGen(tree.left);
		if (flag == 0) {
			// trigger main
			System.out.println("nline:" + "\t\t\t" + ".asciiz" + "\"\\n\"" + "\n");
			System.out.println("\t\t\t" + ".text");
			System.out.println("\t\t\t" + ".globl main");
			System.out.println("main:");
		}
		// generating code for statements
		stmGen(tree.right);
	}

	public static void main(String[] args) throws ParseException, FileNotFoundException {
		parser = new Parser(new FileInputStream(args[0]));
		stackInit();
		AST tree = parser.Input(); // get the AST from the Parser
		// toString(tree, 1); // print AST for debugging purpose
		generator(tree, 0);
		// end of mips
		System.out.println("\t\t\t" + "li " + "$v0, 10");
		System.out.println("\t\t\t" + "syscall");
	}
}

// AST class
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
