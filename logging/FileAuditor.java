package inventoryManager.logging;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

@Deprecated
public class FileAuditor implements UserAuditor {
    private File auditFile;
    private FileWriter fileWriter;
    private final String username;
    private static FileAuditor current;

    public static FileAuditor getCurrent() {
	return current;
    }

    public static FileAuditor generate(String name) {
	if (current != null) {
	    if (current.username.equals(name))
		return current;
	    else
		current.closeAuditor();
	}
	current = new FileAuditor(name);
	return current;
    }

    public FileAuditor(String user) {
	actions = new LinkedList<>();
	System.out.printf("Creating audit log for user '%s'\n", user);
	this.username = user;
	auditFile = new File(System.getProperty("user.dir"), "_audits" + File.separator + user + ".csv");
	boolean b = auditFile.exists();
	if (!b)
	    try {
		System.out.printf("Audit file not found for '%s' - creating one", user);
		auditFile.getParentFile().mkdirs();
		auditFile.createNewFile();
	    } catch (IOException e) {
		System.err.println("Failed to create audit file");
		e.printStackTrace();
		return;
	    }
	try {
	    fileWriter = new FileWriter(auditFile, b);
	} catch (IOException e) {
	    System.err.println("Failed to create file writer");
	    e.printStackTrace();
	    return;
	}
	System.out.println("Audit log created successfully");
    }

    public void flushToFile() {
	Iterator<AuditEntry> actionsIterator = actions.iterator();
	String text;
	while (actionsIterator.hasNext()) {
	    text = actionsIterator.next().toString() + "\n";
	    try {
		fileWriter.write(text);
	    } catch (IOException e) {
		System.err.printf("Failed to write action to file -- %s", text);
		e.printStackTrace();
	    }
	}
	try {
	    fileWriter.flush();
	} catch (IOException e) {
	    System.err.println("Failed to flush to file");
	    e.printStackTrace();
	}
	actionsIterator = null;
	actions.clear();
    }

    public void closeAuditor() {
	System.out.println("Closing Audit Log");
	if (!actions.isEmpty())
	    flushToFile();
	actions = null;
	try {
	    fileWriter.close();
	} catch (IOException e) {
	    System.err.println("Failed to close file writer");
	    e.printStackTrace();
	}
    }

    public boolean logAction(AuditAction type, String... details) {
	if (details == null || details.length == 0) {
	    AuditEntry action = new AuditEntry(type);
	    actions.add(action);
	} else if (details.length == 1)
	    actions.add(new AuditEntry(type, details[0]));
	else
	    actions.add(new AuditEntry(type, String.join("; ", details)));
	return true;
    }

    private List<AuditEntry> actions;

    @Override
    public boolean setUser(String accountName) {
	generate(accountName);
	return true;
    }
}
