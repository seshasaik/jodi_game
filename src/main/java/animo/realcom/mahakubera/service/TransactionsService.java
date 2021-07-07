package animo.realcom.mahakubera.service;

import animo.realcom.mahakubera.entity.TransactionCodes;
import animo.realcom.mahakubera.entity.Transactions;

public interface TransactionsService {

	TransactionCodes getTransactionCodesByStatusCode(String statusCode);
	
	int saveTransaction(Transactions transactions);
}
