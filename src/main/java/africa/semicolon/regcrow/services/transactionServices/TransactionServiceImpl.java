package africa.semicolon.regcrow.services.transactionServices;

import africa.semicolon.regcrow.dtos.request.TransactionCreationRequest;
import africa.semicolon.regcrow.dtos.request.TransactionUpdateRequest;
import africa.semicolon.regcrow.dtos.response.ApiResponse;
import africa.semicolon.regcrow.dtos.response.CustomerResponse;
import africa.semicolon.regcrow.dtos.response.TransactionCreationResponse;
import africa.semicolon.regcrow.dtos.response.TransactionResponse;
import africa.semicolon.regcrow.exceptions.TransactionCreationFailedException;
import africa.semicolon.regcrow.exceptions.TransactionNotFoundException;
import africa.semicolon.regcrow.exceptions.UserNotFoundException;
import africa.semicolon.regcrow.models.Customer;
import africa.semicolon.regcrow.models.Payment;
import africa.semicolon.regcrow.models.Transaction;
import africa.semicolon.regcrow.repositories.TransactionRepository;
import africa.semicolon.regcrow.utils.RegcrowMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static africa.semicolon.regcrow.utils.ExceptionUtils.*;
import static africa.semicolon.regcrow.utils.ResponseUtils.*;

@Service
@AllArgsConstructor
@Slf4j
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;
    private final ModelMapper mapper;
    private final RegcrowMapper regcrowMapper;
    @Override
    public TransactionCreationResponse createTransaction(TransactionCreationRequest transactionCreationRequest) throws TransactionCreationFailedException {
        Transaction transaction = regcrowMapper.map(transactionCreationRequest);
        Transaction savedTransaction = transactionRepository.save(transaction);
        boolean transactionIsCreated = savedTransaction.getId() != null;
        if (!transactionIsCreated) throw new TransactionCreationFailedException(TRANSACTION_CREATION_FAILED);
        return buildTransactionCreationResponse(savedTransaction.getId());
    }

    @Override
    public TransactionResponse getTransactionById(Long id) throws TransactionNotFoundException {
        Optional<Transaction> foundTransaction =  transactionRepository.findById(id);
        Transaction transaction = foundTransaction.orElseThrow(()->new TransactionNotFoundException(
                String.format(TRANSACTION_WITH_ID_NOT_FOUND, id)
        ));
        TransactionResponse transactionResponse = buildTransactionResponse(transaction);
        log.info("transaction response {}", transactionResponse);
        return transactionResponse;
    }

    @Override
    public List<TransactionResponse> getAllTransactions() {
        return transactionRepository.findAll().stream()
                .map(each -> mapper.map(each, TransactionResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<TransactionResponse> getTransactionsByCustomerId(Long customerId) {
        return Stream
                .concat(transactionRepository.findAllByBuyerId(customerId).stream(), transactionRepository.findAllBySellerId(customerId).stream())
                .map(each -> mapper.map(each, TransactionResponse.class)).toList();
    }

    @Override
    public void deleteAll() {
        transactionRepository.deleteAll();
    }

    @Override
    public ApiResponse<?> deleteTransaction(Long id) {
        transactionRepository.deleteById(id);
        return ApiResponse.builder()
                        .message(TRANSACTION_DELETED_SUCCESSFULLY)
                        .build();
    }

    @Override
    public ApiResponse<?> updateTransactionDetails(TransactionUpdateRequest transactionUpdateRequest) throws TransactionNotFoundException {
        var transaction = transactionRepository.findById(transactionUpdateRequest.getTransactionId());
        var foundTransaction = transaction.orElseThrow(() ->
                new TransactionNotFoundException(
                        String.format(TRANSACTION_WITH_ID_NOT_FOUND, transactionUpdateRequest.getTransactionId())));
        map(transactionUpdateRequest, foundTransaction);
        transactionRepository.save(foundTransaction);
        return ApiResponse.builder()
                .message(TRANSACTION_UPDATED_SUCCESSFULLY)
                .data(buildTransactionResponse(foundTransaction))
                .build();
    }

    private void map(TransactionUpdateRequest transactionUpdateRequest, Transaction foundTransaction) {
        if (transactionUpdateRequest.getStatus() != null)
            foundTransaction.setStatus(transactionUpdateRequest.getStatus());
    }

    private TransactionResponse buildTransactionResponse(Transaction transaction) {
        return TransactionResponse.builder()
                .id(transaction.getId())
                .sellerId(transaction.getSellerId())
                .buyerId(transaction.getBuyerId())
                .description(transaction.getDescription())
                .status(transaction.getStatus())
                .build();
    }

    private TransactionCreationResponse buildTransactionCreationResponse(Long id) {
        return TransactionCreationResponse.builder()
                .id(id)
                .message(TRANSACTION_CREATION_SUCCESSFUL)
                .build();
    }
}
