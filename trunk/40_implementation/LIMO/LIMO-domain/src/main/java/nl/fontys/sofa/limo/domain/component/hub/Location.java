package nl.fontys.sofa.limo.domain.component.hub;

import com.sksamuel.gaia.Continent;
import com.sksamuel.gaia.Country;
import java.io.Serializable;
import javax.persistence.Embedded;

/**
 * The location of a hub is stored in this class. Only continent is mandatory.
 *
 * @author Dominik Kaisers <d.kaisers@student.fontys.nl>
 */
public class Location implements Serializable {
    
    private Continent continent;
    private Country country;
    private String state;
    private String town;
    private String postcode;
    private String street;
    private String housenumber;
    @Embedded
    private Coordinate position;

    public Location() {
    }

    public Continent getContinent() {
        return continent;
    }

    public void setContinent(Continent continent) {
        this.continent = continent;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
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

    public Coordinate getPosition() {
        return position;
    }

    public void setPosition(Coordinate position) {
        this.position = position;
    }
    
    /**
     * Private class for storage of a geo location in form of latitude, longitude and elevation.
     */
    public static class Coordinate implements Serializable {
        
        private double latitude;
        private double longitude;
        private double elevation;

        public Coordinate() {
            this(0, 0, 0);
        }

        public Coordinate(double latitude, double longitude) {
            this(latitude, longitude, 0);
        }

        public Coordinate(double latitude, double longitude, double elevation) {
            this.latitude = latitude;
            this.longitude = longitude;
            this.elevation = elevation;
        }

        public double getLatitude() {
            return latitude;
        }

        public void setLatitude(double latitude) {
            this.latitude = latitude;
        }

        public double getLongitude() {
            return longitude;
        }

        public void setLongitude(double longitude) {
            this.longitude = longitude;
        }

        public double getElevation() {
            return elevation;
        }

        public void setElevation(double elevation) {
            this.elevation = elevation;
        }
        
    }
    
}
