import org.antlr.v4.runtime.tree.ParseTreeVisitor;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import org.antlr.v4.runtime.CharStreams;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class main {
    public static void main(String[] args) throws IOException {

        // we expect exactly one argument: the name of the input file
        if (args.length != 1) {
            System.err.println("\n");
            System.err.println("Impl Interpreter\n");
            System.err.println("=================\n\n");
            System.err.println("Please give as input argument a filename\n");
            System.exit(-1);
        }
        String filename = args[0];

        // open the input file
        CharStream input = CharStreams.fromFileName(filename);
        //new ANTLRFileStream (filename); // depricated

        // create a lexer/scanner
        ccLexer lex = new ccLexer(input);

        // get the stream of tokens from the scanner
        CommonTokenStream tokens = new CommonTokenStream(lex);

        // create a parser
        ccParser parser = new ccParser(tokens);

        // and parse anything from the grammar for "start"
        ParseTree parseTree = parser.start();

        // Construct an interpreter and run it on the parse tree
        Interpreter interpreter = new Interpreter();
        String result = interpreter.visit(parseTree);
        System.out.println("The result is: " + result);
    }
}

// We write an interpreter that ccements interface
// "ccVisitor<T>" that is automatically generated by ANTLR
// This is parameterized over a return type "<T>" which is in our case
// sccy a String.

class Interpreter extends AbstractParseTreeVisitor<String> implements ccVisitor<String> {


    @Override
    public String visitStart(ccParser.StartContext ctx) {
        System.out.println("value: " + ctx.i.getText());
        visit(ctx.h);
        visit(ctx.i);
        visit(ctx.o);
        visit(ctx.l);
        visit(ctx.u);
        visit(ctx.s);
        return null;
    }

    @Override
    public String visitHardwareDecl(ccParser.HardwareDeclContext ctx) {
        System.out.println("name: " + ctx.name.getText());
        return null;
    }

    @Override
    public String visitUpdateDecl(ccParser.UpdateDeclContext ctx) {
        System.out.println("updatedecl!: " + ctx.u.getText());
        return null;
    }

    @Override
    public String visitUpdate(ccParser.UpdateContext ctx) {
        System.out.println("update!: " + ctx.input.getText());
        return null;
    }

    @Override
    public String visitSimulateDecl(ccParser.SimulateDeclContext ctx) {
        System.out.println("simudecl!: " + ctx.s.getText());
        return null;
    }

    @Override
    public String visitSimulate(ccParser.SimulateContext ctx) {
        System.out.println("simu!: " + ctx.input.getText() + " value: " + ctx.value.getText());
        return null;
    }

    @Override
    public String visitLatchesdecl(ccParser.LatchesdeclContext ctx) {
//        System.out.println("not!: " + ctx.s.getText());
        return null;
    }

    @Override
    public String visitLatches(ccParser.LatchesContext ctx) {
//        System.out.println("not!: " + ctx.s.getText());
        return null;
    }

    @Override
    public String visitInputDecl(ccParser.InputDeclContext ctx) {
        for (Token s : ctx.signals){
            System.out.println("sig" + s.getText());
        }
        return null;
    }

    @Override
    public String visitOutputDecl(ccParser.OutputDeclContext ctx) {
//        System.out.println("output: " + ctx.s.getText());
        return null;
    }

    @Override
    public String visitNot(ccParser.NotContext ctx) {
//        System.out.println("not!: " + ctx.s.getText());
        return null;
    }

    @Override
    public String visitSignal(ccParser.SignalContext ctx) {
//        System.out.println("not!: " + ctx.s.getText());
        return null;
    }

    @Override
    public String visitOr(ccParser.OrContext ctx) {
//        System.out.println("not!: " + ctx.s.getText());
        return null;
    }

    @Override
    public String visitAnd(ccParser.AndContext ctx) {
//        System.out.println("not!: " + ctx.s.getText());
        return null;
    }

    @Override
    public String visitParen(ccParser.ParenContext ctx) {
//        System.out.println("not!: " + ctx.s.getText());
        return null;
    }
}

