package inventoryManager.ui.view;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.Iterator;
import java.util.Map;
import java.util.WeakHashMap;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class Aggregate extends View{
	Map<String, String> MapNameType;
	private JButton ButtonSearch;			// will be used for the Search bottom
	private JTextField fieldConditionStart1;    // The first condition start at
	private JTextField fieldConditionStart2;	// The Second condition start at
	private JTextField fieldConditionStart3;	// The Third condition start at
	private JTextField fieldConditionStart4;	// The Forth condition start at
	private JTextField fieldConditionStart5;	// The Fifth condition start at
	private JTextField fieldConditionEnd1;		// The first condition end at
	private JTextField fieldConditionEnd2;		// The Second condition end at
	private JTextField fieldConditionEnd3;		// The Third condition end at
	private JTextField fieldConditionEnd4;		// The Forth condition end at
	private JTextField fieldConditionEnd5;		// The Fifth condition end at
	private JTextField fieldConditionEqual1;	// The first condition equal to
	private JTextField fieldConditionEqual2;	// The Second condition equal to
	private JTextField fieldConditionEqual3;	// The Third condition equal to
	private JTextField fieldConditionEqual4;	// The Forth condition equal to
	private JTextField fieldConditionEqual5;	// The Fifth condition equal to
	private String Name1;						// The Name for column1
	private String Name2;						// The Name for column2
	private String Name3;						// The Name for column3
	private String Name4;						// The Name for column4
	private String Name5;						// The Name for column5
	private String Type1;						// The Type for column1
	private String Type2;						// The Type for column2
	private String Type3;						// The Type for column3
	private String Type4;						// The Type for column4
	private String Type5;						// The Type for column5
	private JPanel contextPanel1;
	private JPanel contextPanel2;
	private JPanel contextPanel3;
	private JPanel contextPanel4;
	private JPanel contextPanel5;
	@SuppressWarnings("unused")
	private JPanel contextPanel6;
	@SuppressWarnings("rawtypes")
	private JComboBox jcb1;

	public Aggregate() {
		super("Aggregate");
	    // For testing
    	MapNameType = new WeakHashMap<>();
    	MapNameType.put("Name", "String");
	    MapNameType.put("Item", "Integer");
	    MapNameType.put("Price", "Integer");
	    MapNameType.put("Dates", "Integer");
	    MapNameType.put("Invertory", "Integer");
	    
	    fieldConditionStart1 = new JTextField();
	    fieldConditionStart2 = new JTextField();
	    fieldConditionStart3 = new JTextField();
	    fieldConditionStart4 = new JTextField();
	    fieldConditionStart5 = new JTextField();
	    fieldConditionEnd1 = new JTextField();
	    fieldConditionEnd2 = new JTextField();
	    fieldConditionEnd3 = new JTextField();
	    fieldConditionEnd4 = new JTextField();
	    fieldConditionEnd5 = new JTextField();
	    fieldConditionEqual1 = new JTextField();
	    fieldConditionEqual2 = new JTextField();
	    fieldConditionEqual3 = new JTextField();
	    fieldConditionEqual4 = new JTextField();
	    fieldConditionEqual5 = new JTextField();
                                	    
	    /** For this part is decide how many conditions do we have
	     *  At here each blank must have the fix parameter for it,
	     *  because we have to get what the user input in the blank  
	     */
	    String nametype = transMapToString(MapNameType);   // convert the map to string 
	    int n = MapNameType.entrySet().size();
	    String[] NameType = transStringToList(n,nametype); // convert the map to list
	    
	    DropDownList(NameType.length, NameType[0],NameType[2], NameType[4],NameType[6],NameType[8]);
	    createButtons();
	    
	    // Base on how many name we need to search the program will has how many column with search bar
		if (n == 1) {
			createPanels1(NameType[0], NameType[1]);
		}
	    else if(n == 2) {
	    	createPanels2(NameType[0], NameType[1], NameType[2], NameType[3]);
		}
	    else if(n == 3) {
	    	createPanels3(NameType[0], NameType[1], NameType[2], NameType[3], NameType[4], NameType[5]);
	    }
	    else if(n == 4) {
	    	createPanels4(NameType[0], NameType[1], NameType[2], NameType[3], NameType[4], NameType[5], NameType[6], NameType[7]);
	    }
	    else if(n == 5) {	    	
	    	createPanels5(NameType[0], NameType[1], NameType[2], NameType[3], NameType[4], NameType[5], NameType[6], NameType[7], NameType[8], NameType[9]);
		}	
	    else {
	    	warning();
	    }
	    createWindow();
	    
}
	// create the search button  
	private void createButtons() {
		ButtonSearch = new JButton("Search");
		ButtonSearch.setToolTipText("Search the result with condition");
		ButtonSearch.addActionListener(actionEvent -> onSearchButtonClick(actionEvent));
		contextPanel1 = new JPanel();
		contextPanel1.add(ButtonSearch);
		add(contextPanel1, BorderLayout.SOUTH);	
	}
	
	// create the drop down list, the drop down list is depend on how many name we have 
	// and also the condition name will change automatic
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void DropDownList(int size, String name1, String name2, String name3, String name4, String name5) {
		int size1 = size/2;
		System.out.println(size1);
		Container contentPane = getContentPane();
		contextPanel1 = new JPanel(new GridLayout(1, 0), false);
		if(size1 == 1) {
			String[] chatter = {"Condition "+name1};
			jcb1 = new JComboBox(chatter);
		}
		else if(size1 == 2) {
			String[] chatter = {"Condition "+name1,"Condition "+name2};
			jcb1 = new JComboBox(chatter);
		}
		else if(size1 == 3) {
			String[] chatter = {"Condition "+name1,"Condition "+name2,"Condition "+name3};	
			jcb1 = new JComboBox(chatter);
		}
		else if(size1 == 4) {
			String[] chatter = {"Condition "+name1,"Condition "+name2,"Condition "+name3,"Condition "+name4};
			jcb1 = new JComboBox(chatter);
		}
		else if(size1 == 5) {
			String[] chatter = {"Condition "+name1,"Condition "+name2,"Condition "+name3,"Condition "+name4,"Condition "+name5};
			jcb1 = new JComboBox(chatter);
		}
		else {
			warning();
		}
		
		contextPanel1.add(new JLabel("Condition"));
		contextPanel1.add(jcb1);
		contentPane.add(contextPanel1, BorderLayout.NORTH);
	}
	/**
	 *  Create the search condition
	 *  For this part we put the maximum search conditions is 5
	 */
	// when it only has one colunms  
	private void createPanels1(String name, String Type) {
		Name1 = name;
		Type1 = Type;
		Container contentPane = getContentPane();
		contextPanel1 = new JPanel(new GridLayout(0, 1), false);
		contextPanel1.add(new JLabel("Condition1"));
		contextPanel6 = new JPanel();
		contextPanel1.add(new JLabel(name));
		contextPanel1.add(new JLabel("From:"));
		contextPanel1.add(fieldConditionStart1);
		contextPanel1.add(new JLabel("To:"));
		contextPanel1.add(fieldConditionEnd1);
		contextPanel1.add(new JLabel("Equal:"));
		contextPanel1.add(fieldConditionEqual1);
		contentPane.add(contextPanel1, BorderLayout.CENTER);
	}
	// when it has two columns 
	private void createPanels2(String name1, String Type1, String name2, String Type2) {
		Name1 = name1;
		this.Type1 = Type1;
		Name2 = name2;
		this.Type2 = Type2;
		Container contentPane = getContentPane();
		// First layout
		contextPanel1 = new JPanel(new GridLayout(0, 1), false);
		contextPanel1.add(new JLabel("Condition1"));
		contextPanel6 = new JPanel();
		contextPanel1.add(new JLabel(name1));
		contextPanel1.add(new JLabel("From:"));
		contextPanel1.add(fieldConditionStart1);
		contextPanel1.add(new JLabel("To:"));
		contextPanel1.add(fieldConditionEnd1);
		contextPanel1.add(new JLabel("Equal:"));
		contextPanel1.add(fieldConditionEqual1);
		
		// Second layout
		contextPanel2 = new JPanel(new GridLayout(0, 1), false);
		contextPanel2.add(new JLabel("Condition2"));
		contextPanel6 = new JPanel();
		contextPanel2.add(new JLabel(name2));
		contextPanel2.add(new JLabel("From:"));
		contextPanel2.add(fieldConditionStart2);
		contextPanel2.add(new JLabel("To:"));
		contextPanel2.add(fieldConditionEnd2);
		contextPanel2.add(new JLabel("Equal:"));
		contextPanel2.add(fieldConditionEqual2);
		
		JPanel layout = new JPanel();
		layout.add(contextPanel1);
		layout.add(contextPanel2);
		contentPane.add(layout, BorderLayout.CENTER);
		
	}
	// when it has three columns 
	private void createPanels3(String name1, String Type1, String name2, String Type2, String name3, String Type3) {
		Name1 = name1;
		this.Type1 = Type1;
		Name2 = name2;
		this.Type2 = Type2;
		Name3 = name3;
		this.Type3 = Type3;
		Container contentPane = getContentPane();
		// First layout
		contextPanel1 = new JPanel(new GridLayout(0, 1), false);
		contextPanel1.add(new JLabel("Condition1"));
		contextPanel6 = new JPanel();
		contextPanel1.add(new JLabel(name1));
		contextPanel1.add(new JLabel("From:"));
		contextPanel1.add(fieldConditionStart1);
		contextPanel1.add(new JLabel("To:"));
		contextPanel1.add(fieldConditionEnd1);
		contextPanel1.add(new JLabel("Equal:"));
		contextPanel1.add(fieldConditionEqual1);
		
		// Second layout
		contextPanel2 = new JPanel(new GridLayout(0, 1), false);
		contextPanel2.add(new JLabel("Condition2"));
		contextPanel6 = new JPanel();
		contextPanel2.add(new JLabel(name2));
		contextPanel2.add(new JLabel("From:"));
		contextPanel2.add(fieldConditionStart2);
		contextPanel2.add(new JLabel("To:"));
		contextPanel2.add(fieldConditionEnd2);
		contextPanel2.add(new JLabel("Equal:"));
		contextPanel2.add(fieldConditionEqual2);
		
		// Third layout
		contextPanel3 = new JPanel(new GridLayout(0, 1), false);
		contextPanel3.add(new JLabel("Condition3"));
		contextPanel6 = new JPanel();
		contextPanel3.add(new JLabel(name3));
		contextPanel3.add(new JLabel("From:"));
		contextPanel3.add(fieldConditionStart3);
		contextPanel3.add(new JLabel("To:"));
		contextPanel3.add(fieldConditionEnd3);
		contextPanel3.add(new JLabel("Equal:"));
		contextPanel3.add(fieldConditionEqual3);
		
		JPanel layout = new JPanel();
		layout.add(contextPanel1);
		layout.add(contextPanel2);
		layout.add(contextPanel3);
		contentPane.add(layout, BorderLayout.CENTER);
	
	}
	// when it has four columns 
	private void createPanels4(String name1, String Type1, String name2, String Type2, String name3, String Type3, String name4, String Type4) {
		Name1 = name1;
		this.Type1 = Type1;
		Name2 = name2;
		this.Type2 = Type2;
		Name3 = name3;
		this.Type3 = Type3;
		Name4 = name4;
		this.Type4 = Type4;
		Container contentPane = getContentPane();
		// First layout
		contextPanel1 = new JPanel(new GridLayout(0, 1), false);
		contextPanel1.add(new JLabel("Condition1"));
		contextPanel6 = new JPanel();
		contextPanel1.add(new JLabel(name1));
		contextPanel1.add(new JLabel("From:"));
		contextPanel1.add(fieldConditionStart1);
		contextPanel1.add(new JLabel("To:"));
		contextPanel1.add(fieldConditionEnd1);
		contextPanel1.add(new JLabel("Equal:"));
		contextPanel1.add(fieldConditionEqual1);
		
		// Second layout
		contextPanel2 = new JPanel(new GridLayout(0, 1), false);
		contextPanel2.add(new JLabel("Condition2"));
		contextPanel6 = new JPanel();
		contextPanel2.add(new JLabel(name2));
		contextPanel2.add(new JLabel("From:"));
		contextPanel2.add(fieldConditionStart2);
		contextPanel2.add(new JLabel("To:"));
		contextPanel2.add(fieldConditionEnd2);
		contextPanel2.add(new JLabel("Equal:"));
		contextPanel2.add(fieldConditionEqual2);
		
		// Third layout
		contextPanel3 = new JPanel(new GridLayout(0, 1), false);
		contextPanel3.add(new JLabel("Condition3"));
		contextPanel6 = new JPanel();
		contextPanel3.add(new JLabel(name3));
		contextPanel3.add(new JLabel("From:"));
		contextPanel3.add(fieldConditionStart3);
		contextPanel3.add(new JLabel("To:"));
		contextPanel3.add(fieldConditionEnd3);
		contextPanel3.add(new JLabel("Equal:"));
		contextPanel3.add(fieldConditionEqual3);
		
		// Forth layout 
		contextPanel4 = new JPanel(new GridLayout(0, 1), false);
		contextPanel4.add(new JLabel("Condition4"));
		contextPanel6 = new JPanel();
		contextPanel4.add(new JLabel(name4));
		contextPanel4.add(new JLabel("From:"));
		contextPanel4.add(fieldConditionStart4);
		contextPanel4.add(new JLabel("To:"));
		contextPanel4.add(fieldConditionEnd4);
		contextPanel4.add(new JLabel("Equal:"));
		contextPanel4.add(fieldConditionEqual4);
	
		JPanel layout = new JPanel();
		layout.add(contextPanel1);
		layout.add(contextPanel2);
		layout.add(contextPanel3);
		layout.add(contextPanel4);
		contentPane.add(layout, BorderLayout.CENTER);
	}
	// when it has five columns 
	private void createPanels5(String name1, String Type1, String name2, String Type2, String name3, String Type3, String name4, String Type4, String name5, String Type5) {
		Name1 = name1;
		this.Type1 = Type1;
		Name2 = name2;
		this.Type2 = Type2;
		Name3 = name3;
		this.Type3 = Type3;
		Name4 = name4;
		this.Type4 = Type4;
		Name5 = name5;
		this.Type5 = Type5;
		
		Container contentPane = getContentPane();
		// First layout
		contextPanel1 = new JPanel(new GridLayout(0, 1), false);
		contextPanel1.add(new JLabel("Condition1"));
		contextPanel6 = new JPanel();
		contextPanel1.add(new JLabel(name1));
		contextPanel1.add(new JLabel("From:"));
		contextPanel1.add(fieldConditionStart1);
		contextPanel1.add(new JLabel("To:"));
		contextPanel1.add(fieldConditionEnd1);
		contextPanel1.add(new JLabel("Equal:"));
		contextPanel1.add(fieldConditionEqual1);
		
		// Second layout
		contextPanel2 = new JPanel(new GridLayout(0, 1), false);
		contextPanel2.add(new JLabel("Condition2"));
		contextPanel6 = new JPanel();
		contextPanel2.add(new JLabel(name2));
		contextPanel2.add(new JLabel("From:"));
		contextPanel2.add(fieldConditionStart2);
		contextPanel2.add(new JLabel("To:"));
		contextPanel2.add(fieldConditionEnd2);
		contextPanel2.add(new JLabel("Equal:"));
		contextPanel2.add(fieldConditionEqual2);
		
		// Third layout
		contextPanel3 = new JPanel(new GridLayout(0, 1), false);
		contextPanel3.add(new JLabel("Condition3"));
		contextPanel6 = new JPanel();
		contextPanel3.add(new JLabel(name3));
		contextPanel3.add(new JLabel("From:"));
		contextPanel3.add(fieldConditionStart3);
		contextPanel3.add(new JLabel("To:"));
		contextPanel3.add(fieldConditionEnd3);
		contextPanel3.add(new JLabel("Equal:"));
		contextPanel3.add(fieldConditionEqual3);
		
		// Forth layout 
		contextPanel4 = new JPanel(new GridLayout(0, 1), false);
		contextPanel4.add(new JLabel("Condition4"));
		contextPanel6 = new JPanel();
		contextPanel4.add(new JLabel(name4));
		contextPanel4.add(new JLabel("From:"));
		contextPanel4.add(fieldConditionStart4);
		contextPanel4.add(new JLabel("To:"));
		contextPanel4.add(fieldConditionEnd4);
		contextPanel4.add(new JLabel("Equal:"));
		contextPanel4.add(fieldConditionEqual4);
		
		// Fifth layout
		contextPanel5 = new JPanel(new GridLayout(0, 1), false);
		contextPanel5.add(new JLabel("Condition5"));
		contextPanel6 = new JPanel();
		contextPanel5.add(new JLabel(name5));
		contextPanel5.add(new JLabel("From:"));
		contextPanel5.add(fieldConditionStart5);
		contextPanel5.add(new JLabel("To:"));
		contextPanel5.add(fieldConditionEnd5);
		contextPanel5.add(new JLabel("Equal:"));
		contextPanel5.add(fieldConditionEqual5);
		
		JPanel layout = new JPanel();
		layout.add(contextPanel1);
		layout.add(contextPanel2);
		layout.add(contextPanel3);
		layout.add(contextPanel4);
		layout.add(contextPanel5);
		contentPane.add(layout, BorderLayout.CENTER);
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
		setMinimumSize(new Dimension(500, 300));
		setActive(true);
	}
	
	private void onSearchButtonClick(ActionEvent actionEvent) {
		String name1 = Name1;
		String name2 = Name2;
		String name3 = Name3;
		String name4 = Name4;
		String name5 = Name5;
		String type1 = Type1;
		String type2 = Type2;
		String type3 = Type3;
		String type4 = Type4;
		String type5 = Type5;
		String Name = (String)(jcb1.getSelectedItem());
		String ConditionStart1 = fieldConditionStart1.getText();
		String ConditionStart2 = fieldConditionStart2.getText();
		String ConditionStart3 = fieldConditionStart3.getText();
		String ConditionStart4 = fieldConditionStart4.getText();
		String ConditionStart5 = fieldConditionStart5.getText();
		String ConditionEnd1 = fieldConditionEnd1.getText();
		String ConditionEnd2 = fieldConditionEnd2.getText();
		String ConditionEnd3 = fieldConditionEnd3.getText();
		String ConditionEnd4 = fieldConditionEnd4.getText();
		String ConditionEnd5 = fieldConditionEnd5.getText();
		String ConditionEqual1 = fieldConditionEqual1.getText();
		String ConditionEqual2 = fieldConditionEqual2.getText();
		String ConditionEqual3 = fieldConditionEqual3.getText();
		String ConditionEqual4 = fieldConditionEqual4.getText();
		String ConditionEqual5 = fieldConditionEqual5.getText();
		
		if(Name.equals("Condition "+name1)){
			// if the user just input the first blank
			if(!ConditionStart1.isEmpty() && ConditionEnd1.isEmpty() && ConditionEqual1.isEmpty()) {
				System.out.println("Start At: "+ConditionStart1);	// here only has one parameter which is condition1 start the type is string
				System.out.println("The Name is "+name1+ " and type is "+type1);
			}
			// if the user only input the second blank
			else if(ConditionStart1.isEmpty() && !ConditionEnd1.isEmpty() && ConditionEqual1.isEmpty()) {
				System.out.println("End At: "+ConditionEnd1);	// here only has one parameter which is condition1 end the type is string
				System.out.println("The Name is "+name1+ " and type is "+type1);
			}
			else if(ConditionStart1.isEmpty() && ConditionEnd1.isEmpty() && !ConditionEqual1.isEmpty()) {
				System.out.println("Equal To: "+ConditionEqual1);	// here only has one parameter which is equal the type is string
				System.out.println("The Name is "+name1+ " and type is "+type1);
			}
			else if(!ConditionStart1.isEmpty() && !ConditionEnd1.isEmpty()) {
				System.out.println("Start At: "+ConditionStart1);	// here only has two parameters which are start and end the type both are string
				System.out.println("End At: "+ConditionEnd1);
				System.out.println("The Name is "+name1+ " and type is "+type1);
			}
			else {
				warning();
				clear();
			}
		}
		else if(Name.equals("Condition "+name2)) {
			// if the user just input the first blank
			if(!ConditionStart2.isEmpty() && ConditionEnd2.isEmpty() && ConditionEqual2.isEmpty()) {
				System.out.println("Start At: "+ConditionStart2);	// here only has one parameter which is condition1 start the type is string
				System.out.println("The Name is "+name2+ " and type is "+type2+".");
			}
			// if the user only input the second blank
			else if(ConditionStart2.isEmpty() && !ConditionEnd2.isEmpty() && ConditionEqual2.isEmpty()) {
				System.out.println("End At: "+ConditionEnd2);	// here only has one parameter which is condition1 end the type is string
				System.out.println("The Name is "+name2+ " and type is "+type2+".");
			}
			else if(ConditionStart2.isEmpty() && ConditionEnd2.isEmpty() && !ConditionEqual2.isEmpty()) {
				System.out.println("Equal To: "+ConditionEqual2);	// here only has one parameter which is equal the type is string
				System.out.println("The Name is "+name2+ " and type is "+type2+".");
			}
			else if(!ConditionStart2.isEmpty() && !ConditionEnd2.isEmpty()) {
				System.out.println("Start At: "+ConditionStart2);	// here only has two parameters which are start and end the type both are string
				System.out.println("End At: "+ConditionEnd2);
				System.out.println("The Name is "+name2+ " and type is "+type2+".");
			}
			else {
				warning();
				clear();
			}
		}
		else if(Name.equals("Condition "+name3)) {
			// if the user just input the first blank
			if(!ConditionStart3.isEmpty() && ConditionEnd3.isEmpty() && ConditionEqual1.isEmpty()) {
				System.out.println("Start At: "+ConditionStart1);	// here only has one parameter which is condition1 start the type is string
				System.out.println("The Name is "+name3+ " and type is "+type3+".");
			}
			// if the user only input the second blank
			else if(ConditionStart3.isEmpty() && !ConditionEnd3.isEmpty() && ConditionEqual3.isEmpty()) {
				System.out.println("End At: "+ConditionEnd3);	// here only has one parameter which is condition1 end the type is string
				System.out.println("The Name is "+name3+ " and type is "+type3+".");
			}
			else if(ConditionStart3.isEmpty() && ConditionEnd3.isEmpty() && !ConditionEqual3.isEmpty()) {
				System.out.println("Equal To: "+ConditionEqual3);	// here only has one parameter which is equal the type is string
				System.out.println("The Name is "+name3+ " and type is "+type3+".");
			}
			else if(!ConditionStart3.isEmpty() && !ConditionEnd3.isEmpty()) {
				System.out.println("Start At: "+ConditionStart3);	// here only has two parameters which are start and end the type both are string
				System.out.println("End At: "+ConditionEnd3);
				System.out.println("The Name is "+name3+ " and type is "+type3+".");
			}
			else {
				warning();
				clear();
			}
		}
		else if(Name.equals("Condition "+name4)) {
			// if the user just input the first blank
			if(!ConditionStart4.isEmpty() && ConditionEnd4.isEmpty() && ConditionEqual4.isEmpty()) {
				System.out.println("Start At: "+ConditionStart4);	// here only has one parameter which is condition1 start the type is string
				System.out.println("The Name is "+name4+ " and type is "+type4+".");
			}
			// if the user only input the second blank
			else if(ConditionStart4.isEmpty() && !ConditionEnd4.isEmpty() && ConditionEqual4.isEmpty()) {
				System.out.println("End At: "+ConditionEnd4);	// here only has one parameter which is condition1 end the type is string
				System.out.println("The Name is "+name4+ " and type is "+type4+".");
			}
			else if(ConditionStart4.isEmpty() && ConditionEnd4.isEmpty() && !ConditionEqual4.isEmpty()) {
				System.out.println("Equal To: "+ConditionEqual4);	// here only has one parameter which is equal the type is string
				System.out.println("The Name is "+name4+ " and type is "+type4+".");
			}
			else if(!ConditionStart4.isEmpty() && !ConditionEnd4.isEmpty()) {
				System.out.println("Start At: "+ConditionStart4);	// here only has two parameters which are start and end the type both are string
				System.out.println("End At: "+ConditionEnd4);
				System.out.println("The Name is "+name4+ " and type is "+type4+".");
			}
			else {
				warning();
				clear();
			}
		}
		else if(Name.equals("Condition "+name5)) {
			// if the user just input the first blank
			if(!ConditionStart5.isEmpty() && ConditionEnd5.isEmpty() && ConditionEqual5.isEmpty()) {
				System.out.println("Start At: "+ConditionStart5);	// here only has one parameter which is condition1 start the type is string
				System.out.println("The Name is "+name5+ " and type is "+type5+".");
			}
			// if the user only input the second blank
			else if(ConditionStart5.isEmpty() && !ConditionEnd5.isEmpty() && ConditionEqual5.isEmpty()) {
				System.out.println("End At: "+ConditionEnd5);	// here only has one parameter which is condition1 end the type is string
				System.out.println("The Name is "+name5+ " and type is "+type5+".");
			}
			else if(ConditionStart5.isEmpty() && ConditionEnd5.isEmpty() && !ConditionEqual5.isEmpty()) {
				System.out.println("Equal To: "+ConditionEqual5);	// here only has one parameter which is equal the type is string
				System.out.println("The Name is "+name5+ " and type is "+type5+".");
			}
			else if(!ConditionStart5.isEmpty() && !ConditionEnd5.isEmpty()) {
				System.out.println("Start At: "+ConditionStart5);	// here only has two parameters which are start and end the type both are string
				System.out.println("End At: "+ConditionEnd5);
				System.out.println("The Name is "+name5+ " and type is "+type5+".");
			}
			else {
				warning();
				clear();
			}
		}
		else {
			warning();
		}
	}
	
	// Create the window if the user input the invalid condition
	public void warning() {
		JOptionPane.showMessageDialog(null, "Your input is invaild! \nPlease enter again",
				"Prompt message", JOptionPane.ERROR_MESSAGE);
	}
	
	// Reset all user input 
	// if the user input the invalid input it will has the warning window and reset all the input
	public void clear() {
		fieldConditionStart1.setText(null);
	    fieldConditionStart2.setText(null);
	    fieldConditionStart3.setText(null); 
	    fieldConditionStart4.setText(null); 
	    fieldConditionStart5.setText(null); 
	    fieldConditionEnd1.setText(null); 
	    fieldConditionEnd2.setText(null); 
	    fieldConditionEnd3.setText(null); 
	    fieldConditionEnd4.setText(null); 
	    fieldConditionEnd5.setText(null); 
	    fieldConditionEqual1.setText(null); 
	    fieldConditionEqual2.setText(null); 
	    fieldConditionEqual3.setText(null); 
	    fieldConditionEqual4.setText(null);
	    fieldConditionEqual5.setText(null); 
	}
	
	// Convert the map to String 
	@SuppressWarnings("rawtypes")
	public static String transMapToString(Map map){  
		  java.util.Map.Entry entry;  
		  StringBuffer sb = new StringBuffer();  
		  for(Iterator iterator = map.entrySet().iterator(); iterator.hasNext();)  
		  {  
		    entry = (java.util.Map.Entry)iterator.next();  
		      sb.append(entry.getKey().toString()).append( " " ).append(null==entry.getValue()?"":  
		      entry.getValue().toString()).append (iterator.hasNext() ? " " : "");  
		  }  
		  return sb.toString();  
		} 
	
	// Convert the String to list 
	public String[] transStringToList(int size, String s) {
		int size1 = size*2;
		String[] SS = new String[size1];
		SS = s.split(" ",size1);
		return SS;
	}
}
















