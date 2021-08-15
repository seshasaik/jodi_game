package animo.realcom.mahakubera.util;

public enum RoleName {
	ADMIN("ROLE_ADMIN"), ADMIN_BACK_OFFICE_USER("ROLE_ADMIN_BACK_OFFICE_USER"), COUNTRY_HEAD("ROLE_COUNTRY_HEAD"),
	SUB_VENDOR("ROLE_SUB_VENDOR"), SUB_VENDOR_BACK_OFFICE_USER("ROLE_SUB_VENDOR_BACK_OFFICE_USER"),
	VENDOR("ROLE_VENDOR"), VENDOR_BACK_OFFICE_USER("ROLE_VENDOR_BACK_OFFICE_USER");

	private String role;

	private RoleName(String role) {
		this.role = role;
	}

	public String getRoleName() {
		return this.role;
	}
}
