package nl.fontys.sofa.limo.view.node.property.editor;

import com.sksamuel.gaia.Country;
import java.beans.PropertyEditorSupport;
import java.util.HashMap;
import java.util.List;
import org.openide.nodes.PropertyEditorRegistration;

/**
 *
 * @author Dominik Kaisers <d.kaisers@student.fontys.nl>
 */
@PropertyEditorRegistration(targetType = Country.class)
public class CountryPopertyEditor extends PropertyEditorSupport{
    
    private HashMap<String, Country> countriesByName = new HashMap<>();

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        if (text.equals("-"))
            setValue(null);
        
        setValue(countriesByName.get(text));
    }

    @Override
    public String getAsText() {
        if (getValue() == null)
            return "-";
        
        return ((Country) getValue()).getName();
    }

    @Override
    public String[] getTags() {
        String[] names = new String[Country.getAll().size() + 1];
        List<Country> selectable = Country.getAll();
        countriesByName.clear();
        names[0] = "-";
        for (Country country : selectable) {
            names[selectable.indexOf(country) + 1] = country.getName();
            countriesByName.put(country.getName(), country);
        }
        return names;
    }
    
}