package nl.fontys.sofa.limo.view.topcomponent;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import nl.fontys.sofa.limo.view.util.LIMOResourceBundle;
import org.netbeans.api.settings.ConvertAsProperties;
import org.openide.awt.ActionID;
import org.openide.util.Exceptions;
import org.openide.util.NbBundle.Messages;
import org.openide.windows.TopComponent;

/**
 * Top component which displays the start page.
 *
 * @author Sebastiaan Heijmann
 */
@ConvertAsProperties(
        dtd = "-//nl.fontys.sofa.limo.view.topcomponent//Startpage//EN",
        autostore = false
)
@TopComponent.Description(
        preferredID = "StartpageTopComponent",
        iconBase = "icons/gui/Home.png",
        persistenceType = TopComponent.PERSISTENCE_ALWAYS
)
@TopComponent.Registration(
        mode = "editor",
        openAtStartup = true
)
@ActionID(
        category = "Window",
        id = "nl.fontys.sofa.limo.view.topcomponent.StartpageTopComponent"
)
@TopComponent.OpenActionRegistration(
        displayName = "#CTL_StartpageAction",
        preferredID = "StartpageTopComponent"
)
@Messages({
    "CTL_StartpageAction=Startpage",
    "CTL_StartpageTopComponent=Startpage Window"
})
public final class StartpageTopComponent extends TopComponent {

    private JPanel contentPanel;
    private JPanel footerPane;
    private JPanel headerPane;
    private JLabel logoPane;
    private JLabel titleLB;
    private JLabel website;

    public StartpageTopComponent() {
        initComponents();
        setName(Bundle.CTL_StartpageTopComponent());
    }

    /**
     * Initialize the components of the startpage.
     */
    private void initComponents() {
        headerPane = new JPanel();
        titleLB = new JLabel();
        contentPanel = new JPanel();
        logoPane = new JLabel();
        footerPane = new JPanel();
        website = new JLabel("Visit http://limo.fontysvenlo.org for more information about LIMO");

        setBorder(BorderFactory.createEmptyBorder(25, 100, 75, 100));
        setDisplayName(LIMOResourceBundle.getString("STARTPAGE"));
        setMaximumSize(new java.awt.Dimension(10000, 5000));
        setMinimumSize(new java.awt.Dimension(700, 500));
        setName(LIMOResourceBundle.getString("STATPAGE"));
        setPreferredSize(new java.awt.Dimension(10000, 500));
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        headerPane.setBackground(new java.awt.Color(0, 70, 70));
        headerPane.setBorder(BorderFactory.createEmptyBorder(10, 1, 1, 1));
        headerPane.setMaximumSize(new java.awt.Dimension(10000, 60));
        headerPane.setMinimumSize(new java.awt.Dimension(600, 60));
        headerPane.setPreferredSize(new java.awt.Dimension(10000, 60));
        headerPane.setRequestFocusEnabled(false);

        titleLB.setFont(new java.awt.Font("DejaVu Serif", 1, 24));
        titleLB.setForeground(new Color(255, 65, 0));
        titleLB.setText("Logistics Impact Model");
        titleLB.setHorizontalTextPosition(SwingConstants.CENTER);
        headerPane.add(titleLB);
        add(headerPane);

        contentPanel.setBackground(new java.awt.Color(255, 255, 255));
        contentPanel.setMaximumSize(new java.awt.Dimension(10000, 300));
        contentPanel.setMinimumSize(new java.awt.Dimension(1000, 300));
        contentPanel.setPreferredSize(new java.awt.Dimension(10000, 300));
        contentPanel.setLayout(new java.awt.BorderLayout());

        logoPane.setHorizontalAlignment(SwingConstants.RIGHT);
        logoPane.setIcon(new ImageIcon(getClass().getResource("/nl/fontys/sofa/limo/view/images/logo.png")));
        logoPane.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 25));
        logoPane.setMaximumSize(new java.awt.Dimension(5000, 300));
        logoPane.setMinimumSize(new java.awt.Dimension(300, 300));
        logoPane.setPreferredSize(new java.awt.Dimension(400, 300));
        contentPanel.add(logoPane, java.awt.BorderLayout.CENTER);

        add(contentPanel);

        footerPane.setBackground(new java.awt.Color(0, 134, 136));
        footerPane.setMaximumSize(new java.awt.Dimension(10000, 40));
        footerPane.setMinimumSize(new java.awt.Dimension(600, 40));
        footerPane.setPreferredSize(new java.awt.Dimension(10000, 40));

        website.setFont(new java.awt.Font("DejaVu Serif", 1, 12));
        website.setForeground(Color.WHITE);
        website.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    Desktop.getDesktop().browse(new URI("http://limo.fontysvenlo.org"));
                } catch (IOException | URISyntaxException ex) {
                    Exceptions.printStackTrace(ex);
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });
        
        footerPane.add(website, java.awt.BorderLayout.CENTER);
        add(footerPane);
    }

    @Override
    public void componentOpened() {
        // TODO add custom code on component opening
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        Color upperColor = new Color(0x00868a);
        Color lowerColor = new Color(0xFFFFFF);
        GradientPaint gp = new GradientPaint(getWidth() / 2, 0, upperColor, getWidth() / 2, getHeight() / 2, lowerColor);
        g2d.setPaint(gp);
        g2d.fillRect(0, 0, getWidth(), getHeight());
    }

    @Override
    public void componentClosed() {
        // TODO add custom code on component closing
    }

    void writeProperties(java.util.Properties p) {
        // better to version settings since initial version as advocated at
        // http://wiki.apidesign.org/wiki/PropertyFiles
        p.setProperty("version", "1.0");
        // TODO store your settings
    }

    void readProperties(java.util.Properties p) {
        String version = p.getProperty("version");
        // TODO read your settings according to their version
    }
}
