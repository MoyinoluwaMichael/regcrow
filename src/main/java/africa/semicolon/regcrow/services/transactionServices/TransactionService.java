package africa.semicolon.regcrow.services.transactionServices;

import africa.semicolon.regcrow.dtos.request.TransactionCreationRequest;
import africa.semicolon.regcrow.dtos.request.TransactionUpdateRequest;
import africa.semicolon.regcrow.dtos.response.ApiResponse;
import africa.semicolon.regcrow.dtos.response.TransactionCreationResponse;
import africa.semicolon.regcrow.dtos.response.TransactionResponse;
import africa.semicolon.regcrow.exceptions.TransactionCreationFailedException;
import africa.semicolon.regcrow.exceptions.TransactionNotFoundException;

import java.util.List;

public interface TransactionService {
    TransactionCreationResponse createTransaction(TransactionCreationRequest transactionCreationRequest) throws TransactionCreationFailedException;

    TransactionResponse getTransactionById(Long id) throws TransactionNotFoundException;

    List<TransactionResponse> getAllTransactions();

    List<TransactionResponse> getTransactionsByCustomerId(Long customerId);

    void deleteAll();

    ApiResponse<?> deleteTransaction(Long id);

    ApiResponse<?> updateTransactionDetails(TransactionUpdateRequest transactionUpdateRequest) throws TransactionNotFoundException;
}
