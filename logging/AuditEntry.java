package inventoryManager.logging;

import inventoryManager.util.TimeUtils;

class AuditEntry {
    final String timestamp;
    final AuditAction action;
    final String details;

    public AuditEntry(AuditAction action) {
	this(action, null);
    }

    public AuditEntry(AuditAction action, String details) {
	this.timestamp = TimeUtils.nowToString();
	this.action = action;
	this.details = details;
	if (details != null)
	    System.out.printf("Log action created -> (%s, %s)\n", action, details);
	else
	    System.out.printf("Log action created -> (%s)\n", action);
    }

    @Override
    public String toString() {
	return String.join(",", timestamp, action.name(), details == null ? "" : details);
    }
}
