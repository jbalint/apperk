package com.ii.sample.model;

public class Person extends NamedEntity {
    private Address address;
    private Long id;
    private String nickname;
    private Integer age;

    /**
     * Sets Address
     *
     * @param    address             an Address
     */
    public void setAddress(Address address) {
        this.address = address;
    }

    /**
     * Returns Address
     *
     * @return    an Address
     */
    public Address getAddress() {
        return address;
    }

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
     * Sets Nickname
     *
     * @param    nickname            a  String
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    /**
     * Returns Nickname
     *
     * @return    a  String
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * Sets Age
     *
     * @param    age                 an Integer
     */
    public void setAge(Integer age) {
        this.age = age;
    }

    /**
     * Returns Age
     *
     * @return    an Integer
     */
    public Integer getAge() {
        return age;
    }
}
