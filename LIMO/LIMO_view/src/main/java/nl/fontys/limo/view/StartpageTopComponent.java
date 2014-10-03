package nl.fontys.limo.view;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import org.netbeans.api.settings.ConvertAsProperties;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.windows.TopComponent;
import org.openide.util.NbBundle.Messages;

/**
 * Top component which displays the startpage.
 */
@ConvertAsProperties(
				dtd = "-//nl.fontys.limo.view//Startpage//EN",
				autostore = false
)
@TopComponent.Description(
				preferredID = "StartpageTopComponent",
				//iconBase="SET/PATH/TO/ICON/HERE", 
				persistenceType = TopComponent.PERSISTENCE_ALWAYS
)
@TopComponent.Registration(mode = "editor", openAtStartup = true)
@ActionID(category = "Window", id = "nl.fontys.limo.view.StartpageTopComponent")
@ActionReference(path = "Menu/Window" /*, position = 333 */)
@TopComponent.OpenActionRegistration(
				displayName = "#CTL_StartpageAction",
				preferredID = "StartpageTopComponent"
)
@Messages({
	"CTL_StartpageAction=Startpage",
	"CTL_StartpageTopComponent=Startpage Window",
	"HINT_StartpageTopComponent=This is a Startpage window"
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
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {

    jPanel1 = new javax.swing.JPanel();
    jLabel1 = new javax.swing.JLabel();
    jPanel2 = new javax.swing.JPanel();
    jLabel2 = new javax.swing.JLabel();
    jPanel4 = new javax.swing.JPanel();
    jPanel3 = new javax.swing.JPanel();

    setBorder(javax.swing.BorderFactory.createEmptyBorder(25, 100, 75, 100));
    setDisplayName(org.openide.util.NbBundle.getMessage(StartpageTopComponent.class, "StartpageTopComponent.displayName")); // NOI18N
    setMaximumSize(new java.awt.Dimension(10000, 5000));
    setMinimumSize(new java.awt.Dimension(1000, 500));
    setName("Startpage"); // NOI18N
    setPreferredSize(new java.awt.Dimension(10000, 500));
    setLayout(new javax.swing.BoxLayout(this, javax.swing.BoxLayout.PAGE_AXIS));

    jPanel1.setBackground(new java.awt.Color(0, 70, 70));
    jPanel1.setBorder(javax.swing.BorderFactory.createCompoundBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true), javax.swing.BorderFactory.createEmptyBorder(15, 1, 1, 1)));
    jPanel1.setMaximumSize(new java.awt.Dimension(10000, 75));
    jPanel1.setMinimumSize(new java.awt.Dimension(800, 75));
    jPanel1.setName(""); // NOI18N
    jPanel1.setPreferredSize(new java.awt.Dimension(10000, 75));
    jPanel1.setRequestFocusEnabled(false);

    jLabel1.setFont(new java.awt.Font("DejaVu Serif", 1, 24)); // NOI18N
    jLabel1.setForeground(new java.awt.Color(255, 51, 0));
    org.openide.awt.Mnemonics.setLocalizedText(jLabel1, org.openide.util.NbBundle.getMessage(StartpageTopComponent.class, "StartpageTopComponent.jLabel1.text")); // NOI18N
    jLabel1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
    jPanel1.add(jLabel1);

    add(jPanel1);

    jPanel2.setBackground(new java.awt.Color(255, 255, 255));
    jPanel2.setMaximumSize(new java.awt.Dimension(10000, 300));
    jPanel2.setMinimumSize(new java.awt.Dimension(1000, 300));
    jPanel2.setPreferredSize(new java.awt.Dimension(10000, 300));
    jPanel2.setLayout(new java.awt.BorderLayout());

    jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
    jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/nl/fontys/limo/view/image/logo.png"))); // NOI18N
    org.openide.awt.Mnemonics.setLocalizedText(jLabel2, org.openide.util.NbBundle.getMessage(StartpageTopComponent.class, "StartpageTopComponent.jLabel2.text")); // NOI18N
    jLabel2.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 25));
    jLabel2.setMaximumSize(new java.awt.Dimension(5000, 300));
    jLabel2.setMinimumSize(new java.awt.Dimension(400, 300));
    jLabel2.setPreferredSize(new java.awt.Dimension(400, 300));
    jPanel2.add(jLabel2, java.awt.BorderLayout.CENTER);

    jPanel4.setBackground(new java.awt.Color(255, 255, 255));
    jPanel4.setMaximumSize(new java.awt.Dimension(5000, 300));
    jPanel4.setMinimumSize(new java.awt.Dimension(200, 300));
    jPanel4.setPreferredSize(new java.awt.Dimension(200, 300));
    jPanel2.add(jPanel4, java.awt.BorderLayout.LINE_START);

    add(jPanel2);

    jPanel3.setBackground(new java.awt.Color(0, 134, 136));
    jPanel3.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 0, 0), 1, true));
    jPanel3.setMaximumSize(new java.awt.Dimension(10000, 40));
    jPanel3.setMinimumSize(new java.awt.Dimension(800, 40));
    jPanel3.setPreferredSize(new java.awt.Dimension(10000, 40));
    add(jPanel3);
  }// </editor-fold>//GEN-END:initComponents

  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JLabel jLabel1;
  private javax.swing.JLabel jLabel2;
  private javax.swing.JPanel jPanel1;
  private javax.swing.JPanel jPanel2;
  private javax.swing.JPanel jPanel3;
  private javax.swing.JPanel jPanel4;
  // End of variables declaration//GEN-END:variables
	@Override
	public void componentOpened() {
		// TODO add custom code on component opening
	}

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        Color upperColor = new Color(0x00868a);
        Color lowerColor = new Color(0xFFFFFF);
        GradientPaint gp = new GradientPaint(getWidth()/2, 0, upperColor, getWidth()/2, getHeight()/2, lowerColor);
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
