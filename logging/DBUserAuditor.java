package inventoryManager.logging;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import inventoryManager.db.SQLiteDatabase;

public class DBUserAuditor implements UserAuditor {
    private final String AUDIT_TABLE_PREFIX;
    private String AUDIT_TABLE;
    private String currentUser;
    private boolean ready;

    private enum Field {
	TIMESTAMP("datetime"),
	ACTION("action"),
	DESCRIPTION("desc");
	private final String dbname;

	private Field(String dbName) {
	    this.dbname = dbName;
	}

	@Override
	public String toString() {
	    return dbname;
	}
    }

    public DBUserAuditor() {
	AUDIT_TABLE_PREFIX = "sys_audit_";
    }

    @Override
    public boolean setUser(String accountName) {
	if (accountName == null || accountName.isBlank())
	    return false;
	if (accountName.equals(currentUser))
	    return true;
	closeAuditor();
	currentUser = accountName;
	AUDIT_TABLE = AUDIT_TABLE_PREFIX.concat(currentUser);
	if (SQLiteDatabase.getInstance().tableExists(AUDIT_TABLE)) {
	    ready = true;
	    return true;
	}
	String statement = String.format("CREATE TABLE %s ('%s' DATETIME, '%s' TEXT, '%s' TEXT, primary key(%s));", AUDIT_TABLE, Field.TIMESTAMP, Field.ACTION, Field.DESCRIPTION, Field.TIMESTAMP);
	SQLiteDatabase.getInstance().bExecuteStatement(statement);
	ready = SQLiteDatabase.getInstance().tableExists(AUDIT_TABLE, true);
	return ready;
    }

    @Override
    public boolean logAction(AuditAction action, String... description) {
	String desc;
	if (description == null || description.length == 0)
	    desc = "";
	else
	    desc = String.join(";", description);
	String statement = String.format("INSERT INTO %s values ('%s', '%s', '%s');", AUDIT_TABLE, Timestamp.valueOf(LocalDateTime.now()), action.name(), desc);
	try {
	    return SQLiteDatabase.getInstance().sExecuteStatement(statement).getUpdateCount() >= 1;
	} catch (Exception e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	    return false;
	}
    }

    @Override
    public void closeAuditor() {
	// TODO Auto-generated method stub
    }
}
