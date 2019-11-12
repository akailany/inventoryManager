package inventoryManager.tables;

import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

import javax.swing.table.DefaultTableModel;

public class CSVTableStorage {
	private File folder;
	private static CSVTableStorage instance;

	private CSVTableStorage() {
		folder = new File(System.getProperty("user.dir"), "_tables");
		if (!folder.exists())
			folder.mkdirs();
	}

	public static CSVTableStorage getInstance() {
		if (instance == null)
			instance = new CSVTableStorage();
		return instance;
	}

	public boolean saveTable(File csvFile, DefaultTableModel table) {
		if (table == null) {
			System.err.printf("Failed to save table: %s [Table is null] \n", csvFile);
			return false;
		}
		String[] currentRow = new String[table.getColumnCount()];
		try {
			csvFile.createNewFile();
			FileWriter writer = new FileWriter(csvFile, false);
			// Get all the headers
			int i = 0;
			for (; i < currentRow.length;)
				currentRow[i++] = table.getColumnName(i);
			writer.write(String.join(",", currentRow));
			writer.write("\n");
			for (int row = 0; row < table.getRowCount(); row++) {
				for (i = 0; i < table.getColumnCount(); i++) {
					currentRow[i] = table.getValueAt(row, i).toString();
					if (currentRow[i] == null)
						currentRow[i] = "";
				}
				writer.write(String.join(",", currentRow));
				writer.write("\n");
			}
			writer.flush();
			writer.close();
			return true;
		} catch (Exception exc) {
			exc.printStackTrace();
			return false;
		}
	}

	public DefaultTableModel loadTable(File csvFile) {
		if (!csvFile.exists()) {
			System.err.println("File not found: " + csvFile.getPath());
			return null;
		}
		System.out.println("File found: " + csvFile.getPath());
		try {
			Scanner reader = new Scanner(csvFile);
			if (!reader.hasNextLine()) {
				System.err.println("File is empty");
				reader.close();
				return null;
			}
			String[] columnNames = reader.nextLine().split(",");
			System.out.println("File has " + columnNames.length + " column headers: " + String.join(", ", columnNames));
			DataMatchedTableModel table = new DataMatchedTableModel(columnNames, 0);
			while (reader.hasNext())
				table.addRow(reader.nextLine().split(","));
			reader.close();
			System.out.println("Model created of size " + table.getRowCount() + " by " + table.getColumnCount());
			table.verifyDataTypes();
			System.out.print("Data types determined to be: ");
			for (int i = 0; i < table.getColumnCount(); i++) {
				System.out.print(table.getColumnClass(i).getSimpleName() + " ");
			}
			System.out.println();
			return table;
		} catch (Exception exc) {
			exc.printStackTrace();
			return null;
		}
	}
}
