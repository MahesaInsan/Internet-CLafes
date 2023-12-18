package models;

public class TransactionHistory {
	private TransactionHeader transactionHeader;
    private TransactionDetail transactionDetail;

    public TransactionHistory(TransactionHeader transactionHeader, TransactionDetail transactionDetail) {
        this.transactionHeader = transactionHeader;
        this.transactionDetail = transactionDetail;
    }

    public TransactionHeader getTransactionHeader() {
        return transactionHeader;
    }

    public TransactionDetail getTransactionDetail() {
        return transactionDetail;
    }
}
