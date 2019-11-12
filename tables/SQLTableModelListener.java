package inventoryManager.tables;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import inventoryManager.db.SQLiteDatabase;

public class SQLTableModelListener implements TableModelListener {
    private SQLTableModel parent;

    public SQLTableModelListener(SQLTableModel sqlTableModel) {
	this.parent = sqlTableModel;
    }

    @Override
    public void tableChanged(TableModelEvent e) {
	System.out.println("CHANGED: " + e.getColumn() + " , " + e.getFirstRow());
	int columnChanged = e.getColumn();
	StringBuilder builder = new StringBuilder("UPDATE "); // UPDATE
	builder.append(parent.getTableName()); // <table>
	builder.append(" SET "); // SET
	builder.append(parent.getColumnName(columnChanged)); // <field>
	builder.append(" = "); // =
	// <new value>
	if (parent.getColumnClass(columnChanged) == String.class)
	    builder.append("'").append(parent.getValueAt(e.getFirstRow(), columnChanged)).append("'");
	else
	    builder.append(parent.getValueAt(e.getFirstRow(), columnChanged));
	builder.append(" WHERE ");
	String tmp = null;
	for (int i = 0; i < parent.getColumnCount(); i++) {
	    if (i == columnChanged)
		continue;
	    if (tmp != null)
		builder.append(" AND ");
	    if (parent.getColumnClass(i) == String.class)
		tmp = String.format("'%s'", parent.getValueAt(e.getFirstRow(), i));
	    else
		tmp = parent.getValueAt(e.getFirstRow(), i).toString();
	    builder.append(parent.getColumnName(i)).append(" = ").append(tmp);
	}
	builder.append(";");
	tmp = builder.toString().trim();
	System.out.println("Update String:\n\t" + tmp);
	SQLiteDatabase.getInstance().sExecuteStatement(tmp);
    }
}
