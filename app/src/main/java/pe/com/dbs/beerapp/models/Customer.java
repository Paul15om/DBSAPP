package pe.com.dbs.beerapp.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by JeralBenites on 26/02/2017.
 */

@JsonIgnoreProperties(ignoreUnknown=true)
public class Customer {

    private Integer customerId;
    private String pass;
    private String age;
    private String email;
    private Integer state;

    public Customer(String age, Integer customerId, String email, String pass, Integer state) {
        this.age = age;
        this.customerId = customerId;
        this.email = email;
        this.pass = pass;
        this.state = state;
    }

    public Customer(String email, String pass) {
        this.email = email;
        this.pass = pass;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }
}
