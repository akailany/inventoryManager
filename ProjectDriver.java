package inventoryManager;

import inventoryManager.auth.Authenticator;
import inventoryManager.auth.DBAuthenticator;
import inventoryManager.logging.DBUserAuditor;
import inventoryManager.logging.UserAuditor;
import inventoryManager.ui.Login;


public class ProjectDriver {
    public static Authenticator authenticator;
    public static UserAuditor auditor;

    public static void main(String[] args) {
	authenticator = new DBAuthenticator();
	auditor = new DBUserAuditor();
	new Login();
    }
}
