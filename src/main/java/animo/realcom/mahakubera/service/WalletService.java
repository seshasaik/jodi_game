package animo.realcom.mahakubera.service;

import animo.realcom.mahakubera.entity.AdminWallet;
import animo.realcom.mahakubera.entity.Wallet;

public interface WalletService {

	Wallet getWallet(String regId, String role);
	
	void updateWalletAmount(Wallet wallet);
	
	AdminWallet getAdminWallet();
	
	void updateAdminWalletAmount(AdminWallet wallet);
}
