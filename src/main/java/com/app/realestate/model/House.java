package com.app.realestate.model;

import jakarta.persistence.*;

@Entity
@Table(name="HOUSES")
public class House {

    @Id
    @Column(name = "property_id")
    private String propertyId;

    @Column(name = "type")
    private String type;

    @Column(name = "description")
    private String description;

    @Column(name = "list_price")
    private int listPrice;

    @Column(name = "last_sold_price")
    private int lastSoldPrice;

    @Column(name = "sqft")
    private int sqft;

    @Column(name = "stories")
    private int numOfStories;

    @Column(name = "beds")
    private int numOfRoms;

    @Column(name = "baths")
    private int numOfBaths;

    @Column(name = "garage")
    private int numOfGarages;

    @Column(name = "zip_code")
    private String zipCode;

    @Column(name = "city")
    private String cityName;

    // Getters & Setters
    public String getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(String propertyId) {
        this.propertyId = propertyId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getListPrice() {
        return listPrice;
    }

    public void setListPrice(int listPrice) {
        this.listPrice = listPrice;
    }

    public int getLastSoldPrice() {
        return lastSoldPrice;
    }

    public void setLastSoldPrice(int lastSoldPrice) {
        this.lastSoldPrice = lastSoldPrice;
    }

    public int getSqft() {
        return sqft;
    }

    public void setSqft(int sqft) {
        this.sqft = sqft;
    }

    public int getNumOfStories() {
        return numOfStories;
    }

    public void setNumOfStories(int numOfStories) {
        this.numOfStories = numOfStories;
    }

    public int getNumOfRoms() {
        return numOfRoms;
    }

    public void setNumOfRoms(int numOfRoms) {
        this.numOfRoms = numOfRoms;
    }

    public int getNumOfBaths() {
        return numOfBaths;
    }

    public void setNumOfBaths(int numOfBaths) {
        this.numOfBaths = numOfBaths;
    }

    public int getNumOfGarages() {
        return numOfGarages;
    }

    public void setNumOfGarages(int numOfGarages) {
        this.numOfGarages = numOfGarages;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    @Override
    public String toString() {
        return "House{" +
                "propertyId='" + propertyId + '\'' +
                ", type='" + type + '\'' +
                ", description='" + description + '\'' +
                ", numOfRoms=" + numOfRoms +
                ", numOfBaths=" + numOfBaths +
                ", sqft=" + sqft +
                ", numOfStories=" + numOfStories +
                ", numOfGarages=" + numOfGarages +
                ", listPrice=" + listPrice +
                ", lastSoldPrice=" + lastSoldPrice +
                ", zipCode='" + zipCode + '\'' +
                ", cityName='" + cityName + '\'' +
                '}';
    }
}
