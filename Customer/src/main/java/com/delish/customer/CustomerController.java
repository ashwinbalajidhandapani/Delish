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
@RequestMapping("/customer")
public class CustomerController {
    private final CustomerService customerService;

    @GetMapping
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

    @GetMapping(path = "/find")
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
}
