package inventoryManager.ui.view;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;

import inventoryManager.ProjectDriver;
import inventoryManager.auth.AccountData;
import inventoryManager.auth.AccountType;
import inventoryManager.logging.AuditAction;

@SuppressWarnings("serial")
public class ViewUsers extends View {
    private JButton buttonAddUser;
    private JButton buttonRemoveUser;
    private JTextField fieldUsername;
    private JPasswordField fieldPassword;
    private JComboBox<AccountType> boxAccountType;
    private List<AccountData> listAccountData;
    private JList<AccountData> jlistAccounts;
    private int selectedIndex;
    private JPanel contextPanel;

    public ViewUsers() {
	super("Edit Users");
	createButtons();
	createPanels();
	createWindow();
    }

    /**
     * Creates all of the buttons that this class needs to function properly and
     * assigns the appropriate action listeners
     */
    private void createButtons() {
	listAccountData = ProjectDriver.authenticator.getAllAccounts("Admin", "Admin");
	jlistAccounts = new JList<AccountData>(new Vector<>(listAccountData));
	jlistAccounts.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	jlistAccounts.addListSelectionListener(listSelectionevent -> onListSelection(listSelectionevent));
	buttonAddUser = new JButton("Add User / Edit Password");
	buttonAddUser.setToolTipText("Add a user with the specified information, or edit the password of an existing one");
	buttonAddUser.addActionListener(actionEvent -> onAddUserButtonClick(actionEvent));
	buttonRemoveUser = new JButton("Remove Selected User");
	buttonRemoveUser.setToolTipText("Remove the currently selected user");
	buttonRemoveUser.addActionListener(actionEvent -> onRemoveUserButtonClick(actionEvent));
	fieldUsername = new JTextField();
	fieldPassword = new JPasswordField();
	boxAccountType = new JComboBox<AccountType>(AccountType.values());
	boxAccountType.removeItemAt(AccountType.INVALID.ordinal());
	boxAccountType.setEditable(false);
    }

    /**
     * Take the existing buttons and other JComponents and adds them to the frame
     */
    private void createPanels() {
	Container contentPane = getContentPane();
	contentPane.add(jlistAccounts, BorderLayout.CENTER);
	contextPanel = new JPanel(new GridLayout(0, 1), false);
	contextPanel.add(new JLabel("Username"));
	contextPanel.add(fieldUsername);
	contextPanel.add(new JLabel("Password"));
	contextPanel.add(fieldPassword);
	contextPanel.add(new JLabel("Account Type"));
	contextPanel.add(boxAccountType);
	contextPanel.add(buttonAddUser);
	contextPanel.add(buttonRemoveUser);
	contentPane.add(contextPanel, BorderLayout.EAST);
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
	setMinimumSize(new Dimension(300, 200));
	setActive(true);
    }

    // Events
    private void onAddUserButtonClick(ActionEvent actionEvent) {
	boolean hasAccount = false;
	String name = fieldUsername.getText();
	String password = new String(fieldPassword.getPassword());
	AccountType type = (AccountType) boxAccountType.getSelectedItem();
	AccountData accountData = null;
	for (AccountData data : listAccountData) {
	    if (data.getAccountName().equals(name)) {
		accountData = data;
		accountData.setAccountPassword(password);
		accountData.setAccountType(type);
		hasAccount = true;
		break;
	    }
	}
	if (accountData == null)
	    accountData = new AccountData(name, password, type);
	ProjectDriver.auditor.logAction(hasAccount ? AuditAction.EDIT_ACCOUNT : AuditAction.CREATE_ACCOUNT, getClass().getSimpleName(), hasAccount ? String.format("Changed password of '%s' to '%s'", name, password) : String.format("Created account with name: %s", name));
	boolean accountCreated = ProjectDriver.authenticator.createAccount(accountData.getAccountName(), accountData.getAccountPassword(), accountData.getAccountType());
	String title = String.format("Action %s", accountCreated ? "Successful" : "Failed");
	String message = String.format("Account %s %s", accountCreated ? "was" : "was not", hasAccount ? "modified" : "created");
	JOptionPane.showMessageDialog(null, message, title, JOptionPane.INFORMATION_MESSAGE);
	if (!hasAccount)
	    listAccountData.add(accountData);
	jlistAccounts.setListData(new Vector<>(listAccountData));
	jlistAccounts.setSelectedIndex(hasAccount ? selectedIndex : (listAccountData.size() - 1));
    }

    private void onRemoveUserButtonClick(ActionEvent actionEvent) {
	AccountData accountData = jlistAccounts.getSelectedValue();
	boolean accountRemoved = ProjectDriver.authenticator.removeAccount(accountData.getAccountName(), accountData.getAccountPassword(), accountData.getAccountType());
	ProjectDriver.auditor.logAction(AuditAction.DELETE_ACCOUNT, getClass().getSimpleName(), String.format("Deleted account with name: %s", accountData.getAccountName()));
	String title = String.format("Action %s", accountRemoved ? "Successful" : "Failed");
	String message = String.format("Account %s removed", accountRemoved ? "was" : "was not");
	JOptionPane.showMessageDialog(null, message, title, JOptionPane.INFORMATION_MESSAGE);
	if (!accountRemoved)
	    return;
	listAccountData.remove(accountData);
	jlistAccounts.setListData(new Vector<>(listAccountData));
	jlistAccounts.setSelectedIndex(0);
	selectedIndex = 0;
    }

    private void onListSelection(ListSelectionEvent listSelectionEvent) {
	this.selectedIndex = listSelectionEvent.getFirstIndex();
	AccountData data = jlistAccounts.getSelectedValue();
	if (data != null) {
	    fieldUsername.setText(data.getAccountName());
	    fieldPassword.setText(data.getAccountPassword());
	    boxAccountType.setSelectedIndex(data.getAccountType().ordinal());
	} else {
	    fieldUsername.setText(null);
	    fieldPassword.setText(null);
	    boxAccountType.setSelectedIndex(0);
	}
    }
}
