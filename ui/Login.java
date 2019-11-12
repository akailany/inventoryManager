package inventoryManager.ui;

import java.awt.Color;
import java.awt.FlowLayout;
/*
   The login page, it can be used for different users, if the user or Administrator login there will have the different pages after they log in.
 
   For this log-in System we will call the other functions. We have to check the user name and password, and if it login sucessful it should open the other pages.
*/
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import inventoryManager.ProjectDriver;
import inventoryManager.auth.AccountType;
import inventoryManager.logging.AuditAction;

@SuppressWarnings("serial")
public class Login extends JFrame implements ActionListener {
    // Declare the variables
    private JTextField jUsernameField = null;
    private JPasswordField jPasswordField = null;

    public Login() {
	// Set up the Bottoms
	JButton jButtonLogin = new JButton("Log-in");
	JButton jButtonReset = new JButton("Reset");
	// Set the Monitor
	jButtonLogin.addActionListener(this);
	jButtonReset.addActionListener(this);
	JPanel jPanelUsername = new JPanel();
	JPanel jPanelPassword = new JPanel();
	JPanel jPanelButtons = new JPanel();
	JLabel jLabelUsername = new JLabel("User  Name:");
	JLabel jLabelPassword = new JLabel("Password  :");
	jUsernameField = new JTextField(10);
	jPasswordField = new JPasswordField(10);
	jPanelUsername.setBackground(Color.WHITE);
	jPanelPassword.setBackground(Color.WHITE);
	// add it to Panel
	jPanelUsername.add(jLabelUsername);
	jPanelUsername.add(jUsernameField);
	jPanelPassword.add(jLabelPassword);
	jPanelPassword.add(jPasswordField);
	jPanelButtons.add(jButtonLogin);
	jPanelButtons.add(jButtonReset);
	// Add it into Frame
	this.add(jPanelUsername);
	this.add(jPanelPassword);
	this.add(jPanelButtons);
	// Set the format of interface
	try {
	    ImageIcon img = new ImageIcon(getClass().getResourceAsStream("login.jpg").readAllBytes());
	    JLabel background = new JLabel("", img, JLabel.CENTER);
	    this.add(background);
	    background.setBackground(Color.WHITE);
	} catch (Exception e) {
	    System.err.println("Failed to load background image for login screen");
	    e.printStackTrace();
	}
	setLayout(new FlowLayout());
	// Set the title
	this.setTitle("I.M.S");
	// Set the size of interface
	this.setSize(450, 250);
	// Set the location of the interface
	this.setLocation(750, 400);
	// Make sure when the interface close the JVM is exit too
	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	// Show the Interface
	this.setVisible(true);
	this.setResizable(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
	System.out.println(e.getActionCommand());
	if (e.getActionCommand().equals("Log-in")) {
	    System.out.println("Command was to log in");
	    String username = jUsernameField.getText();
	    String password = new String(jPasswordField.getPassword());
	    AccountType type = ProjectDriver.authenticator.login(username, password);
	    System.out.println("Account type is: " + type);
	    setVisible(false);
	    if (type == AccountType.INVALID) {
		JOptionPane.showMessageDialog(null, "User or password is not correct! \nPlease enter again", "Prompt message", JOptionPane.ERROR_MESSAGE);
		clear();
		setVisible(true);
		return;
	    }
	    ProjectDriver.auditor.setUser(username);
	    ProjectDriver.auditor.logAction(AuditAction.LOGIN);
	    new DynamicUI(username, type);
	} else if (e.getActionCommand().equals("Reset"))// if click the Reset
	{
	    clear();
	}
    }

    // empty the user name and password box
    public void clear() {
	jUsernameField.setText(null);
	jPasswordField.setText(null);
    }
}
