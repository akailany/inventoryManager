package inventoryManager.ui.view;

import javax.swing.JInternalFrame;

import inventoryManager.ProjectDriver;
import inventoryManager.logging.AuditAction;

@SuppressWarnings("serial")
public abstract class View extends JInternalFrame {
    private static boolean isActive = false;

    public View(String name) {
	super(name);
	ProjectDriver.auditor.logAction(AuditAction.VIEW_OPEN, getClass().getSimpleName());
	isActive = false;
	setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
    }

    @Override
    public void dispose() {
	isActive = false;
	ProjectDriver.auditor.logAction(AuditAction.VIEW_CLOSE, getClass().getSimpleName());
	super.dispose();
    }

    public static boolean isActive() {
	return isActive;
    }

    protected static void setActive(boolean active) {
	isActive = active;
    }
}
