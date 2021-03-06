package nl.fontys.sofa.limo.view.custom.panel;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import nl.fontys.sofa.limo.api.dao.ProcedureCategoryDAO;
import nl.fontys.sofa.limo.view.custom.procedure.AddStandardProcedureDialog;
import nl.fontys.sofa.limo.view.util.LIMOResourceBundle;
import org.openide.util.Lookup;

/**
 * This panel can be used by the wizards which uses procedures.
 *
 * @author Sven Mäurer
 */
public final class StandardProceduresPanel extends JPanel {

    private AddStandardProcedureDialog addProcedureDialog;
    protected ProcedureCategoryDAO procedureCatDao;

    public StandardProceduresPanel() {
        initComponents();
    }

    @Override
    public String getName() {
        return LIMOResourceBundle.getString("PROCEDURES");
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        procedureCatDao = Lookup.getDefault().lookup(ProcedureCategoryDAO.class);
        addProcedureDialog = new AddStandardProcedureDialog(procedureCatDao, null);
        addProcedureDialog.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        addProcedureDialog.setVisible(true);
        add(addProcedureDialog, BorderLayout.CENTER);
    }
}
