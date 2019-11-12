package inventoryManager.auth;

public class AccountData implements Comparable<AccountData> {
    private String accountName;
    private String accountPassword;
    private AccountType accountType;

    public AccountData() {
    }

    public AccountData(String accountName) {
	this.accountName = accountName;
    }

    public AccountData(String accountName, String accountPassword, AccountType accountType) {
	this.accountName = accountName;
	this.accountPassword = accountPassword;
	this.accountType = accountType;
    }

    public boolean isComplete() {
	if (accountName == null || accountName.isBlank())
	    return false;
	if (accountPassword == null || accountPassword.isBlank())
	    return false;
	return accountType != null;
    }

    @Override
    public int compareTo(AccountData o) {
	if (accountType != o.getAccountType())
	    return -accountType.compareTo(o.accountType);
	return accountName.compareTo(o.getAccountName());
    }

    @Override
    public String toString() {
	return String.format("[%s] %s", accountType, accountName);
    }

    public String getAccountName() {
	return accountName;
    }

    public void setAccountName(String accountName) {
	if (accountName == null || accountName.isBlank())
	    accountName = null;
	else {
	    this.accountName = accountName;
	}
    }

    public String getAccountPassword() {
	return accountPassword;
    }

    public void setAccountPassword(String accountPassword) {
	if (accountPassword == null || accountPassword.isBlank())
	    accountPassword = null;
	else {
	    this.accountPassword = accountPassword;
	}
    }

    public AccountType getAccountType() {
	return accountType;
    }

    public void setAccountType(AccountType accountType) {
	this.accountType = accountType;
    }
}
