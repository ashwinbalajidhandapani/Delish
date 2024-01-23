package com.delish.customer;

import com.delish.customer.Exceptions.DataUnchangedException;
import com.delish.customer.utils.Utility;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@AllArgsConstructor
@Service
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final JdbcTemplate jdbcTemplate;
    private Utility utility;

    // Retrieves all customers
    public List<Customer> retrieveAllCustomers(){
        return customerRepository.findAll();
    }

    // Method to check if an Id exist already
    public boolean ifIdExists(Long id){
        List<Customer> customers = retrieveAllCustomers();
        for (Customer c: customers){
            if(c.getId() == id){
                return true;
            }
        }
        return false;
    }

    // inserts a customer information to DB
    public Customer saveCustomer(Customer customer){
        Long id = utility.generateRandomId();
        while(ifIdExists(id)){
            id = utility.generateRandomId();
        }
        if(utility.validateEmail(customer.getEmailid())){
            Customer customerInfo = Customer.builder()
                    .id(id)
                    .emailid(customer.getEmailid())
                    .phone(customer.getPhone())
                    .username(customer.getUsername())
                    .password(customer.getPassword())
                    .build();
            return customerRepository.saveAndFlush(customerInfo);
        }
        return null;
    }

    // find a customer based on emailId
    public Optional<Customer> fetchUserInformation(String emailId){
        List<Customer> result = jdbcTemplate.query(
          "SELECT username, phone FROM customer WHERE emailid=? LIMIT 1",
                new CustomerRowMapper(), emailId
        );

        return result.stream().findFirst();
    }

    public Optional<Customer> fetchUserInformationComplete(String emailId){
        List<Customer> result = jdbcTemplate.query(
                "SELECT id, username, phone, emailid, password FROM customer WHERE emailid=? LIMIT 1",
                new CustomerRowMapperId(), emailId
        );

        return result.stream().findFirst();
    }


    public void updateUserBasedOnUserEmail(Customer customer){
        // this makes an initial check if the customer is present within the database and gets information for comparison
        Customer custInfo = fetchUserInformationComplete(customer.getEmailid()).orElseThrow(() -> new NoSuchElementException("Customer not returned"));
        if(!custInfo.getUsername().equals(customer.getUsername()) && custInfo.getPhone().equals(customer.getPhone())){
            // username is changed by the user
            jdbcTemplate.update("UPDATE customer SET username = ? WHERE emailid=?", customer.getUsername(), customer.getEmailid());
        }

        else if(custInfo.getUsername().equals(customer.getUsername()) && !custInfo.getPhone().equals(customer.getPhone())){
            // username is changed by the user
            jdbcTemplate.update("UPDATE customer SET phone = ? WHERE emailid=?", customer.getPhone(), customer.getEmailid());
        }

        else if(custInfo.getUsername().equals(customer.getUsername()) && custInfo.getPhone().equals(customer.getPhone())){
            throw new DataUnchangedException("No change in Data, please make changes to either phone or username");
        }

    }

    public void resetPassword(Customer customer) throws RuntimeException{
        Customer custInfoRetrievedFromDB = fetchUserInformationComplete(
                customer.getEmailid())
                .orElseThrow(()-> new NoSuchElementException("No customer data found"));
        System.out.println(custInfoRetrievedFromDB.getPassword());
        if(custInfoRetrievedFromDB.getPassword().equals(customer.getPassword())){
            throw new DataUnchangedException("New password is same as the old password");
        }
        else{
            try{
                jdbcTemplate.update("UPDATE customer SET password=? WHERE emailid = ?", customer.getPassword(), customer.getEmailid());
            }
            catch (Exception e){
                e.toString();
            }

        }
    }

    public void deleteAccount(Customer customer){
        Customer customerRetrivedFromDb = fetchUserInformationComplete(customer.getEmailid()).orElseThrow(() -> new NoSuchElementException());
        if(customer.getEmailid().equals(customerRetrivedFromDb.getEmailid()) && customer.getPassword().equals(customerRetrivedFromDb.getPassword())){
            jdbcTemplate.update("DELETE FROM customer WHERE emailid=?", customer.getEmailid());
        }
    }

    public static class CustomerRowMapper implements RowMapper<Customer>{

        @Override
        public Customer mapRow(ResultSet rs, int rowNum) throws SQLException {
            return Customer.builder()
                    .username(rs.getString("username"))
                    .phone(rs.getString("phone"))
                    .build();
        }
    }

    public static class CustomerRowMapperId implements RowMapper<Customer>{

        @Override
        public Customer mapRow(ResultSet rs, int rowNum) throws SQLException {
            return Customer.builder()
                    .id(rs.getLong("id"))
                    .username(rs.getString("username"))
                    .emailid(rs.getString("emailid"))
                    .phone(rs.getString("phone"))
                    .password(rs.getString("password"))
                    .build();
        }
    }



}
