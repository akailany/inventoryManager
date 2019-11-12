package inventoryManager.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.border.LineBorder;

import team6project.ProjectDriver;
import team6project.auth.AccountType;
import team6project.logging.AuditAction;
import team6project.ui.view.Aggregate;
import team6project.ui.view.ViewInventoryCSV;
import team6project.ui.view.ViewInventoryDB;
import team6project.ui.view.ViewUsers;

@SuppressWarnings("serial")
/**
 * The goal of the DynamicUI object is to have an interface that can change with
 * the role of the user. <br>
 * By having a UI that does this, we can change how the program feels without
 * needed to do it on a per-interface basis. <br>
 * This UI uses the JDesktop as it's main component and allows for the user to
 * manage more than one view at a time for multitasking.<br>
 * This class is set up to create two menu options. One for normal users, and
 * one for administrators.
 */
public class DynamicUI extends JFrame {
	private AccountType type;
	// User Components
	private List<JComponent> userComponents;
	private JMenuItem jUserButtonLogout;
	private JMenuItem jUserOpenCSV;
	private JMenuItem jUserConnectToDB;
	// Admin JMenuItem
	private List<JComponent> adminComponents;
	private JMenuItem jAdminButtonUsers;
	private JMenuItem jAdminButtonAggregate;
	// The workspace
	private int workspaceMinSizeWidth = 1000;
	private int workspaceMinSizeHeight = 700;
	private JDesktopPane workingDesktop;
	private JMenuBar jMenuBar;
	// View tracking

	public DynamicUI(String user, AccountType type) {
		this.type = type;
		ProjectDriver.auditor.setUser(user);
		ProjectDriver.auditor.logAction(AuditAction.VIEW_OPEN, getClass().getSimpleName());
		createButtons();
		createPanels();
		createWindow();
	}

	private void createButtons() {
		userComponents = new LinkedList<JComponent>();
		jUserOpenCSV = new JMenuItem("Open CSV");
		jUserOpenCSV.addActionListener(actionEvent -> onClickOpenCSV(actionEvent));
		userComponents.add(jUserOpenCSV);
		jUserConnectToDB = new JMenuItem("Connect to SQLite DB");
		jUserConnectToDB.addActionListener(actionEvent -> onClickConnectDB(actionEvent));
		userComponents.add(jUserConnectToDB);
		jUserButtonLogout = new JMenuItem("Log Out");
		jUserButtonLogout.setBorder(new LineBorder(Color.RED));
		jUserButtonLogout.addActionListener(actionEvent -> onClickLogout(actionEvent));
		userComponents.add(jUserButtonLogout);
		// Add even more if user is an administrator
		// otherwise, do not even instantiate the fields
		if (type != AccountType.ADMIN)
			return;
		adminComponents = new LinkedList<JComponent>();
		jAdminButtonAggregate = new JMenuItem("Aggregate");
		jAdminButtonAggregate.addActionListener(actionEvent -> onClickAggregate(actionEvent));
		jAdminButtonUsers = new JMenuItem("Users");
		jAdminButtonUsers.addActionListener(actionEvent -> onClickUsers(actionEvent));
		adminComponents.add(jAdminButtonAggregate);
		adminComponents.add(jAdminButtonUsers);
	}

	private void onClickConnectDB(ActionEvent actionEvent) {
		if (!ViewInventoryDB.isActive())
			workingDesktop.add(new ViewInventoryDB());
	}

	private void onClickOpenCSV(ActionEvent actionEvent) {
		if (!ViewInventoryCSV.isActive())
			workingDesktop.add(new ViewInventoryCSV());
	}

	private void onClickUsers(ActionEvent actionEvent) {
		if (!ViewUsers.isActive())
			workingDesktop.add(new ViewUsers());
	}

	private void onClickAggregate(ActionEvent actionEvent) {
		if (!Aggregate.isActive())
			workingDesktop.add(new Aggregate());
	}

	private void createPanels() {
		jMenuBar = new JMenuBar();
		JMenu userMenu = new JMenu("User");
		if (!userComponents.isEmpty()) {
			for (JComponent comp : userComponents) {
				userMenu.add(comp);
			}
		}
		jMenuBar.add(userMenu);
		if (type == AccountType.ADMIN && !adminComponents.isEmpty()) {
			JMenu adminMenu = new JMenu("Admin");
			if (!userComponents.isEmpty()) {
				for (JComponent comp : adminComponents) {
					adminMenu.add(comp);
				}
			}
			jMenuBar.add(adminMenu);
		}
		jMenuBar.setBackground(Color.LIGHT_GRAY);
		Container contentPane = getContentPane();
		contentPane.removeAll();
		contentPane.setBackground(Color.WHITE);
		contentPane.setLayout(new BorderLayout(10, 5));
		setJMenuBar(jMenuBar);
		workingDesktop = new JDesktopPane();
		workingDesktop.setDoubleBuffered(true);
		workingDesktop.setMinimumSize(new Dimension(400, 300));
		workingDesktop.setBackground(Color.WHITE);
		contentPane.add(workingDesktop);
	}

	protected boolean setLookAndFeel(String lookAndFeel) {
		System.out.println("Installed LookAndFeels: ");
		for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels())
			System.out.println(info.getName());
		System.out.println("Attempting to set Look and Feel to: " + lookAndFeel);
		try {
			for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
				if (lookAndFeel.equals(info.getName())) {
					javax.swing.UIManager.setLookAndFeel(info.getClassName());
					return true;
				}
			}
		} catch (Exception ex) {
			System.err.println("Failed to set LookAndFeel to: " + lookAndFeel);
			System.err.println(ex.getMessage());
		}
		return false;
	}

	private void createWindow() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLookAndFeel("Windows");
		pack();
		jMenuBar.setMinimumSize(jMenuBar.getSize());
		jMenuBar.setMaximumSize(jMenuBar.getSize());
		setMinimumSize(new Dimension(workspaceMinSizeWidth, workspaceMinSizeHeight));
		setSize(getMinimumSize());
		setResizable(true);
		setDefaultLookAndFeelDecorated(true);
		setVisible(true);
		workingDesktop.setVisible(true);
	}

	// Events
	private void onClickLogout(ActionEvent actionEvent) {
		int value = JOptionPane.showConfirmDialog(null, "Are you sure you would like to log out?",
				"Log Out Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
		if (value != JOptionPane.YES_OPTION)
			return;
		dispose();
	}

	@Override
	public void dispose() {
		ProjectDriver.auditor.logAction(AuditAction.VIEW_CLOSE, getClass().getSimpleName());
		ProjectDriver.auditor.logAction(AuditAction.LOGOUT);
		ProjectDriver.auditor.closeAuditor();
		super.dispose();
		System.exit(0);
	}
}
