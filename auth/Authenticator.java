package inventoryManager.auth;

import java.util.List;

/**
 * 
 * @author Jacob
 *
 */
public interface Authenticator {
    public AccountType login(String accountName, String accountPassword);

    public boolean createAccount(String accountName, String accountPassword, AccountType accountType);

    public boolean removeAccount(String accountName, String accountPassword, AccountType accountType);

    public List<AccountData> getAllAccounts(String accountName, String accountPassword);
}
