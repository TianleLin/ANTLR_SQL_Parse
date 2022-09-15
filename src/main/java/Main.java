import github.com.antlr.grammars.hivesql.parser.HiveLexer;
import github.com.antlr.grammars.hivesql.parser.HiveParser;
import github.com.antlr.grammars.mysql.parser.MySqlLexer;
import github.com.antlr.grammars.mysql.parser.MySqlParser;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.antlr.v4.runtime.tree.Trees;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

class TestSql {
    public static void main(String args[]) throws IOException {
//        mysqlParser();
//        printMysqlTree();
        hiveSqlParser();
//        printHiveSqlTree();
    }
    public static String printSyntaxTree(Parser parser, ParseTree root) {
        StringBuilder buf = new StringBuilder();
        recursive(root, buf, 0, Arrays.asList(parser.getRuleNames()));
        return buf.toString();
    }

    private static void recursive(ParseTree aRoot, StringBuilder buf, int offset, List<String> ruleNames) {
        for (int i = 0; i < offset; i++) {
            buf.append("  ");
        }
        buf.append(Trees.getNodeText(aRoot, ruleNames)).append("\n");
        if (aRoot instanceof ParserRuleContext) {
            ParserRuleContext prc = (ParserRuleContext) aRoot;
            if (prc.children != null) {
                for (ParseTree child : prc.children) {
                    recursive(child, buf, offset + 1, ruleNames);
                }
            }
        }
    }

    private static void mysqlParser() throws IOException {
        /** antlr4 格式化SQL **/
        CharStream charStreams = CharStreams.fromFileName("src/mysql.txt");
        MySqlLexer lexer = new MySqlLexer(charStreams);
        MySqlParser parser = new MySqlParser(new CommonTokenStream(lexer));
        //定义CreateTableListener
        MySQLCreateTableListener listener = new MySQLCreateTableListener();
        ParseTreeWalker.DEFAULT.walk(listener, parser.sqlStatements());
        /**提取关键参数-表名*/
        String tableName= listener.getTableName();
        List<String> uidList = listener.getUidList();
        List<String> dataTypeList = listener.getDataTypeList();
        /**测试打印方法*/
        System.out.println(tableName);
        System.out.println(uidList);
        System.out.println(uidList.size());
        System.out.println(dataTypeList);
        System.out.println(dataTypeList.size());
    }
    private static void printMysqlTree() throws IOException {
        CharStream charStreams = CharStreams.fromFileName("src/mysql.txt");
        MySqlLexer lexer = new MySqlLexer(charStreams);
        MySqlParser parser = new MySqlParser(new CommonTokenStream(lexer));
        ParseTree parseTree = parser.createTable();
        System.out.println(printSyntaxTree(parser, parseTree));
    }
    private static void hiveSqlParser() throws IOException {
        /** antlr4 格式化SQL **/
        CharStream charStreams = CharStreams.fromFileName("src/hivesql.txt");
        HiveLexer lexer = new HiveLexer(charStreams);
        HiveParser parser = new HiveParser(new CommonTokenStream(lexer));
        //定义CreateTableListener
        HiveSQLCreateTableListener listener = new HiveSQLCreateTableListener();
        ParseTreeWalker.DEFAULT.walk(listener, parser.statements());
        /**提取关键参数-表名*/
        String tableName= listener.getTableNameList();
        List<String> colTypeList = listener.getColTypeList();
        List<String> identifierList = listener.getIdentifierList();
        /**测试打印方法*/
        System.out.println(tableName);
        System.out.println(colTypeList);
        System.out.println(colTypeList.size());
        System.out.println(identifierList);
        System.out.println(identifierList.size());
    }
    private static void printHiveSqlTree() throws IOException {
        CharStream charStreams = CharStreams.fromFileName("src/hivesql.txt");
        HiveLexer lexer = new HiveLexer(charStreams);
        HiveParser parser = new HiveParser(new CommonTokenStream(lexer));
        ParseTree parseTree = parser.statements();
        System.out.println(printSyntaxTree(parser, parseTree));
    }
}
