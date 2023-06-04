package africa.semicolon.regcrow.utils;

import africa.semicolon.regcrow.dtos.request.TransactionCreationRequest;
import africa.semicolon.regcrow.models.Payment;
import africa.semicolon.regcrow.models.Transaction;

public class RegcrowMapper {
    public Transaction map(TransactionCreationRequest transactionCreationRequest) {
        Payment payment = Payment.builder()
                .amount(transactionCreationRequest.getAmount())
                .build();
        Transaction transaction = new Transaction();
        transaction.setBuyerId(transactionCreationRequest.getBuyerId());
        transaction.setSellerId(transactionCreationRequest.getSellerId());
        transaction.setDescription(transactionCreationRequest.getDescription());
        transaction.setPayment(payment);
        return transaction;
    }
}
