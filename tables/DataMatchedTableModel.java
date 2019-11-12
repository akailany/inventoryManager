package inventoryManager.tables;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import javax.swing.table.DefaultTableModel;

public class DataMatchedTableModel extends DefaultTableModel {
    /**
     * 
     */
    private static final long serialVersionUID = -296125740077303375L;
    private Class<?>[] columns;

    public DataMatchedTableModel(String[] columnNames, int i) {
	super(columnNames, i);
    }

    public void verifyDataTypes() {
	columns = new Class<?>[getColumnCount()];
	Object[] arr = new Object[getRowCount()];
	Object[] ret;
	for (int column = 0; column < getColumnCount(); column++) {
	    for (int row = 0; row < getRowCount(); row++) {
		arr[row] = getValueAt(row, column);
	    }
	    ret = convertToSuperclass(arr);
	    columns[column] = ret.getClass().getComponentType();
	    for (int row = 0; row < getRowCount(); row++) {
		setValueAt(ret[row], row, column);
	    }
	}
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
	if (columns != null && columns.length > columnIndex) {
	    return columns[columnIndex];
	} else
	    return super.getColumnClass(columnIndex);
    }

    public Object[] convertToSuperclass(Object[] objects) {
	Object[] out = new Object[objects.length];
	// Check booleans first
	int i = 0;
	for (Object s : objects) {
	    if (s instanceof Boolean)
		out[i++] = (boolean) s;
	    else {
		if (!s.toString().toLowerCase().matches("(true|false)"))
		    break;
		out[i++] = Boolean.parseBoolean(s.toString());
	    }
	    Boolean[] db = new Boolean[out.length];
	    System.arraycopy(out, 0, db, 0, out.length);
	    return db;
	}
	// Check for integers
	i = 0;
	for (Object s : objects) {
	    if (s instanceof Integer)
		out[i++] = (int) s;
	    else
		try {
		    out[i++] = Integer.parseInt(s.toString());
		} catch (Exception exc) {
		    break;
		}
	    Integer[] db = new Integer[out.length];
	    System.arraycopy(out, 0, db, 0, out.length);
	    return db;
	}
	// Check for doubles
	i = 0;
	for (Object s : objects) {
	    if (s instanceof Double)
		out[i++] = (double) s;
	    else
		try {
		    out[i++] = Double.parseDouble(s.toString());
		} catch (Exception exc) {
		    break;
		}
	    Double[] db = new Double[out.length];
	    System.arraycopy(out, 0, db, 0, out.length);
	    return db;
	}
	// Check for Date
	i = 0;
	for (Object s : objects) {
	    if (s instanceof Date)
		out[i++] = (Date) s;
	    else
		try {
		    out[i++] = LocalDateTime.parse(s.toString(), DateTimeFormatter.ofPattern("MM/d/yyyy"));
		} catch (Exception exc) {
		    break;
		}
	    Date[] db = new Date[out.length];
	    System.arraycopy(out, 0, db, 0, out.length);
	    return db;
	}
	// Check for String
	i = 0;
	for (Object s : objects) {
	    if (s instanceof String)
		out[i++] = (String) s;
	    else if (s.toString().matches("\\p{Alnum}")) {
		out[i++] = s.toString();
	    } else
		break;
	    String[] db = new String[out.length];
	    System.arraycopy(out, 0, db, 0, out.length);
	    return db;
	}
	// Just return a clone
	i = 0;
	for (Object o : objects)
	    out[i++] = o;
	return out;
    }
}
