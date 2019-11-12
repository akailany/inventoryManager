package inventoryManager.tables;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import javax.swing.table.DefaultTableModel;

@SuppressWarnings("serial")
public class SQLTableModel extends DefaultTableModel {
	private ResultSetMetaData metaData;
	private int columnCount;
	private String[] typeNames;
	private String[] headers;
	private Class<?>[] columnTypes;
	private String tableName;

	public SQLTableModel(ResultSet resultSet) throws SQLException {
		super();
		System.out.println(getClass().getSimpleName() + " Constructor called");
		System.out.println("Fetch size of ResultSet: " + resultSet.getFetchSize());
		metaData = resultSet.getMetaData();
		columnCount = metaData.getColumnCount();
		typeNames = new String[columnCount];
		headers = new String[columnCount];
		columnTypes = new Class<?>[columnCount];
		for (int i = 0; i < columnCount; i++) {
			typeNames[i] = metaData.getColumnTypeName(i + 1);
			headers[i] = metaData.getColumnLabel(i + 1);
			columnTypes[i] = getDataType(typeNames[i]);
		}
		setColumnIdentifiers(headers);	
		// Load in the values
		int currentColumn;
		int row = 0;
		while (resultSet.next()) {
			addRow(new Object[columnCount]);
			for (currentColumn = 0; currentColumn < columnCount; currentColumn++) {
				setValueAt(getValueInClass(resultSet, currentColumn), row, currentColumn);
			}
			row++;
		}
		addTableModelListener(new SQLTableModelListener(this));
	}

	public SQLTableModel(String tableName, ResultSet resultSet) throws SQLException {
		this(resultSet);
		setTableName(tableName);
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return columnTypes[columnIndex];
	}

	@Override
	public String getColumnName(int column) {
		return headers[column];
	}

	private Object getValueInClass(ResultSet resultSet, int i) {
		try {
			if (columnTypes[i] == Integer.class)
				return resultSet.getInt(i + 1);
			if (columnTypes[i] == Double.class)
				return resultSet.getDouble(i + 1);
			if (columnTypes[i] == String.class)
				return resultSet.getString(i + 1);
			if (columnTypes[i] == Date.class)
				resultSet.getDate(i + 1);
			if (columnTypes[i] == Boolean.class)
				resultSet.getBoolean(i + 1);
			return resultSet.getObject(i + 1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	private Class<?> getDataType(String type) {
		if (type.equals("INTEGER") || type.contains("INT"))
			return Integer.class;
		if (type.contains("CHAR") || type.contains("TEXT") || type.contains("CLOB"))
			return String.class;
		if (type.equals("REAL") || type.contains("DOUBLE") || type.equals("FLOAT"))
			return Double.class;
		if (type.equals("DATE") || type.equals("DATETIME"))
			return Date.class;
		if (type.equals("BOOLEAN"))
			return Boolean.class;
		return Object.class;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
}
