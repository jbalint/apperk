package com.ii.sample.model;

import java.io.Serializable;

public class Address implements Serializable {
    private Long id;
    private String addressLine1;
    private String addressLine2;
    private String city;
    private String state;
    private String zipCode;

    /**
     * Sets Id
     *
     * @param    id                  a  Long
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Returns Id
     *
     * @return    a  Long
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets AddressLine1
     *
     * @param    addressLine1        a  String
     */
    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    /**
     * Returns AddressLine1
     *
     * @return    a  String
     */
    public String getAddressLine1() {
        return addressLine1;
    }

    /**
     * Sets AddressLine2
     *
     * @param    addressLine2        a  String
     */
    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    /**
     * Returns AddressLine2
     *
     * @return    a  String
     */
    public String getAddressLine2() {
        return addressLine2;
    }

    /**
     * Sets City
     *
     * @param    city                a  String
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * Returns City
     *
     * @return    a  String
     */
    public String getCity() {
        return city;
    }

    /**
     * Sets State
     *
     * @param    state               a  String
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * Returns State
     *
     * @return    a  String
     */
    public String getState() {
        return state;
    }

    /**
     * Sets ZipCode
     *
     * @param    zipCode             a  String
     */
    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    /**
     * Returns ZipCode
     *
     * @return    a  String
     */
    public String getZipCode() {
        return zipCode;
    }
}
