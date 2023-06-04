package africa.semicolon.regcrow.services;

import africa.semicolon.regcrow.dtos.request.CustomerRegistrationRequest;
import africa.semicolon.regcrow.dtos.request.CustomerUpdateRequest;
import africa.semicolon.regcrow.dtos.request.TransactionCreationRequest;
import africa.semicolon.regcrow.dtos.request.TransactionUpdateRequest;
import africa.semicolon.regcrow.dtos.response.CustomerRegistrationResponse;
import africa.semicolon.regcrow.dtos.response.CustomerResponse;
import africa.semicolon.regcrow.dtos.response.TransactionCreationResponse;
import africa.semicolon.regcrow.dtos.response.TransactionResponse;
import africa.semicolon.regcrow.exceptions.CustomerRegistrationFailedException;
import africa.semicolon.regcrow.exceptions.TransactionCreationFailedException;
import africa.semicolon.regcrow.exceptions.TransactionNotFoundException;
import africa.semicolon.regcrow.exceptions.UserNotFoundException;
import africa.semicolon.regcrow.models.Status;
import africa.semicolon.regcrow.services.customerServices.CustomerService;
import africa.semicolon.regcrow.services.transactionServices.TransactionService;
import africa.semicolon.regcrow.services.transactionServices.TransactionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import static africa.semicolon.regcrow.utils.AppUtils.ONE;
import static africa.semicolon.regcrow.utils.AppUtils.TWO;
import static java.math.BigInteger.TEN;
import static java.math.BigInteger.ZERO;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class RegcrowTransactionServiceTest {
    @Autowired
    CustomerService customerService;
    @Autowired
    TransactionService transactionService;
    CustomerRegistrationRequest customerRegistrationRequest1;
    CustomerRegistrationRequest customerRegistrationRequest2;
    CustomerRegistrationResponse customerRegistrationResponse1;
    CustomerRegistrationResponse customerRegistrationResponse2;
    TransactionCreationRequest transactionCreationRequest;
    TransactionCreationResponse transactionCreationResponse;

    @BeforeEach
    public void setUp() throws CustomerRegistrationFailedException, TransactionCreationFailedException {
        transactionService.deleteAll();
        customerRegistrationRequest1 =
                CustomerRegistrationRequest.builder()
                        .password("1234")
                        .email("Ogunsmoyin.m@gmail.com")
                        .build();
        customerRegistrationResponse1 = customerService.register(customerRegistrationRequest1);

        customerRegistrationRequest2 =
                CustomerRegistrationRequest.builder()
                        .password("1234")
                        .email("profolakunle@gmail.com")
                        .build();
        customerRegistrationResponse2 = customerService.register(customerRegistrationRequest2);

        transactionCreationRequest = TransactionCreationRequest.builder()
                .buyerId(customerRegistrationResponse1.getId())
                .sellerId(customerRegistrationResponse2.getId())
                .description("Mac-Book")
                .amount(BigDecimal.valueOf(50_000))
                .build();
        transactionCreationResponse = transactionService.createTransaction(transactionCreationRequest);

    }

    @Test void testThatTransactionCanBeCreated() {
        assertThat(transactionCreationResponse).isNotNull();
    }

    @Test void getTransactionByIdTest() throws TransactionNotFoundException {
        TransactionResponse foundTransaction = transactionService.getTransactionById(transactionCreationResponse.getId());
        assertThat(foundTransaction).isNotNull();
        assertThat(foundTransaction.getDescription()).isNotNull();
        assertThat(foundTransaction.getDescription()).isEqualTo(transactionCreationRequest.getDescription());
    }

    @Test void getAllTransactions() throws TransactionCreationFailedException {
        transactionCreationRequest.setBuyerId(customerRegistrationResponse1.getId());
        transactionCreationRequest.setSellerId(customerRegistrationResponse2.getId());
        transactionService.createTransaction(transactionCreationRequest);
        List<TransactionResponse> transactions = transactionService.getAllTransactions();
        assertThat(transactions.size()).isEqualTo(TWO);
    }

    @Test void getTransactionsByCustomerIdTest() throws TransactionCreationFailedException, CustomerRegistrationFailedException {
        customerRegistrationRequest1.setEmail("aliyah@gmail.com");
        customerRegistrationRequest1.setPassword("1234");
        var customerResponse3 = customerService.register(customerRegistrationRequest1);

        transactionCreationRequest.setBuyerId(customerResponse3.getId());
        transactionCreationRequest.setSellerId(customerRegistrationResponse2.getId());
        transactionService.createTransaction(transactionCreationRequest);

        var customer1Transactions = transactionService.getTransactionsByCustomerId(customerRegistrationResponse1.getId());
        var customer2Transactions = transactionService.getTransactionsByCustomerId(customerRegistrationResponse2.getId());
        var customer3Transactions = transactionService.getTransactionsByCustomerId(customerResponse3.getId());

        assertThat(customer1Transactions.size()).isEqualTo(ONE);
        assertThat(customer2Transactions.size()).isEqualTo(TWO);
        assertThat(customer3Transactions.size()).isEqualTo(ONE);
    }

    @Test void deleteTransactionTest(){
        var transactions = transactionService.getAllTransactions();
        int numberOfTransactions = transactions.size();
        assertThat(numberOfTransactions).isGreaterThan(ZERO.intValue());
        transactionService.deleteTransaction(transactionCreationResponse.getId());
        List<TransactionResponse> currentTransactions = transactionService.getAllTransactions();
        assertThat(currentTransactions.size()).isEqualTo(numberOfTransactions- BigInteger.ONE.intValue());
    }

    @Test void updateTransactionTest() throws TransactionNotFoundException {
        var transactionResponse = transactionService.getTransactionById(transactionCreationResponse.getId());
        assertThat(transactionResponse.getStatus()).isEqualTo(Status.PENDING);

        TransactionUpdateRequest transactionUpdateRequest =
                TransactionUpdateRequest.builder()
                                        .transactionId(transactionResponse.getId())
                                        .status(Status.SUCCESS)
                                        .build();
        transactionService.updateTransactionDetails(transactionUpdateRequest);
        var updatedTransaction = transactionService.getTransactionById(transactionResponse.getId());

        assertThat(updatedTransaction.getStatus()).isEqualTo(Status.SUCCESS);
    }
}
