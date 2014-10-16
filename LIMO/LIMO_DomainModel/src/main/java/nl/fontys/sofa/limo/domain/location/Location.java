package nl.fontys.sofa.limo.domain.location;

import nl.fontys.sofa.limo.domain.interfaces.Copyable;

public class Location implements Copyable<Location> {

    private Continents continent;
    private CountryCode country;
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
        continent = Continents.EUROPE;
    }

    public Location(Continents continent) {
        this.continent = continent;
    }

    public Location(Continents continent, float latitude, float longitude) {
        this.continent = continent;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Location(Continents continent, CountryCode country) {
        this.continent = continent;
        this.country = country;
    }

    public Location(Continents continent, CountryCode country, float latitude, float longitude) {
        this.continent = continent;
        this.country = country;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Location(Continents continent, CountryCode country, String state) {
        this.continent = continent;
        this.country = country;
        this.state = state;
    }

    public Location(Continents continent, CountryCode country, String state, float latitude, float longitude) {
        this.continent = continent;
        this.country = country;
        this.state = state;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Location(Continents continent, CountryCode country, String state, String town) {
        this.continent = continent;
        this.country = country;
        this.state = state;
        this.town = town;
    }

    public Location(Continents continent, CountryCode country, String state, String town, float latitude, float longitude) {
        this.continent = continent;
        this.country = country;
        this.state = state;
        this.town = town;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Location(Continents continent, CountryCode country, String state, String town, String postcode) {
        this.continent = continent;
        this.country = country;
        this.state = state;
        this.town = town;
        this.postcode = postcode;
    }

    public Location(Continents continent, CountryCode country, String state, String town, String postcode, float latitude, float longitude) {
        this.continent = continent;
        this.country = country;
        this.state = state;
        this.town = town;
        this.postcode = postcode;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Location(Continents continent, CountryCode country, String state, String town, String postcode, String street) {
        this.continent = continent;
        this.country = country;
        this.state = state;
        this.town = town;
        this.postcode = postcode;
        this.street = street;
    }

    public Location(Continents continent, CountryCode country, String state, String town, String postcode, String street, float latitude, float longitude) {
        this.continent = continent;
        this.country = country;
        this.state = state;
        this.town = town;
        this.postcode = postcode;
        this.street = street;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Location(Continents continent, CountryCode country, String state, String town, String postcode, String street, String housenumber) {
        this.continent = continent;
        this.country = country;
        this.state = state;
        this.town = town;
        this.postcode = postcode;
        this.street = street;
        this.housenumber = housenumber;
    }

    public Location(Continents continent, CountryCode country, String state, String town, String postcode, String street, String housenumber, float latitude, float longitude) {
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

    public CountryCode getCountry() {
        return country;
    }

    public void setCountry(CountryCode country) {
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
    //</editor-fold>

    @Override
    public Location copy() {
        Location copied = new Location();
        copied.setContinent(continent);
        copied.setCountry(country);
        copied.setHousenumber(housenumber);
        copied.setLatitude(latitude);
        copied.setLongitude(longitude);
        copied.setPostcode(postcode);
        copied.setState(state);
        copied.setStreet(street);
        copied.setTown(town);
        return copied;
    }
}