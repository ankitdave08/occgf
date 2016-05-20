package com.listertechnologies.occgf.api;

public class OccProfile {
    private String firstName;
    private String lastName;
    private String email;
    private OccShippingAddress shippingAddress;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public OccShippingAddress getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(OccShippingAddress shippingAddress) {
        this.shippingAddress = shippingAddress;
    }
}
