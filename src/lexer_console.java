import java.io.FileReader;
import java.io.IOException;

public class lexer_console {
    public static boolean skip_comment=false;
    public static void main(String args[]) throws IOException {


        if(args.length < 1){
            System.out.println("error: pass my file!");
            return;
        }
        if(args.length >1){
            if(args[1].equals("-filter")){
                skip_comment = true;
            }
        }
        MyLexer lexer = null;
        try {
            lexer = new MyLexer(new FileReader(args[0]));
        }
        catch (Exception e){
            System.out.println("error:" + e.getMessage());
            return;
        }
        while(true) {

            int tk = 0;
            try {
                tk = lexer.yylex();
            }
            catch (Error e){
                System.out.println("error: " +e.getMessage());
                break;
            }
            if(tk==0) break;

            if(tk==Parser.VAR){
                System.out.print(String.format("VAR(\"%s\", %d, %d, %d); ",lexer.yytext(),lexer.yyline(),lexer.yycolumn(),lexer.yycolumn() + lexer.yytext().length()-1));
            }
            else if(tk==Parser.NUMBER){
                System.out.print(String.format("NUMBER(%s, %d, %d, %d); ",lexer.yytext(),lexer.yyline(),lexer.yycolumn(),lexer.yycolumn() + lexer.yytext().length()-1));
            }
            else if(tk==Parser.SOME_OPERATOR){
                System.out.print(String.format("SOME_OP(%s, %d, %d, %d); ",lexer.yytext(),lexer.yyline(),lexer.yycolumn(),lexer.yycolumn() + lexer.yytext().length()-1));
            }
            else if(tk==Parser.COMMENTS){
                if(!skip_comment)
                System.out.print(String.format("COMMENT(\"%s\", %d, %d, %d); ",lexer.yytext().replaceAll("\\p{Cntrl}", ""),lexer.yyline(),lexer.yycolumn(),lexer.yycolumn() + lexer.yytext().length()-1));
            }
            else if(tk==Parser.NL){

            }
            else
            {
                String tokenName = null;
                switch (tk){
                    case Parser.BRACKET_CLOSE:               tokenName = "CBRACKET_CLOSE"; break;
                    case Parser.BRACKET_OPEN:               tokenName = "BRACKET_OPEN"; break;
                    case Parser.SKIP_KW:               tokenName = "SKIP_KW"; break;
                    case Parser.WRITE_KW:               tokenName = "WRITE_KW"; break;
                    case Parser.READ_KW:               tokenName = "READ_KW"; break;
                    case Parser.WHILE_KW:               tokenName = "WHILE_KW"; break;
                    case Parser.DO_KW:               tokenName = "DO_KW"; break;
                    case Parser.IF_KW:               tokenName = "IF_KW"; break;
                    case Parser.THEN_KW:               tokenName = "THEN_KW"; break;
                    case Parser.ELSE_KW:               tokenName = "ELSE_KW"; break;
                    case Parser.SEMICOLON:               tokenName = "SEMICOLON"; break;
                    case Parser.COLONEQUAL:               tokenName = "COLONEQUAL"; break;
                    case Parser.COLON:               tokenName = "COLON"; break;
                    default: tk =-1;break;
                }
                System.out.print(String.format("%s(%d, %d, %d); ",tokenName,lexer.yyline(),lexer.yycolumn(),lexer.yycolumn() + lexer.yytext().length()-1));
            }
        }
    }
}
