package com.ii.sample.model;

/**
 * Base class for entities that have a 'name' property.
 */
public class NamedEntity {
    private String name;

    /**
     * Sets Name
     *
     * @param    name                a  String
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns Name
     *
     * @return    a  String
     */
    public String getName() {
        return name;
    }
}
