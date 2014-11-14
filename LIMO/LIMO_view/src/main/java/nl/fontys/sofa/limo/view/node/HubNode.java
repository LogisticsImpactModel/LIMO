package nl.fontys.sofa.limo.view.node;

import com.sksamuel.gaia.Continent;
import com.sksamuel.gaia.Country;
import java.awt.event.ActionEvent;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import nl.fontys.sofa.limo.api.service.provider.HubService;
import nl.fontys.sofa.limo.domain.component.Icon;
import nl.fontys.sofa.limo.domain.component.hub.Hub;
import nl.fontys.sofa.limo.view.node.property.StupidProperty;
import nl.fontys.sofa.limo.view.node.property.editor.EventPropertyEditor;
import nl.fontys.sofa.limo.view.node.property.editor.IconPropertyEditor;
import nl.fontys.sofa.limo.view.wizard.hub.HubWizardAction;
import org.openide.ErrorManager;
import org.openide.nodes.Sheet;
import org.openide.util.Lookup;

/**
 * View representation of Hub.
 *
 * @author Sebastiaan Heijmann
 */
public class HubNode extends AbstractBeanNode<Hub> {

    private final Hub bean;

    public HubNode(Hub bean) throws IntrospectionException {
        super(bean, Hub.class);
        this.bean = bean;
    }

    @Override
    public boolean canDestroy() {
        return true;
    }

    @Override
    public Action[] getActions(boolean context) {
        ArrayList<Action> actionList = new ArrayList<>();
        actionList.add(new AbstractAction("Edit") {
            @Override
            public void actionPerformed(ActionEvent e) {
                HubWizardAction wiz = new HubWizardAction();
                wiz.isUpdate(bean);
                wiz.actionPerformed(e);
                createProperties(getBean(), null);
                setSheet(getSheet());
            }
        });
        actionList.add(new AbstractAction("Delete") {

            @Override
            public void actionPerformed(ActionEvent e) {
                HubService service = Lookup.getDefault().lookup(HubService.class);
                service.delete(bean);
            }
        });
        return actionList.toArray(new Action[actionList.size()]);
    }

    @Override
    protected void createProperties(Hub bean, BeanInfo info) {
        super.createProperties(bean, info);

        Sheet sets = getSheet();
        Sheet.Set generalSet = Sheet.createPropertiesSet();
        generalSet.setName("properties");
        generalSet.setDisplayName("Properties");

        try {
            StupidProperty name = new StupidProperty<>(getBean(), String.class, "name");
            name.addPropertyChangeListener(getListener());
            name.setDisplayName("Name");
            name.setShortDescription("The name of the procedure category.");

            StupidProperty description = new StupidProperty<>(getBean(), String.class, "description");
            description.addPropertyChangeListener(getListener());
            description.setDisplayName("Description");
            description.setShortDescription("An optional short description of the procedure category.");

            StupidProperty iconProp = new StupidProperty(getBean(), Icon.class, "icon");
            iconProp.addPropertyChangeListener(getListener());
            iconProp.setPropertyEditorClass(IconPropertyEditor.HubIconPropertyEditor.class);
            iconProp.setDisplayName("Icon");
            iconProp.setShortDescription("The icon that gets displayed with this Leg-Type.");
            iconProp.setValue("valueIcon", new ImageIcon(getBean().getIcon().getImage()));
            iconProp.setValue("canEditAsText", false);

            StupidProperty eventProp = new StupidProperty(getBean(), List.class, "events");
            eventProp.addPropertyChangeListener(getListener());
            eventProp.setPropertyEditorClass(EventPropertyEditor.class);
            eventProp.setDisplayName("Event");
            eventProp.setShortDescription("All Events stored with this Hub.");
            eventProp.setValue("canEditAsText", false);

            generalSet.put(name);
            generalSet.put(description);
            generalSet.put(iconProp);
            generalSet.put(eventProp);
        } catch (NoSuchMethodException ex) {
            ErrorManager.getDefault();
        }

        Sheet.Set locationSet = Sheet.createPropertiesSet();
        locationSet.setName("location");
        locationSet.setDisplayName("Location");

        try {
            StupidProperty continent = new StupidProperty(getBean().getLocation(), Continent.class, "continent");
            continent.addPropertyChangeListener(getListener());
            continent.setName("continent");
            continent.setDisplayName("Continent");

            StupidProperty country = new StupidProperty(getBean().getLocation(), Country.class, "country");
            country.addPropertyChangeListener(getListener());
            country.setName("country");
            country.setDisplayName("Country");

            StupidProperty state = new StupidProperty(getBean().getLocation(), String.class, "state");
            state.addPropertyChangeListener(getListener());
            state.setName("state");
            state.setDisplayName("State");

            StupidProperty town = new StupidProperty(getBean().getLocation(), String.class, "town");
            town.addPropertyChangeListener(getListener());
            town.setName("town");
            town.setDisplayName("Town");

            StupidProperty postcode = new StupidProperty(getBean().getLocation(), String.class, "postcode");
            postcode.addPropertyChangeListener(getListener());
            postcode.setName("postcode");
            postcode.setDisplayName("Postcode");

            StupidProperty street = new StupidProperty(getBean().getLocation(), String.class, "street");
            street.addPropertyChangeListener(getListener());
            street.setName("street");
            street.setDisplayName("Street");

            StupidProperty housenumber = new StupidProperty(getBean().getLocation(), String.class, "housenumber");
            housenumber.addPropertyChangeListener(getListener());
            housenumber.setName("housenumber");
            housenumber.setDisplayName("House number");

            locationSet.put(continent);
            locationSet.put(country);
            locationSet.put(state);
            locationSet.put(town);
            locationSet.put(postcode);
            locationSet.put(street);
            locationSet.put(housenumber);
        } catch (NoSuchMethodException ex) {
            ErrorManager.getDefault();
        }

        sets.put(generalSet);
        sets.put(locationSet);
    }
}
