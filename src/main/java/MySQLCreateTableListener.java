import github.com.antlr.grammars.mysql.parser.MySqlParser;
import github.com.antlr.grammars.mysql.parser.MySqlParserBaseListener;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

class MySQLCreateTableListener extends MySqlParserBaseListener {
    private String tableName = null;
    private List<String> createDefinitionList = new LinkedList<>();
    private List<String> uidList = new LinkedList<>();
    private List<String> dataTypeList = new LinkedList<>();

    @Override
    public void enterColumnCreateTable(MySqlParser.ColumnCreateTableContext ctx) {
        // extract table name
        tableName = ctx.tableName().getText();
        if (tableName.startsWith("`")) {
            tableName = tableName.replaceAll("`", "");
        }
        MySqlParser.CreateDefinitionsContext createDefinitionsContext = ctx.createDefinitions();
        List<MySqlParser.CreateDefinitionContext> createDefinitionContextList = createDefinitionsContext.createDefinition();
        for (MySqlParser.CreateDefinitionContext createDefinitionContext :
                createDefinitionContextList) {
            // extract column name
            List<MySqlParser.UidContext> uidContextList = createDefinitionContext.getRuleContexts(MySqlParser.UidContext.class);
            for (MySqlParser.UidContext uidContext :
                        uidContextList) {
                String uid = uidContext.getText();
                if (uid.startsWith("`")) {
                    uid = uid.replaceAll("`", "");
                }
                uidList.add(uid);
            }
            // extract column type
            List<MySqlParser.ColumnDefinitionContext> columnDefinitionContextList = createDefinitionContext.getRuleContexts(MySqlParser.ColumnDefinitionContext.class);
            for (MySqlParser.ColumnDefinitionContext columnDefinitionContext :
                    columnDefinitionContextList) {
                dataTypeList.add(columnDefinitionContext.start.getText());
            }
        }
    }

    public String getTableName() {
        return tableName;
    }

    public List<String> getCreateDefinitionList() {
        return createDefinitionList;
    }

    public List<String> getUidList() {
        return uidList;
    }

    public List<String> getDataTypeList() {
        return dataTypeList;
    }
}
