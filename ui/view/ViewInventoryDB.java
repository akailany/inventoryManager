package inventoryManager.ui.view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import inventoryManager.db.SQLiteDatabase;
import inventoryManager.db.SQLiteQueryBuilder;
import inventoryManager.tables.SQLTableModel;

@SuppressWarnings("serial")
public class ViewInventoryDB extends View {
	private String tableName;
	private SQLiteDatabase database;
	private DefaultTableModel tableModel;
	// File Buttons
	private JMenuItem buttonConnectToDatabase;
	private JMenuItem buttonCreateTable;
	private JMenuItem buttonLoadTable;
	private JMenuItem buttonDeleteTable;
	// Table Buttons
	private JMenuItem buttonRemoveRow;
	private JMenuItem buttonAddRow;

	public ViewInventoryDB() {
		super("Database Viewer");
		createButtons();
		createPanels();
		createWindow();
	}

	public void addColumn(String title) {
		addColumn(title, null);
	}

	public void addColumn(String title, Object defaultValue) {
		if (defaultValue != null) {
			Vector<Object> vector = new Vector<>(tableModel.getRowCount());
			for (int i = 0; i < tableModel.getRowCount(); i++)
				vector.set(i, defaultValue);
		} else
			tableModel.addColumn(title);
	}

	/**
	 * Creates all of the buttons that this class needs to function properly and
	 * assigns the appropriate action listeners
	 */
	private void createButtons() {
		buttonCreateTable = new JMenuItem("Create Table");
		buttonCreateTable.addActionListener(actionEvent -> onButtonCreateTable(actionEvent));
		buttonConnectToDatabase = new JMenuItem("Connect to database");
		buttonConnectToDatabase.addActionListener(actionEvent -> onButtonConnectToDatabase(actionEvent));
		buttonLoadTable = new JMenuItem("Load Table");
		buttonLoadTable.addActionListener(actionEvent -> onButtonLoadTable(actionEvent));
		buttonDeleteTable = new JMenuItem("Delete Table");
		buttonDeleteTable.addActionListener(actionEvent -> onButtonDeleteTable(actionEvent));
		buttonAddRow = new JMenuItem("Add Row");
		buttonAddRow.addActionListener(actionEvent -> onButtonCreateRow(actionEvent));
		buttonRemoveRow = new JMenuItem("Remove Row");
		buttonRemoveRow.addActionListener(actionEvent -> onButtonRemoveRow(actionEvent));
	}

	private void onButtonCreateRow(ActionEvent actionEvent) {
		tableModel.addRow(new String[tableModel.getColumnCount()]);
	}

	private void onButtonRemoveRow(ActionEvent actionEvent) {
		// TODO Unused method stub
		JOptionPane.showMessageDialog(null, "Sorry for the inconvenience", "Not added yet", JOptionPane.ERROR_MESSAGE);
	}

	private void onButtonDeleteTable(ActionEvent actionEvent) {
		// TODO Unused method stub
		JOptionPane.showMessageDialog(null, "Sorry for the inconvenience", "Not added yet", JOptionPane.ERROR_MESSAGE);
	}

	private void onButtonLoadTable(ActionEvent actionEvent) {
		// TODO Unused method stub
		JOptionPane.showMessageDialog(null, "Sorry for the inconvenience", "Not added yet", JOptionPane.ERROR_MESSAGE);
	}

	private void onButtonConnectToDatabase(ActionEvent actionEvent) {
		// TODO Allow user to choose table
		List<String> tables = SQLiteDatabase.getInstance().getTableNames();
		JFrame popup = new JFrame("Select your table");
		JComboBox<String> comboBox = new JComboBox<>(new Vector<String>(tables));
		popup.add(comboBox, BorderLayout.CENTER);

		JButton select = new JButton("Select table");
		select.addActionListener(l -> {
			tableName = comboBox.getSelectedItem().toString();
			popup.dispose();
			loadTable(tableName);
		});
		popup.add(select, BorderLayout.PAGE_END);
		popup.pack();
		popup.setVisible(true);
		popup.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}

	private void loadTable(String name) {
		database = SQLiteDatabase.getInstance();
		try {
			tableModel = new SQLTableModel(tableName,
					database.executeQuery(new SQLiteQueryBuilder(tableName).toString()));
		} catch (SQLException e) {
			System.out.println("An SQLException occured in creating the SQLTableModel");
			e.printStackTrace();
		}
		refreshTableView();
	}

	private boolean refreshTableView() {
		System.out.println("Refreshing table view");
		getContentPane().removeAll();
		if (tableModel == null) {
			System.out.println("TableModel is null");
			return false;
		}
		JTable jTable = new JTable(tableModel);
		jTable.setAutoCreateRowSorter(true);
		jTable.setRowSelectionAllowed(false);
		JScrollPane pane = new JScrollPane(jTable, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		getContentPane().add(pane, BorderLayout.CENTER);
		pane.setVisible(true);
		pack();
		return true;
	}

	private void onButtonCreateTable(ActionEvent actionEvent) {
		// TODO Unused method stub
		JOptionPane.showMessageDialog(null, "Sorry for the inconvenience", "Not added yet", JOptionPane.ERROR_MESSAGE);
	}

	/**
	 * Take the existing buttons and other JComponents and adds them to the frame
	 */
	private void createPanels() {
		getContentPane().removeAll();
		JMenuBar menuBar = new JMenuBar();
		JMenu menu = new JMenu("File");
		menu.add(buttonCreateTable);
		menu.add(buttonLoadTable);
		menu.add(buttonConnectToDatabase);
		menu.add(buttonDeleteTable);
		menuBar.add(menu);
		setJMenuBar(menuBar);
	}

	/**
	 * Once everything has been added to the frame, this creates the window
	 */
	private void createWindow() {
		pack();
		setResizable(true);
		setMaximizable(true);
		setIconifiable(true);
		setClosable(true);
		setVisible(true);
		setActive(true);
	}

	@Override
	public void dispose() {
		// TODO Unused method stub
		System.out.println("Dispose called");
		super.dispose();
	}
}
