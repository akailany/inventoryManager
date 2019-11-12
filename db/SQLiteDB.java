package inventoryManager.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLiteDB {
    private static Connection con;
    private static boolean hasData = false;

    public ResultSet displayProducts() throws ClassNotFoundException, SQLException {
	if (con == null) {
	    getConnection();
	}
	Statement state = con.createStatement();
	ResultSet res = state.executeQuery("SELECT * FROM Inventory");
	return res;
    }// end displayUsers()

    private void getConnection() throws ClassNotFoundException, SQLException {
	Class.forName("org.sqlite.JDBC");
	con = DriverManager.getConnection("jdbc:sqlite:IMS.db");
	initialise();
    }// end getConnection()

    private void initialise() throws SQLException {
	if (!hasData) {
	    hasData = true;
	    Statement state = con.createStatement();
	    //ResultSet res = state.executeQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='Inventory'");
	    // code inside this If statement will only be performed once to ensure that we
	    // have a data base and table and I entered a couple of products for starters
	    
		//state = con.createStatement();
		try {state.executeQuery("DROP TABLE Inventory;");
		System.out.println("Deleting table");
		}catch(Exception exc) {}
	    
		System.out.println("Building the Inventory table with prepopulated values.");
		// need to build the table
		Statement state2 = con.createStatement();
		boolean b = state2.execute("CREATE TABLE Inventory ('id' integer, 'pname' varchar (60), 'ptype' varchar(60), 'pnum' varchar(60), primary key(id));");
		System.out.println("Executed: " + b);
		// inserting sample data for testing --> delete after ensuring everything works
		PreparedStatement prep = con.prepareStatement("INSERT INTO Inventory values(1, ?,?,?);");
		// No need to worry about column one as it is key
		prep.setString(1, "Milk");
		prep.setString(2, "Dairy");
		prep.setString(3, "00001");
		prep.execute();
		PreparedStatement prep2 = con.prepareStatement("INSERT INTO Inventory values(2, ?,?,?);");
		prep2.setString(1, "Pistachio");
		prep2.setString(2, "Nuts");
		prep2.setString(3, "00002");
		prep2.execute();
	    
	}
    }// end initialize()

    public void addProduct(String pname, String ptype, String pnum) throws ClassNotFoundException, SQLException {
	if (con == null) {
	    getConnection();
	}
	PreparedStatement prep = con.prepareStatement("INSERT INTO Inventory values(?, ?,?,?);");
	prep.setInt(1, (int) (Math.random() * 100000));
	prep.setString(2, pname);
	prep.setString(3, ptype);
	prep.setString(4, pnum);
	prep.execute();
   System.out.println("Records created successfully");
    }// end addProduct()
    
    
    //this method will delete an existing row.
    public void delete (String pname, String ptype, String pnum) throws ClassNotFoundException, SQLException {
	if (con == null) {
	    getConnection();
	}
   
   	PreparedStatement prep = con.prepareStatement("DELETE from Inventory values(?, ?,?,?);");
      prep.setInt(1, (int) (Math.random() * 100000));
	   prep.setString(2, pname);
	   prep.setString(3, ptype);
	   prep.setString(4, pnum);
	   prep.execute();
      System.out.println("Operation delete done successfully");

   }//end delete
   
   
   //this method will update an existing row with the passed values
   public void update (String pname, String ptype, String pnum) throws ClassNotFoundException, SQLException {
	if (con == null) {
	    getConnection();
	}
   
   	PreparedStatement prep = con.prepareStatement("UPDATE Inventory values(?, ?,?,?);");
      prep.setInt(1, (int) (Math.random() * 100000));
	   prep.setString(2, pname);
	   prep.setString(3, ptype);
	   prep.setString(4, pnum);
	   prep.execute();
      System.out.println("Operation update done successfully");
   }//end delete
   
   
   public void createDB (String table) throws ClassNotFoundException, SQLException {
   
   if (con == null) {
	    getConnection();
	}
         String create = "CREATE TABLE ";
         String tblName = table;
         String Input = "('id' integer, 'pname' varchar (60), 'ptype' varchar(60), 'pnum' varchar(60), primary key(id));";
         String Statement = create + tblName + Input;
   	     PreparedStatement prep = con.prepareStatement(Statement);
         prep.execute();
         System.out.println("Table " + tblName + " was created successfully");

   
   }//end createDB

}// end class
