package nl.fontys.sofa.limo.view.node;

import java.io.IOException;
import javax.swing.Action;
import nl.fontys.sofa.limo.api.dao.DAO;
import nl.fontys.sofa.limo.api.exception.ServiceNotFoundException;
import nl.fontys.sofa.limo.api.service.provider.ProcedureCategoryService;
import nl.fontys.sofa.limo.domain.component.procedure.ProcedureCategory;
import nl.fontys.sofa.limo.view.custom.panel.NameDescriptionDialogInputPanel;
import org.openide.DialogDescriptor;
import org.openide.DialogDisplayer;
import org.openide.actions.NewAction;
import org.openide.nodes.Children;
import org.openide.util.Lookup;
import org.openide.util.actions.SystemAction;
import org.openide.util.datatransfer.NewType;

/**
 * Root node for ProcedureCategory.
 *
 * @author Sebastiaan Heijmann
 */
public class ProcedureCategoryRootNode extends AbstractRootNode {

    private DAO service;

    public ProcedureCategoryRootNode(Children children) throws ServiceNotFoundException {
        super(children);
        service = Lookup.getDefault().lookup(ProcedureCategoryService.class);
    }

    @Override
    Class getBeanClass() {
        return ProcedureCategory.class;
    }

    @Override
    Class getServiceClass() {
        return ProcedureCategoryService.class;
    }

    @Override
    public Action[] getActions(boolean context) {
        return new Action[]{SystemAction.get(NewAction.class)};
    }

    @Override
    public NewType[] getNewTypes() {
        return new NewType[]{new NewType() {

            @Override
            public String getName() {
                return "Procedure Category";
            }

            @Override
            public void create() throws IOException {
                NameDescriptionDialogInputPanel inputPane = new NameDescriptionDialogInputPanel();
                DialogDescriptor dd = new DialogDescriptor(inputPane, "Procedure Category");
                DialogDisplayer.getDefault().notify(dd);

                String name = inputPane.getNameFieldValue();
                String description = inputPane.getDescriptionFieldValue();

                if (!name.isEmpty() && !description.isEmpty()) {
                    ProcedureCategory pc = new ProcedureCategory();
                    pc.setName(name);
                    pc.setDescription(description);
                    service.insert(pc);
                }
            }
        }};
    }
}
