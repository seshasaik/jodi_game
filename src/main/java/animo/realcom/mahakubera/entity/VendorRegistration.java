package animo.realcom.mahakubera.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Table(name = "vendor_registration")
@Entity
@Data
public class VendorRegistration {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "vendor_reg_id", updatable = false, nullable = false)
	private Long id;

	@Column(name = "vendor_name")
	private String name;

	@Column(name = "vendor_gender")
	private String vendorGender;

	@Column(name = "vendor_dob")
	private String vendorDOB;

	@Column(name = "vendor_mobile")
	private String vendorMobile;

	@Column(name = "vendor_email")
	private String vendorEmail;

	@Column(name = "vendor_password")
	private String vendorOriginalPassword;

	@Column(name = "view_password")
	private String vendorPassword;

	@Column(name = "vendor_status")
	private String vendorStatus;

	@Column(name = "vendor_role")
	private String vendorRole;

	@Column(name = "vendor_aadhar")
	private String vendorAadhar;

	@Column(name = "address_line_1")
	private String addressLine1;

	@Column(name = "address_line_2")
	private String addressLine2;

	@Column(name = "country")
	private String country;

	@Column(name = "state")
	private String state;

	@Column(name = "city")
	private String city;

	@Column(name = "pincode")
	private String pincode;

	@Column(name = "date_of_register")
	private String dateOfRegister;

	@Column(name = "remarks")
	private String remarks;

	@Column(name = "header_id")
	private String headerId;

	@Column(name = "approved_by")
	private String approvedBy;

	@Column(name = "approved_vendor")
	private String approvedVendor;

	@Column(name = "joker_st")
	private String jokerSt;

	@Column(name = "joker_qty")
	private String jokerQty;

	@Column(name = "cert_status")
	private String certStatus;

	@Column(name = "warn_timer")
	private String warnTimer;

	@Column(name = "sub_vendor_status")
	private String subVendorStatus;

	@Column(name = "gstno")
	private String gstNo;

	@Column(name = "joker_ignore")
	private Integer jokerIgnore;

	@Column(name = "joker_quantity")
	private Long jokerQuantity;

	@Column(name = "pan_no")
	private String vendorPanNumber;

	@Column(name = "vendor_mac_address")
	private String vendorMacAddress;

	@Column(name = "vendor_mac_status")
	private String vendorMacStatus;

	@Column(name = "vendor_game_status")
	private Integer vendor_game_status;

}
