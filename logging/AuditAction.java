package inventoryManager.logging;

public enum AuditAction {
    // Basic actions
    LOGIN,
    LOGOUT,
    // Database actions
    TABLE_CREATE,
    TABLE_DELETE,
    TABLE_OPEN,
    TABLE_CLOSE,
    COLUMN_CREATE,
    ROW_CREATE,
    ENTRY_EDIT,
    ENTRY_CLEAR,
    ENTRY_ADD,
    // Account actions
    CREATE_ACCOUNT,
    DELETE_ACCOUNT,
    EDIT_ACCOUNT,
    // UI actions
    VIEW_OPEN,
    VIEW_CLOSE,
    VIEW_CHANGE
}
