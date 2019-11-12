package inventoryManager.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class SQLiteDatabase {
	private final List<String> tableNames;
	private final String DATABASE_FILE;
	private Connection connection;
	private static SQLiteDatabase instance;

	public SQLiteDatabase() {
		DATABASE_FILE = "jdbc:sqlite:IMS.db";
		tableNames = new LinkedList<String>();
		// Make sure we are ready for user
		openConnection();
		loadTablesFromCurrentDB();
	}

	public static SQLiteDatabase getInstance() {
		if (instance == null)
			instance = new SQLiteDatabase();
		return instance;
	}

	/**
	 * Check if this instance currently has a connection to the database
	 * 
	 * @return true if an active connection exists
	 */
	public boolean hasConnection() {
		try {
			if (connection == null)
				return false;
			if (connection.isClosed())
				return false;
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	/**
	 * Attempt to establish a connection to the database
	 * 
	 * @return true a connection exists or is created
	 */
	public boolean openConnection() {
		if (hasConnection())
			return true;
		try {
			Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection(DATABASE_FILE);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	/**
	 * Have the database update the names of available tables. This should be done
	 * each time a table is created or removed
	 * 
	 * @return the number of table names loaded
	 */
	public int loadTablesFromCurrentDB() {
		try {
			// Load in the names for all of the SQL Tables
			String tableQuery = new SQLiteQueryBuilder("sqlite_master").column("name").where("type='table'").toString();
			ResultSet set = executeQuery(tableQuery);
			tableNames.clear();
			while (set.next())
				tableNames.add(set.getString("name"));
			System.out.println("Loaded " + tableNames.size() + " table(s)");
			for (String s : tableNames)
				System.out.println("\t- " + s);
		} catch (Exception exc) {
			return -1;
		}
		return tableNames.size();
	}

	public boolean tableExists(String name, boolean updateList) {
		if (updateList)
			loadTablesFromCurrentDB();
		return tableNames.contains(name);
	}

	public boolean tableExists(String name) {
		return tableExists(name, false);
	}

	/**
	 * Send a query statement to the database
	 * 
	 * @param query the statement to the sent
	 * @return the ResultSet as returned by the JDBC driver
	 */
	public ResultSet executeQuery(String query) {
		if (!hasConnection())
			throw new IllegalStateException("Cannot execute query! We do not have a database connection!");
		try {
			System.out.println("Executing query: " + query);
			ResultSet set = connection.createStatement().executeQuery(query);
			return set;
		} catch (Exception exc) {
			System.err.printf("An error occured in executing query:\n\t%s\n", query);
			exc.printStackTrace();
		}
		return null;
	}

	/**
	 * Send a statement to the database
	 * 
	 * @param statement the statement to the sent
	 * @return true if the first result is a ResultSet object; false if it is an
	 *         update count or there are no results or an error occurs
	 */
	public boolean bExecuteStatement(String statement) {
		if (!hasConnection())
			throw new IllegalStateException("Cannot execute statement! We do not have a database connection!");
		try {
			System.out.println("(b) Executing statement: " + statement);
			boolean set = connection.createStatement().execute(statement);
			return set;
		} catch (Exception exc) {
			System.err.printf("An error occured in executing statement:\n\t%s\n", statement);
			exc.printStackTrace();
		}
		return false;
	}

	/**
	 * Send a statement to the database
	 * 
	 * @param statement the statement to the sent
	 * @return the Statement used to execute
	 */
	public Statement sExecuteStatement(String statement) {
		if (!hasConnection())
			throw new IllegalStateException("Cannot execute statement! We do not have a database connection!");
		try {
			System.out.println("(s) Executing statement: " + statement);
			Statement set = connection.createStatement();
			set.execute(statement);
			return set;
		} catch (Exception exc) {
			System.err.printf("An error occured in executing statement:\n\t%s\n", statement);
			exc.printStackTrace();
		}
		return null;
	}

	public List<String> getTableNames() {
		return Collections.unmodifiableList(tableNames);
	}

}
