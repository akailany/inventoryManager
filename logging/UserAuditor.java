package inventoryManager.logging;

public interface UserAuditor {
    public boolean setUser(String accountName);

    public boolean logAction(AuditAction action, String... description);

    public void closeAuditor();
}
