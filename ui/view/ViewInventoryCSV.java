package inventoryManager.ui.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.Vector;

import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import inventoryManager.tables.CSVTableStorage;

@SuppressWarnings("serial")
public class ViewInventoryCSV extends View {
	private File csvFile;
	private JTable table;
	private JScrollPane scrollPane;
	// File Buttons
	private JMenuItem buttonCreate;
	private JMenuItem buttonSave;
	private JMenuItem buttonSaveAs;
	private JMenuItem buttonOpen;
	// Table Buttons
	private JMenuItem buttonRemoveRow;
	private JMenuItem buttonAddRow;

	public ViewInventoryCSV() {
		super("Edit CSVs");
		createButtons();
		createPanels();
		createWindow();
	}

	private void loadCSVTable() {
		TableModel tableModel = CSVTableStorage.getInstance().loadTable(csvFile);
		if (tableModel == null)
			System.err.println("Failed to load Table from CSV: " + csvFile);
		if (table != null)
			table.setModel(tableModel);
		else {
			table = new JTable(tableModel);
			table.setAutoCreateRowSorter(true);
			table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
			table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		}
		if (scrollPane != null)
			scrollPane.setViewportView(table);
		else {
			scrollPane = new JScrollPane(table);
			scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
			scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		}
		getContentPane().removeAll();
		getContentPane().add(scrollPane, BorderLayout.CENTER);
		pack();
	}

	public void addColumn(String title) {
		addColumn(title, null);
	}

	public void addColumn(String title, Object defaultValue) {
		DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
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
		buttonCreate = new JMenuItem("New");
		buttonCreate.addActionListener(actionEvent -> onButtonCreateTable(actionEvent));
		buttonSave = new JMenuItem("Save");
		buttonSave.addActionListener(actionEvent -> onButtonSaveTable(actionEvent));
		buttonSaveAs = new JMenuItem("Save As");
		buttonSaveAs.addActionListener(actionEvent -> onButtonSaveAsTable(actionEvent));
		buttonOpen = new JMenuItem("Load");
		buttonOpen.addActionListener(actionEvent -> onButtonLoadCSV(actionEvent));
		buttonAddRow = new JMenuItem("Add Row");
		buttonAddRow.addActionListener(actionEvent -> onButtonCreateRow(actionEvent));
		buttonRemoveRow = new JMenuItem("Remove Row");
		buttonRemoveRow.addActionListener(actionEvent -> onButtonRemoveRow(actionEvent));
	}

	private void onButtonSaveAsTable(ActionEvent actionEvent) {
		// TODO Unused method stub
		JOptionPane.showMessageDialog(null, "Sorry for the inconvenience", "Not added yet", JOptionPane.ERROR_MESSAGE);
	}

	private void onButtonCreateRow(ActionEvent actionEvent) {
		((DefaultTableModel) table.getModel()).addRow(new String[table.getModel().getColumnCount()]);
	}

	private void onButtonRemoveRow(ActionEvent actionEvent) {
		// TODO Unused method stub
		JOptionPane.showMessageDialog(null, "Sorry for the inconvenience", "Not added yet", JOptionPane.ERROR_MESSAGE);
	}

	private void onButtonLoadCSV(ActionEvent actionEvent) {
		JFileChooser fileChooser = new JFileChooser(System.getProperty("user.dir"));
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fileChooser.setFileFilter(new FileNameExtensionFilter("CSV Files", "csv"));
		int value = fileChooser.showOpenDialog(null);
		if (value != JFileChooser.APPROVE_OPTION)
			return;
		csvFile = fileChooser.getSelectedFile();
		loadCSVTable();
	}

	private void onButtonSaveTable(ActionEvent actionEvent) {
		boolean saved = CSVTableStorage.getInstance().saveTable(csvFile, (DefaultTableModel) table.getModel());
		JOptionPane.showMessageDialog(null, saved ? "File saved!" : "File failed to save", "Saving " + csvFile,
				JOptionPane.INFORMATION_MESSAGE);
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
		menu.add(buttonCreate);
		menu.add(buttonOpen);
		menu.add(buttonSave);
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
		setSize(new Dimension(Math.max(getWidth(), 100), Math.max(getHeight(), 100)));
	}

	@Override
	public void dispose() {
		System.out.println("Dispose called");
		super.dispose();
	}
}
