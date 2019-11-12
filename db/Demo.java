package inventoryManager.db;

import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Enumeration;

public class Demo {
	public static void main(String[] args) {
		Enumeration<Driver> drivers = DriverManager.getDrivers();
		System.out.println("Currently available drivers: ");
		while (drivers.hasMoreElements()) {
			Driver d = drivers.nextElement();
			System.out.println(d.toString());
		}
		// TODO Auto-generated method stub
		SQLiteDB test = new SQLiteDB();
		// When you call displayProducts() the return value is ResultSet
		ResultSet rs;
		try {
			rs = test.displayProducts();
			test.addProduct("name", "type", "num");
			test.addProduct("name", "type", "num");
			test.addProduct("name", "type", "num");
			test.addProduct("name", "type", "num");
			System.out.println(rs.getFetchSize());
			while (rs.next()) {
				System.out.println(rs.getInt("id") + " " + rs.getString("pname") + " " + rs.getString("ptype") + " "
						+ rs.getString("pnum"));
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}// end main
}// end class
