package nl.fontys.sofa.limo.domain.location;

import java.io.Serializable;

public class Location implements Serializable {

    private Continents continent;
    private String country;
    private String state;
    private String town;
    private String postcode;
    private String street;
    private String housenumber;

    /**
     * GPS
     */
    private float latitude;
    private float longitude;

    public Location() {
    }

    public Location(Continents continent) {
        this.continent = continent;
    }

    public Location(Continents continent, float latitude, float longitude) {
        this.continent = continent;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Location(Continents continent, String country) {
        this.continent = continent;
        this.country = country;
    }

    public Location(Continents continent, String country, float latitude, float longitude) {
        this.continent = continent;
        this.country = country;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Location(Continents continent, String country, String state) {
        this.continent = continent;
        this.country = country;
        this.state = state;
    }

    public Location(Continents continent, String country, String state, float latitude, float longitude) {
        this.continent = continent;
        this.country = country;
        this.state = state;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Location(Continents continent, String country, String state, String town) {
        this.continent = continent;
        this.country = country;
        this.state = state;
        this.town = town;
    }

    public Location(Continents continent, String country, String state, String town, float latitude, float longitude) {
        this.continent = continent;
        this.country = country;
        this.state = state;
        this.town = town;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Location(Continents continent, String country, String state, String town, String postcode) {
        this.continent = continent;
        this.country = country;
        this.state = state;
        this.town = town;
        this.postcode = postcode;
    }

    public Location(Continents continent, String country, String state, String town, String postcode, float latitude, float longitude) {
        this.continent = continent;
        this.country = country;
        this.state = state;
        this.town = town;
        this.postcode = postcode;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Location(Continents continent, String country, String state, String town, String postcode, String street) {
        this.continent = continent;
        this.country = country;
        this.state = state;
        this.town = town;
        this.postcode = postcode;
        this.street = street;
    }

    public Location(Continents continent, String country, String state, String town, String postcode, String street, float latitude, float longitude) {
        this.continent = continent;
        this.country = country;
        this.state = state;
        this.town = town;
        this.postcode = postcode;
        this.street = street;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Location(Continents continent, String country, String state, String town, String postcode, String street, String housenumber) {
        this.continent = continent;
        this.country = country;
        this.state = state;
        this.town = town;
        this.postcode = postcode;
        this.street = street;
        this.housenumber = housenumber;
    }

    public Location(Continents continent, String country, String state, String town, String postcode, String street, String housenumber, float latitude, float longitude) {
        this.continent = continent;
        this.country = country;
        this.state = state;
        this.town = town;
        this.postcode = postcode;
        this.street = street;
        this.housenumber = housenumber;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    // <editor-fold defaultstate="collapsed" desc=" ${GETTERS AND SETTERS} ">
    public Continents getContinent() {
        return continent;
    }

    public void setContinent(Continents continent) {
        this.continent = continent;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getHousenumber() {
        return housenumber;
    }

    public void setHousenumber(String housenumber) {
        this.housenumber = housenumber;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }
}
