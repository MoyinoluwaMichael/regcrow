package africa.semicolon.regcrow.services.customerServices;

import africa.semicolon.regcrow.dtos.request.CustomerRegistrationRequest;
import africa.semicolon.regcrow.dtos.request.CustomerUpdateRequest;
import africa.semicolon.regcrow.dtos.response.ApiResponse;
import africa.semicolon.regcrow.dtos.response.CustomerRegistrationResponse;
import africa.semicolon.regcrow.dtos.response.CustomerResponse;
import africa.semicolon.regcrow.exceptions.CustomerRegistrationFailedException;
import africa.semicolon.regcrow.exceptions.ProfileUpdateFailedException;
import africa.semicolon.regcrow.exceptions.UserNotFoundException;
import com.github.fge.jsonpatch.JsonPatch;

import java.util.List;

public interface CustomerService {
    CustomerRegistrationResponse register(CustomerRegistrationRequest customerRegistrationRequest) throws CustomerRegistrationFailedException;

    CustomerResponse getCustomerById(Long id) throws UserNotFoundException;



    List<CustomerResponse> getAllCustomers(int page, int items);

    ApiResponse<?> deleteCustomer(Long id);

    void deleteAll();

    ApiResponse<?> updateCustomerDetails(Long id, JsonPatch jsonPatch) throws UserNotFoundException, ProfileUpdateFailedException;

    ApiResponse<?> updateCustomerDetails2(CustomerUpdateRequest customerUpdateRequest) throws UserNotFoundException;
}
