package nl.fontys.sofa.limo.domain.component.hub;

import com.google.gson.annotations.Expose;
import java.util.ArrayList;
import java.util.List;

/**
 * This software is licensed under the Apache 2 license, quoted below.
 *
 * Copyright 2013 Stephen Samuel, https://github.com/sksamuel/gaia
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 *
 * Changes made to project default: adding Implements Serializable
 *
 * @author Sven Mäurer
 */
public enum Continent implements Comparable<Continent> {

    Africa("Africa"),
    Antartica("Antartica"),
    Asia("Asia"),
    Europe("Europe"),
    NorthAmerica("North America"),
    SouthAmerica("South America"),
    Oceania("Oceania");

    @Expose private final String name;
    private final List<SerializableCountry> countries;

    private Continent(String name) {
        this.name = name;
        countries = new ArrayList<>();
    }

    /**
     * Returns a list of countries that reside in this continent (based on the
     * name which was given to it in the constructor).
     *
     * @return a list of countries
     */
    public List<SerializableCountry> getCountries() {

        if (countries.isEmpty()) {
            SerializableCountry.getAll().stream().filter((country) -> (country.getContinent() == this)).forEach((country) -> {
                countries.add(country);
            });
        }
        return countries;
    }

    public String getName() {
        return name;
    }
}
