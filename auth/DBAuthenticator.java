package inventoryManager.auth;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import team6project.db.SQLiteDatabase;
import team6project.db.SQLiteQueryBuilder;

public class DBAuthenticator implements Authenticator {
    private AccountType authLevel;
    private boolean ready;

    private enum Field {
	ACCOUNT_NAME("name"),
	ACCOUNT_PASSWORD("pass"),
	ACCOUNT_TYPE("type");
	private final String dbname;

	private Field(String dbName) {
	    this.dbname = dbName;
	}

	@Override
	public String toString() {
	    return dbname;
	}
    }

    private final String TABLE_ACCOUNTS;
    /** The name for the master login */
    private final String MASTER_USERNAME;
    /** The plain-text password for the master password */
    private final String MASTER_PASSWORD;

    public DBAuthenticator() {
	TABLE_ACCOUNTS = "sys_accounts";
	this.MASTER_USERNAME = "Admin";
	this.MASTER_PASSWORD = "Admin";
	if (!SQLiteDatabase.getInstance().tableExists(TABLE_ACCOUNTS, true)) {
	    String statement = String.format("CREATE TABLE %s ('%s' TEXT, '%s' TEXT, '%s' TEXT, primary key(%s));", TABLE_ACCOUNTS, Field.ACCOUNT_NAME, Field.ACCOUNT_PASSWORD, Field.ACCOUNT_TYPE, Field.ACCOUNT_NAME);
	    SQLiteDatabase.getInstance().bExecuteStatement(statement);
	    ready = SQLiteDatabase.getInstance().tableExists(TABLE_ACCOUNTS, true);
	} else
	    ready = true;
	authLevel = AccountType.INVALID;
    }

    @Override
    public AccountType login(String accountName, String accountPassword) {
	System.out.printf("Login called with paramaters: %s, %s\n", accountName, accountPassword);
	if (accountName == null || accountName.isBlank())
	    return AccountType.INVALID;
	if (accountPassword == null || accountPassword.isBlank())
	    return AccountType.INVALID;
	if (accountName.equals(MASTER_USERNAME))
	    return accountPassword.equals(MASTER_PASSWORD) ? AccountType.ADMIN : AccountType.INVALID;
	if(!ready) return AccountType.INVALID;
	SQLiteQueryBuilder query = new SQLiteQueryBuilder(TABLE_ACCOUNTS);
	query.where(String.format("%s = '%s'", Field.ACCOUNT_NAME, accountName));
	query.where(String.format("%s = '%s'", Field.ACCOUNT_PASSWORD, accountPassword));
	ResultSet result = SQLiteDatabase.getInstance().executeQuery(query.toString());
	try {
	    if (!result.next())
		return AccountType.INVALID;
	    String typeStr = result.getString(Field.ACCOUNT_TYPE.toString());
	    AccountType type = AccountType.valueOf(typeStr);
	    System.out.println("Input: " + typeStr);
	    System.out.println("Type: " + type);
	    if (type == null || type == AccountType.INVALID)
		authLevel = type;
	    return type;
	} catch (Exception e) {
	    e.printStackTrace();
	    return AccountType.INVALID;
	}
    }

    @Override
    public boolean createAccount(String accountName, String accountPassword, AccountType accountType) {
	if (authLevel != AccountType.ADMIN)
	    return false;
	if (login(accountName, accountPassword) != AccountType.INVALID)
	    return false;
	String statement = String.format("INSERT INTO %s (%s, %s, %s) values ('%s', '%s', '%s');", TABLE_ACCOUNTS, Field.ACCOUNT_NAME, Field.ACCOUNT_PASSWORD, Field.ACCOUNT_TYPE, accountName, accountPassword, accountType.name());
	try {
	    return SQLiteDatabase.getInstance().sExecuteStatement(statement).getUpdateCount() >= 1;
	} catch (Exception e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	    return false;
	}
    }

    @Override
    public boolean removeAccount(String accountName, String accountPassword, AccountType accountType) {
	System.out.println("Remove account callled. Auth level is: " + authLevel);
	if (authLevel != AccountType.ADMIN)
	    return false;
	String statement = String.format("DELETE FROM %s WHERE %s = '%s';", TABLE_ACCOUNTS, Field.ACCOUNT_NAME, accountName);
	try {
	    return SQLiteDatabase.getInstance().sExecuteStatement(statement).getUpdateCount() >= 1;
	} catch (Exception e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	    return false;
	}
    }

    @Override
    public List<AccountData> getAllAccounts(String accountName, String accountPassword) {
	if (authLevel != AccountType.ADMIN && login(accountName, accountPassword) != AccountType.ADMIN)
	    return new LinkedList<>();
	else
	    authLevel = AccountType.ADMIN;
	SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder(TABLE_ACCOUNTS);
	ResultSet set = SQLiteDatabase.getInstance().executeQuery(queryBuilder.toString());
	List<AccountData> dataList = new ArrayList<>(8);
	String tmpName, tmpPass;
	AccountType tmpType;
	try {
	    while (set.next()) {
		try {
		    tmpName = set.getString(Field.ACCOUNT_NAME.toString());
		    tmpPass = set.getString(Field.ACCOUNT_PASSWORD.toString());
		    tmpType = AccountType.valueOf(set.getString(Field.ACCOUNT_TYPE.toString()));
		    AccountData data = new AccountData(tmpName, tmpPass, tmpType);
		    if (!data.isComplete())
			continue;
		    dataList.add(data);
		} catch (Exception e) {
		    e.printStackTrace();
		    continue;
		}
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	    return dataList;
	}
	return dataList;
    }
}
