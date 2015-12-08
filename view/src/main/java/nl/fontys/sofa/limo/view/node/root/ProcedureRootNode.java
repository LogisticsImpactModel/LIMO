package nl.fontys.sofa.limo.view.node.root;

import java.io.IOException;
import javax.swing.JOptionPane;
import nl.fontys.sofa.limo.api.exception.ServiceNotFoundException;
import nl.fontys.sofa.limo.api.service.provider.ProcedureService;
import nl.fontys.sofa.limo.domain.component.procedure.Procedure;
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
public class ProcedureRootNode extends AbstractRootNode {

    private final ProcedureService service;

    /**
     * Constructor creates a new ProcedureCategoryRootNode.
     *
     * @param children the children of the rootnode.
     * @throws ServiceNotFoundException
     */
    public ProcedureRootNode(Children children) throws ServiceNotFoundException {
        super(children);
        service = Lookup.getDefault().lookup(ProcedureService.class);
    }

    @Override
    Class getBeanClass() {
        return Procedure.class;
    }

    @Override
    Class getServiceClass() {
        return ProcedureService.class;
    }

    @Override
    public NewType[] getNewTypes() {
        return new NewType[]{new NewType() {

            @Override
            public String getName() {
                return LIMOResourceBundle.getString("PROCEDURE");
            }

            @Override
            public void create() throws IOException {
                NameDescriptionDialogInputPanel inputPane = new NameDescriptionDialogInputPanel();
                int result = JOptionPane.showConfirmDialog(null, inputPane, LIMOResourceBundle.getString("ADD_PROCEDURE"), JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null);

                String name = inputPane.getNameFieldValue();
                String description = inputPane.getDescriptionFieldValue();

                if (result == JOptionPane.OK_OPTION && !name.isEmpty()) {
                    Procedure pc = new Procedure();
                    pc.setName(name);
                    pc.setDescription(description);
                    service.insert(pc);
                }
            }
        }};
    }
}
