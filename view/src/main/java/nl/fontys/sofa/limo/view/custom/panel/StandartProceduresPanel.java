package nl.fontys.sofa.limo.view.custom.panel;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import nl.fontys.sofa.limo.api.dao.ProcedureDAO;
import nl.fontys.sofa.limo.view.custom.procedure.AddStandartProcedureDialog;
import nl.fontys.sofa.limo.view.util.LIMOResourceBundle;
import org.openide.util.Lookup;

/**
 * This panel can be used by the wizards which uses procedures.
 *
 * @author Sven Mäurer
 */
public final class StandartProceduresPanel extends JPanel {

    private AddStandartProcedureDialog addProcedureDialog;
    protected ProcedureDAO procedureDao;
    
    public StandartProceduresPanel() {
        initComponents();
    }

    @Override
    public String getName() {
        return LIMOResourceBundle.getString("PROCEDURES");
    }

    private void initComponents() {
        setLayout(new BorderLayout());
        
        procedureDao = Lookup.getDefault().lookup(ProcedureDAO.class);
        addProcedureDialog = new AddStandartProcedureDialog(procedureDao, null);
        addProcedureDialog.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        addProcedureDialog.setVisible(true);
        add(addProcedureDialog, BorderLayout.CENTER);
    }
}
