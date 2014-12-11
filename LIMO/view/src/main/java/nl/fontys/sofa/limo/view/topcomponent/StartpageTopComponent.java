package nl.fontys.sofa.limo.view.topcomponent;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import org.netbeans.api.settings.ConvertAsProperties;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.util.NbBundle.Messages;
import org.openide.windows.TopComponent;

/**
 * Top component which displays the startpage.
 *
 * @author Sebastiaan Heijmann
 */
@ConvertAsProperties(
        dtd = "-//nl.fontys.sofa.limo.view.topcomponent//Startpage//EN",
        autostore = false
)
@TopComponent.Description(
        preferredID = "StartpageTopComponent",
        iconBase = "icons/gui/start.gif",
        persistenceType = TopComponent.PERSISTENCE_ALWAYS
)
@TopComponent.Registration(mode = "editor", openAtStartup = true)
@ActionID(category = "Window", id = "nl.fontys.sofa.limo.view.topcomponent.StartpageTopComponent")
@ActionReference(path = "Menu/Window", position = 10)
@TopComponent.OpenActionRegistration(
        displayName = "#CTL_StartpageAction",
        preferredID = "StartpageTopComponent"
)
@Messages({
    "CTL_StartpageAction=Startpage",
    "CTL_StartpageTopComponent=Startpage Window",
    "HINT_StartpageTopComponent=Welcome to LIMO"
})
public final class StartpageTopComponent extends TopComponent {

    public StartpageTopComponent() {
        initComponents();
        setName(Bundle.CTL_StartpageTopComponent());
        setToolTipText(Bundle.HINT_StartpageTopComponent());

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    private void initComponents() {

        headerPane = new javax.swing.JPanel();
        titleLB = new javax.swing.JLabel();
        contentPanel = new javax.swing.JPanel();
        logoPane = new javax.swing.JLabel();
        linkPane = new javax.swing.JPanel();
        buttonPane = new javax.swing.JPanel();
//        testButton2BT = new StartPageButton("Test1");
//        jButton1 = new StartPageButton("Test2");
//        jButton2 = new StartPageButton("Test3");
        footerPane = new javax.swing.JPanel();

        setBorder(javax.swing.BorderFactory.createEmptyBorder(25, 100, 75, 100));
        setDisplayName(org.openide.util.NbBundle.getMessage(StartpageTopComponent.class, "StartpageTopComponent.displayName")); // NOI18N
        setMaximumSize(new java.awt.Dimension(10000, 5000));
        setMinimumSize(new java.awt.Dimension(700, 500));
        setName("Startpage"); // NOI18N
        setPreferredSize(new java.awt.Dimension(10000, 500));
        setLayout(new javax.swing.BoxLayout(this, javax.swing.BoxLayout.PAGE_AXIS));

        headerPane.setBackground(new java.awt.Color(0, 70, 70));
        headerPane.setBorder(javax.swing.BorderFactory.createCompoundBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true), javax.swing.BorderFactory.createEmptyBorder(10, 1, 1, 1)));
        headerPane.setMaximumSize(new java.awt.Dimension(10000, 60));
        headerPane.setMinimumSize(new java.awt.Dimension(600, 60));
        headerPane.setName(""); // NOI18N
        headerPane.setPreferredSize(new java.awt.Dimension(10000, 60));
        headerPane.setRequestFocusEnabled(false);

        titleLB.setFont(new java.awt.Font("DejaVu Serif", 1, 24)); // NOI18N
        titleLB.setForeground(new java.awt.Color(255, 65, 0));
        org.openide.awt.Mnemonics.setLocalizedText(titleLB, org.openide.util.NbBundle.getMessage(StartpageTopComponent.class, "StartpageTopComponent.titleLB.text")); // NOI18N
        titleLB.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        headerPane.add(titleLB);

        add(headerPane);

        contentPanel.setBackground(new java.awt.Color(255, 255, 255));
        contentPanel.setMaximumSize(new java.awt.Dimension(10000, 300));
        contentPanel.setMinimumSize(new java.awt.Dimension(1000, 300));
        contentPanel.setPreferredSize(new java.awt.Dimension(10000, 300));
        contentPanel.setLayout(new java.awt.BorderLayout());

        logoPane.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        logoPane.setIcon(new javax.swing.ImageIcon(getClass().getResource("/nl/fontys/sofa/limo/view/images/logo.png"))); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(logoPane, org.openide.util.NbBundle.getMessage(StartpageTopComponent.class, "StartpageTopComponent.logoPane.text")); // NOI18N
        logoPane.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 25));
        logoPane.setMaximumSize(new java.awt.Dimension(5000, 300));
        logoPane.setMinimumSize(new java.awt.Dimension(300, 300));
        logoPane.setPreferredSize(new java.awt.Dimension(400, 300));
        contentPanel.add(logoPane, java.awt.BorderLayout.CENTER);

        linkPane.setBackground(new java.awt.Color(255, 255, 255));
        linkPane.setMaximumSize(new java.awt.Dimension(5000, 300));
        linkPane.setMinimumSize(new java.awt.Dimension(200, 300));
        linkPane.setPreferredSize(new java.awt.Dimension(100, 300));
        contentPanel.add(linkPane, java.awt.BorderLayout.LINE_START);

        buttonPane.setBackground(new java.awt.Color(255, 255, 255));
        buttonPane.setMinimumSize(new java.awt.Dimension(600, 50));
        buttonPane.setName(""); // NOI18N
        buttonPane.setPreferredSize(new java.awt.Dimension(10000, 50));
        buttonPane.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

//        testButton2BT.setVisible(false);
//        org.openide.awt.Mnemonics.setLocalizedText(testButton2BT, org.openide.util.NbBundle.getMessage(StartpageTopComponent.class, "StartpageTopComponent.testButton2BT.text")); // NOI18N
//        buttonPane.add(testButton2BT);
//
//        jButton1.setVisible(false);
//        org.openide.awt.Mnemonics.setLocalizedText(jButton1, org.openide.util.NbBundle.getMessage(StartpageTopComponent.class, "StartpageTopComponent.jButton1.text")); // NOI18N
//        buttonPane.add(jButton1);
//
//        jButton2.setVisible(false);
//        org.openide.awt.Mnemonics.setLocalizedText(jButton2, org.openide.util.NbBundle.getMessage(StartpageTopComponent.class, "StartpageTopComponent.jButton2.text")); // NOI18N
//        buttonPane.add(jButton2);
        contentPanel.add(buttonPane, java.awt.BorderLayout.PAGE_START);

        add(contentPanel);

        footerPane.setBackground(new java.awt.Color(0, 134, 136));
        footerPane.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 65, 0), 2, true));
        footerPane.setMaximumSize(new java.awt.Dimension(10000, 40));
        footerPane.setMinimumSize(new java.awt.Dimension(600, 40));
        footerPane.setPreferredSize(new java.awt.Dimension(10000, 40));
        add(footerPane);
    }
    private javax.swing.JPanel buttonPane;
    private javax.swing.JPanel contentPanel;
    private javax.swing.JPanel footerPane;
    private javax.swing.JPanel headerPane;
//    private javax.swing.JButton jButton1;
//    private javax.swing.JButton jButton2;
    private javax.swing.JPanel linkPane;
    private javax.swing.JLabel logoPane;
//    private javax.swing.JButton testButton2BT;
    private javax.swing.JLabel titleLB;

    @Override
    public void componentOpened() {
        // TODO add custom code on component opening
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        titleLB.setForeground(new Color(255, 65, 0));
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
