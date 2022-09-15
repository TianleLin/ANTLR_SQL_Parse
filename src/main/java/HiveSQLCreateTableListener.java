import github.com.antlr.grammars.hivesql.parser.HiveParser;
import github.com.antlr.grammars.hivesql.parser.HiveParser.TableNameContext;
import github.com.antlr.grammars.hivesql.parser.HiveParserBaseListener;

import java.util.LinkedList;
import java.util.List;

class HiveSQLCreateTableListener extends HiveParserBaseListener {
    private String tableName;
    private List<String> colTypeList = new LinkedList<>();
    private List<String> identifierList = new LinkedList<>();

    @Override
    public void enterColumnNameTypeOrConstraint(HiveParser.ColumnNameTypeOrConstraintContext ctx) {
        String identifier = ctx.getStart().getText();
        if (identifier.startsWith("`")) {
            identifier = identifier.replaceAll("`", "");
        }
        identifierList.add(identifier);
        colTypeList.add(ctx.columnNameTypeConstraint().colType().getText());
    }


    @Override
    public void enterTableName(HiveParser.TableNameContext ctx) {
        tableName = ctx.getText();
        if (tableName.startsWith("`")) {
            tableName = tableName.replaceAll("`", "");
        }
    }

    public String getTableNameList() {
        return tableName;
    }

    public List<String> getColTypeList() {
        return colTypeList;
    }

    public List<String> getIdentifierList() {
        return identifierList;
    }
}
