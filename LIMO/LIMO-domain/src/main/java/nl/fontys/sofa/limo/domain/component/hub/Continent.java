package nl.fontys.sofa.limo.domain.component.hub;

import java.util.ArrayList;
import java.util.List;

public enum Continent implements Comparable<Continent> {

    Africa("Africa"),
    Antartica("Antartica"),
    Asia("Asia"),
    Europe("Europe"),
    NorthAmerica("North America"),
    SouthAmerica("South America"),
    Oceania("Oceania");

    private final String name;
    private List<SerializableCountry> countries;

    private Continent(String name) {
        this.name = name;
        countries = new ArrayList<>();
    }

    /**
     * Returns a list of countries that reside in this continent.
     */
    public List<SerializableCountry> getCountries() {

        if (countries.isEmpty()) {
            for (SerializableCountry country : SerializableCountry.getAll()) {

                if (country.getContinent() == this) {
                    countries.add(country);
                }
            }
        }
        return countries;
    }

    public String getName() {
        return name;
    }
}
