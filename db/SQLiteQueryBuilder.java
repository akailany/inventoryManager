package inventoryManager.db;

import java.util.LinkedList;
import java.util.List;

public class SQLiteQueryBuilder {
    private List<String> listColumns = new LinkedList<String>();
    private List<String> listTables = new LinkedList<String>();
    private List<String> listJoins = new LinkedList<String>();
    private List<String> listLeftJoins = new LinkedList<String>();
    private List<String> listWhereClause = new LinkedList<String>();
    private List<String> listOrderBy = new LinkedList<String>();
    private List<String> listGroupBy = new LinkedList<String>();
    private List<String> listHaving = new LinkedList<String>();

    public SQLiteQueryBuilder() {
    }

    public SQLiteQueryBuilder(String table) {
	listTables.add(table);
    }

    private void appendList(StringBuilder sql, List<String> list, String init, String sep) {
	boolean first = true;
	for (String s : list) {
	    if (first) {
		sql.append(init);
	    } else {
		sql.append(sep);
	    }
	    sql.append(s);
	    first = false;
	}
    }

    public SQLiteQueryBuilder column(String name) {
	listColumns.add(name);
	return this;
    }

    public SQLiteQueryBuilder column(String name, boolean groupBy) {
	listColumns.add(name);
	if (groupBy) {
	    listGroupBy.add(name);
	}
	return this;
    }

    public SQLiteQueryBuilder from(String table) {
	listTables.add(table);
	return this;
    }

    public SQLiteQueryBuilder groupBy(String expr) {
	listGroupBy.add(expr);
	return this;
    }

    public SQLiteQueryBuilder having(String expr) {
	listHaving.add(expr);
	return this;
    }

    public SQLiteQueryBuilder join(String join) {
	listJoins.add(join);
	return this;
    }

    public SQLiteQueryBuilder leftJoin(String join) {
	listLeftJoins.add(join);
	return this;
    }

    public SQLiteQueryBuilder orderBy(String name) {
	listOrderBy.add(name);
	return this;
    }

    @Override
    public String toString() {
	StringBuilder sql = new StringBuilder("SELECT ");
	if (listColumns.size() == 0) {
	    sql.append("*");
	} else {
	    appendList(sql, listColumns, "", ", ");
	}
	appendList(sql, listTables, " FROM ", ", ");
	appendList(sql, listJoins, " JOIN ", " JOIN ");
	appendList(sql, listLeftJoins, " LEFT JOIN ", " LEFT JOIN ");
	appendList(sql, listWhereClause, " WHERE ", " AND ");
	appendList(sql, listGroupBy, " GROUP BY ", ", ");
	appendList(sql, listHaving, " HAVING ", " AND ");
	appendList(sql, listOrderBy, " ORDER BY ", ", ");
	String out = sql.toString().trim();
	System.out.println("Builder output: " + out);
	return out;
    }

    public SQLiteQueryBuilder where(String expr) {
	listWhereClause.add(expr);
	return this;
    }
}
