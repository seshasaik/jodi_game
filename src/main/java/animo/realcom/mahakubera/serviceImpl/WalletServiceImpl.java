package animo.realcom.mahakubera.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import animo.realcom.mahakubera.entity.AdminWallet;
import animo.realcom.mahakubera.entity.Wallet;
import animo.realcom.mahakubera.repository.AdminWalletRepository;
import animo.realcom.mahakubera.repository.WalletRepository;
import animo.realcom.mahakubera.service.WalletService;

@Service
public class WalletServiceImpl implements WalletService {

	@Autowired
	WalletRepository walletRepository;

	@Autowired
	AdminWalletRepository adminWalletRepository;

	public WalletServiceImpl() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Wallet getWallet(String regId, String role) {
		// TODO Auto-generated method stub
		return walletRepository.findByRegIdAndRole(regId, role);
	}

	@Override
	public void updateWalletAmount(Wallet wallet) {
		// TODO Auto-generated method stub
		walletRepository.saveAndFlush(wallet);
	}

	@Override
	public AdminWallet getAdminWallet() {
		// TODO Auto-generated method stub
		return adminWalletRepository.findAll().get(0);
	}

	@Override
	public void updateAdminWalletAmount(AdminWallet wallet) {
		// TODO Auto-generated method stub
		adminWalletRepository.saveAndFlush(wallet);
	}

}
