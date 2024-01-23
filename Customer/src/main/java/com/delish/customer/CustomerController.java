package com.delish.customer;

import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("api/v1/customer")
public class CustomerController {
    private final CustomerService customerService;


    @GetMapping(path = "/all")
    public List<Customer> getAllCustomers(){
        return customerService.retrieveAllCustomers();
    }


    @PostMapping
    public ResponseEntity<Customer> saveCustomerInfo(@RequestBody Customer customer){
            try{
                customerService.saveCustomer(customer);
                return ResponseEntity.status(HttpStatus.CREATED).build();
            }
            catch (Exception psqlException){
                log.info("Supplied values already exists");
                return ResponseEntity.status(HttpStatus.CONFLICT).body(customer);
            }
    }

    @GetMapping
    public ResponseEntity<Customer> findCustomerBasedOnEmailId(@RequestParam String emailId){
        try{
            Optional<Customer> custInfo= customerService.fetchUserInformation(emailId);
            if(custInfo.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

            Customer cust = custInfo.orElseThrow(() -> new NoSuchElementException("Customer not returned"));
            return ResponseEntity.status(HttpStatus.OK).body(cust);
        }
        catch (Exception e){
            log.info(e.toString());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PutMapping
    public ResponseEntity<Customer> updateCustomerBasedOnEmailId(@RequestBody Customer customer){
        try{
            customerService.updateUserBasedOnUserEmail(customer);
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        catch (Exception e){
            log.warn(e.toString());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    @PutMapping("resetPassword")
    public ResponseEntity<Customer> resetPassword(@RequestBody Customer customer){
        try{
            customerService.resetPassword(customer);
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        catch (Exception e){
            log.warn(e.toString());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

    }

    @DeleteMapping
    public ResponseEntity<Customer> deleteUserBasedOnEmailId(@RequestBody Customer customer){
        try{
            customerService.deleteAccount(customer);
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        catch (Exception e){
            log.warn(e.toString());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

    }
}
