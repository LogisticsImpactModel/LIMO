package nl.fontys.sofa.limo.view.custom;

import java.awt.BorderLayout;
import java.util.Collection;
import javax.swing.JFrame;
import nl.fontys.sofa.limo.api.dao.DAOFactory;
import org.openide.util.Lookup;
import org.openide.util.lookup.Lookups;

public class NewClass {

    public static void main(String[] args) {
        JFrame frame = new JFrame("TabbedPaneDemo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(java.awt.Frame.MAXIMIZED_BOTH);

        //Add content to the window.
        frame.setLayout(new BorderLayout());
        frame.add(new TabbedComponentEntries(), BorderLayout.CENTER);

        //Display the window.
        frame.pack();
        frame.setVisible(true);

        Collection<? extends DAOFactory> lookupAll = Lookup.getDefault().lookupAll(DAOFactory.class);
        DAOFactory factory = (DAOFactory) lookupAll.toArray()[0];
        factory.getIconDAO();
        DAOFactory lookup = Lookup.getDefault().lookup(DAOFactory.class);
        lookup.getIconDAO();
    }
}
