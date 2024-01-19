package com.delish.customer;

import com.delish.customer.utils.Utility;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
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
            System.out.println("#########"+customer.getEmailid()+"#########");

            Customer customerInfo = Customer.builder()
                    .id(id)
                    .emailid(customer.getEmailid())
                    .phone(customer.getPhone())
                    .username(customer.getUsername())
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
        for (Customer c: result) {
            System.out.println(c.toString());
        }
        return result.stream().findFirst();
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
}
