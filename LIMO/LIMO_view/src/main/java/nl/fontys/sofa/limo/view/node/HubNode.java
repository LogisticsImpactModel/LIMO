package nl.fontys.sofa.limo.view.node;

import java.awt.event.ActionEvent;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.util.ArrayList;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import nl.fontys.sofa.limo.api.service.provider.HubService;
import nl.fontys.sofa.limo.domain.component.Icon;
import nl.fontys.sofa.limo.domain.component.hub.Hub;
import nl.fontys.sofa.limo.view.node.property.StupidProperty;
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

    private Hub bean;

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
                wiz.isUpdate(true, bean);
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
        //super.createProperties(bean, info);

        Sheet sets = getSheet();
        Sheet.Set set = Sheet.createPropertiesSet();

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
            iconProp.setDisplayName("Icon");
            iconProp.setShortDescription("The icon that gets displayed with this Leg-Type.");
            iconProp.setValue("valueIcon", new ImageIcon(getBean().getIcon().getImage()));
            iconProp.setValue("canEditAsText", false);

            set.put(name);
            set.put(description);
            set.put(iconProp);
        } catch (NoSuchMethodException ex) {
            ErrorManager.getDefault();
        }
        sets.put(set);
    }
}
