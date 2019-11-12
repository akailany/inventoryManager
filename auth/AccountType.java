package inventoryManager.auth;

/**
 * 
 * @author jloosa This class is an enumerable list of permissions for an account
 */
public enum AccountType {
	/**
	 * The accociated account should be treated as a generic user with only basic
	 * permissions
	 */
	USER,
	/**
	 * The accociated account should be treated as an administrator with full
	 * permissions
	 */
	ADMIN,
	/** The accociated account should not be used as the credentials are invalid */
	INVALID
}
