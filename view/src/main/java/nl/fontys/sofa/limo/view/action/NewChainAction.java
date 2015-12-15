package nl.fontys.sofa.limo.view.action;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import javax.swing.AbstractAction;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import nl.fontys.sofa.limo.domain.component.SupplyChain;
import nl.fontys.sofa.limo.view.project.SupplyProject;
import nl.fontys.sofa.limo.view.project.node.SupplyChainNode;
import nl.fontys.sofa.limo.view.topcomponent.ChainBuilderTopComponent;
import nl.fontys.sofa.limo.view.util.LIMOResourceBundle;
import org.openide.DialogDescriptor;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.loaders.DataObject;
import org.openide.nodes.Node;
import org.openide.util.Exceptions;
import org.openide.util.NbBundle.Messages;
import org.openide.util.Utilities;

/**
 *
 * Just an action which does not open the top component if you discarded
 * entering the name for a chain.
 *
 * @author Sven Mäurer
 */
@ActionID(
        category = "Window",
        id = "nl.fontys.sofa.limo.view.action.NewChainAction"
)
@ActionRegistration(
        iconBase = "icons/gui/Link_Add.png",
        displayName = "#CTL_NewChainAction"
)
@ActionReferences({
    @ActionReference(path = "Menu/File", position = 2),
    @ActionReference(path = "Shortcuts", name = "D-N"),
    @ActionReference(path = "Toolbars/File", position = 10)
})
@Messages({
    "CTL_NewChainAction=New Supply Chain..."
})
public final class NewChainAction extends AbstractAction {

    private SupplyProject project;

    @Override
    public boolean isEnabled() {
        return true;
    }

    public NewChainAction() {
        super();
        this.project = null;
    }

    public NewChainAction(SupplyProject project) {
        this.project = project;
        putValue(NAME, "New Supply Chain");
        putValue("iconBase", "icons/gui/Link_add.png");
    }

    /* TO-DO: At project support */
    private DialogDescriptor dialog;

    @Override
    public void actionPerformed(ActionEvent e) {
        project = Utilities.actionsGlobalContext().lookup(SupplyProject.class);
        if (project != null) {
            JPanel panel = new JPanel(new BorderLayout());
            panel.setPreferredSize(new Dimension(250, 50));
            JPanel p = new JPanel();
            p.setLayout(new BoxLayout(p, BoxLayout.X_AXIS));
            JTextField text = new JTextField();
            JLabel label1 = new JLabel(" " + LIMOResourceBundle.getString("SET_NAME_OF", LIMOResourceBundle.getString("SUPPLY_CHAIN")) + "  ");
            JLabel error = new JLabel(" ");
            error.setForeground(Color.red);
            p.add(label1);
            p.add(text);
            panel.add(p, BorderLayout.NORTH);
            panel.add(error, BorderLayout.SOUTH);
            dialog = new DialogDescriptor(panel, LIMOResourceBundle.getString("NAME"), true, (ActionEvent e1) -> {
                if (e1.getSource() == DialogDescriptor.CANCEL_OPTION) {
                    dialog.setClosingOptions(null);
                } else if (e1.getSource() == DialogDescriptor.OK_OPTION) {
                    String s = text.getText();
                    FileObject obj = project.getProjectDirectory().getFileObject("chains/" + s + ".lsc");
                    if (obj == null) {
                        dialog.setClosingOptions(null);
                        ChainBuilderTopComponent chainBuilderTopComponent = new ChainBuilderTopComponent(s);
                        try {
                            SupplyChain chain = chainBuilderTopComponent.getChain();
                            String path = project.getProjectDirectory().getFileObject("chains").getPath();
                            path = path + "/" + chain.getName() + ".lsc";
                            chain.saveToFile(path);
                            FileObject fo = FileUtil.toFileObject(new File(path));
                            DataObject data = DataObject.find(fo);
                            Node node = new SupplyChainNode(data);
                            project.getChainNodeList().getChainNode().addChild(node);
                            node.getPreferredAction().actionPerformed(new ActionEvent(this, ActionEvent.ACTION_FIRST, "new"));
                        } catch (IOException ex) {
                            Exceptions.printStackTrace(ex);
                        }

                    } else {
                        error.setText(" Chain with this name exsits already in the project! ");
                    }
                }
            });
            dialog.setClosingOptions(new Object[]{});
            DialogDisplayer.getDefault().notifyLater(dialog);
        } else {
            NotifyDescriptor.InputLine dd
                    = new DialogDescriptor.InputLine(
                            LIMOResourceBundle.getString("NAME"),
                            LIMOResourceBundle.getString("SET_NAME_OF",
                                    LIMOResourceBundle.getString("SUPPLY_CHAIN")));

            if (DialogDisplayer.getDefault().notify(dd) == NotifyDescriptor.OK_OPTION) {
                String name = dd.getInputText();
                ChainBuilderTopComponent chainBuilderTopComponent = new ChainBuilderTopComponent(name);
                chainBuilderTopComponent.open();
                chainBuilderTopComponent.requestActive();
            }
            if (project != null) {

            } else {
            }

        }
    }

}
