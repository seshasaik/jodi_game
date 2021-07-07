package animo.realcom.mahakubera.serviceImpl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import animo.realcom.mahakubera.entity.TransactionCodes;
import animo.realcom.mahakubera.entity.Transactions;
import animo.realcom.mahakubera.repository.TransactionCodesRepository;
import animo.realcom.mahakubera.repository.TransactionsRepository;
import animo.realcom.mahakubera.service.TransactionsService;

@Service
public class TransactionsServiceImpl implements TransactionsService {

	@Autowired
	TransactionCodesRepository transactionCodesRepository;

	@Autowired
	TransactionsRepository transactionsRepository;

	public TransactionsServiceImpl() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public TransactionCodes getTransactionCodesByStatusCode(String statusCode) {
		// TODO Auto-generated method stub
		return transactionCodesRepository.findByStatusId(statusCode);
	}

	@Override
	public int saveTransaction(Transactions transactions) {
		// TODO Auto-generated method stub
		String vUniqueCode = UUID.randomUUID().toString();// transactions.getSeriesno() + transactions.getTrasactionCode() +
								// transactions.getRegId()
		transactionsRepository.saveTransaction(Long.parseLong(transactions.getRegId()), transactions.getRole(),
				transactions.getCredit(), transactions.getDebit(), transactions.getTotal(),
				transactions.getTransactionId(), transactions.getPurpose(), transactions.getDateTime(),
				transactions.getTransactionCodes(), transactions.getUniqueId(), 1, "", "", vUniqueCode);
		return 0;
	}

}
