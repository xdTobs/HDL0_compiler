import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.AbstractParseTreeVisitor;
import org.antlr.v4.runtime.tree.ParseTree;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @author Henrik Zenkert
 * @author Jakob Hansen
 * @author Tobias Schønau
 * @author Frederik Rolin
 */

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
        System.out.println("The result is: \n" + result);

        // Write to a file called <input_filename>.html
        var f = new File(filename).toString().substring(0, filename.length() - 3) + "html";
        FileWriter output = new FileWriter(f);
        BufferedWriter writer = new BufferedWriter(output);
        writer.write(result);
        writer.close();

    }
}

// We write an interpreter that ccements interface
// "ccVisitor<T>" that is automatically generated by ANTLR
// This is parameterized over a return type "<T>" which is in our case
// sccy a String.

class Interpreter extends AbstractParseTreeVisitor<String> implements ccVisitor<String> {
    @Override
    public String visitStart(ccParser.StartContext ctx) {
        String head = """
                <head>
                    <title>MyLittleApp</title>
                    <script src="https://polyfill.io/v3/polyfill.min.js?features=es6"></script>
                    <script type="text/javascript" id="MathJax-script" async
                        src="https://cdn.jsdelivr.net/npm/mathjax@3/es5/tex-chtml.js">
                    </script>
                    <style>
                        body {
                            max-width: 600px;
                            margin: auto;
                        }
                        li {
                            list-style: none;
                        }
                    </style>
                </head>
                """;

        String result = "\n\n" + visit(ctx.h) + "\n";
        result += visit(ctx.i) + "\n";
        result += visit(ctx.o) + "\n";
        result += visit(ctx.l) + "\n";
        result += visit(ctx.u) + "\n";
        result += visit(ctx.s) + "\n";

        return "<!DOCTYPE html>\n" +
               "<html>\n" +
               head +
               "\n<body>" +
               result +
               "</body>" +
               "\n</html>";
    }

    @Override
    public String visitHardwareDeclaration(ccParser.HardwareDeclarationContext ctx) {
        return "<h1>" + ctx.name.getText() + "</h1>";
    }

    @Override
    public String visitUpdateDeclaration(ccParser.UpdateDeclarationContext ctx) {
        StringBuilder result = new StringBuilder();
        result.append("\n<h2>Updates</h2>\n");
        result.append("<ul>\n");
        for (ccParser.UpdateContext updateCtx : ctx.updates) {
            result.append(visit(updateCtx));
        }
        result.append("</ul>");
        return result.toString();
    }

    @Override
    public String visitUpdate(ccParser.UpdateContext ctx) {
        return "\t<li>\n" +
               "\t\t<span style='display:inline-block; min-width:80px;'>" + ctx.input.getText() + "</span>\n" +
               "\t\t&larr;&nbsp; \\(" + visit(ctx.e) + "\\)\n" +
               "\t</li>\n";

//        return "\t<li>" + ctx.input.getText() + " &larr; \\(" + visit(ctx.e) + "\\)</li>\n";
    }

    @Override
    public String visitSimulateDeclaration(ccParser.SimulateDeclarationContext ctx) {

        StringBuilder result = new StringBuilder();
        result.append("<h2>Simulation inputs</h2>\n");
        result.append("<ul>\n");
        for (ccParser.SimulateContext simulateCtx : ctx.s) {
            result.append(visit(simulateCtx));
        }
        result.append("</ul>");
        return result.toString();
    }

    @Override
    public String visitSimulate(ccParser.SimulateContext ctx) {
        return "\t<li><b>" + ctx.input.getText() + "</b> = " + ctx.value.getText() + "</li>\n";
    }

    @Override
    public String visitLatchesDeclaration(ccParser.LatchesDeclarationContext ctx) {
        StringBuilder result = new StringBuilder();
        result.append("<h2>Latches</h2>\n");

        result.append("<ul>\n");
        for (ccParser.LatchesContext latchContext : ctx.l) {
            result.append(visit(latchContext));
        }
        result.append("</ul>");
        return result.toString();
    }

    //
//
//
//
    @Override
    public String visitLatches(ccParser.LatchesContext ctx) {

        return "\t<li>\n" +
               "\t\t<span style='display:inline-block; min-width:80px;'>" + ctx.input.getText() + "</span>\n" +
               "\t\t&rarr; &nbsp;" + ctx.output.getText() + "\n" +
               "\t</li>\n ";
    }

    @Override
    public String visitInputDeclaration(ccParser.InputDeclarationContext ctx) {
        StringBuilder result = new StringBuilder();
        result.append("<h2>Inputs</h2>\n");
        result.append("<ul>\n");
        for (Token signal : ctx.signals) {
            result.append("\t<li>").append(signal.getText()).append("</li>\n");
        }
        result.append("</ul>");
        return result.toString();
    }

    @Override
    public String visitOutputDeclaration(ccParser.OutputDeclarationContext ctx) {
        StringBuilder result = new StringBuilder();
        result.append("<h2>Outputs</h2>\n");
        result.append("<ul>\n");
        for (Token signal : ctx.signals) {
            result.append("\t<li>").append(signal.getText()).append("</li>\n");
        }
        result.append("</ul>");
        return result.toString();
    }

    @Override
    public String visitNot(ccParser.NotContext ctx) {
        return "(\\neg " + visit(ctx.e) + ")";
    }

    @Override
    public String visitSignal(ccParser.SignalContext ctx) {
        return "\\mathrm{" + ctx.getText() + "} ";
    }

    @Override
    public String visitOr(ccParser.OrContext ctx) {
        return "(" + visit(ctx.e1) + "\\vee " + visit(ctx.e2) + ")";
    }

    @Override
    public String visitAnd(ccParser.AndContext ctx) {
        return "(" + visit(ctx.e1) + "\\wedge " + visit(ctx.e2) + ")";
    }

    @Override
    public String visitParen(ccParser.ParenContext ctx) {
        return "(" + visit(ctx.e) + ")";
    }
}

