package fh.technikum.sam.models;

import lombok.Data;

@Data
public class Customer {

        /** Unique identifier for the customer in the system. This is also used as the driver's ID. **/
        Long customerId;

        /** The username of the customer used for login purposes. **/
        String username;

        /** The customer's password. This should never be stored in plaintext. **/
        String password;

        /** The first name of the customer. **/
        String firstName;

        /** The surname (last name) of the customer. **/
        String surname;

        /** The age of the customer. **/
        Integer age;

        /** The unique driver's license number associated with the customer. **/
        Integer drivingLicenseNumber;

        /** The credit card number of the customer. This is securely stored and should comply with relevant standards. **/
        Integer creditCardNumber;

        /** Indicates if the customer is a fleet manager. **/
        Boolean isFleetManager;
}