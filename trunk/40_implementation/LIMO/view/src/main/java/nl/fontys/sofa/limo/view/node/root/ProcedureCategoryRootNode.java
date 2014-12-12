package nl.fontys.sofa.limo.view.node.root;

import java.io.IOException;
import javax.swing.JOptionPane;
import nl.fontys.sofa.limo.api.exception.ServiceNotFoundException;
import nl.fontys.sofa.limo.api.service.provider.ProcedureCategoryService;
import nl.fontys.sofa.limo.domain.component.procedure.ProcedureCategory;
import nl.fontys.sofa.limo.view.custom.panel.NameDescriptionDialogInputPanel;
import nl.fontys.sofa.limo.view.util.LIMOResourceBundle;
import org.openide.nodes.Children;
import org.openide.util.Lookup;
import org.openide.util.datatransfer.NewType;

/**
 * Root node for ProcedureCategory.
 *
 * @author Sebastiaan Heijmann
 */
public class ProcedureCategoryRootNode extends AbstractRootNode {
    
    private final ProcedureCategoryService service;
    
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
    public NewType[] getNewTypes() {
        return new NewType[]{new NewType() {
            
            @Override
            public String getName() {
                return LIMOResourceBundle.getString("PROCEDURE_CATEGORY");
            }
            
            @Override
            public void create() throws IOException {
                NameDescriptionDialogInputPanel inputPane = new NameDescriptionDialogInputPanel();
                int result = JOptionPane.showConfirmDialog(null, inputPane, LIMOResourceBundle.getString("ADD_PROCEDURE_CATEGORY"), JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null);

                String name = inputPane.getNameFieldValue();
                String description = inputPane.getDescriptionFieldValue();

                if (result == JOptionPane.OK_OPTION && !name.isEmpty() && !description.isEmpty()) {
                    ProcedureCategory pc = new ProcedureCategory();
                    pc.setName(name);
                    pc.setDescription(description);
                    service.insert(pc);
                }
            }
        }};
    }
}
