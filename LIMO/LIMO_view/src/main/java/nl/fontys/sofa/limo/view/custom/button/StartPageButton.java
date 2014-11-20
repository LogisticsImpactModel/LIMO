package nl.fontys.sofa.limo.view.custom.button;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import javax.swing.BorderFactory;
import javax.swing.JButton;

/**
 * Custom startpage button.
 *
 * @author Sebastiaan Heijmann
 */
public class StartPageButton extends JButton {

    private final Color pressedUpperColor = new Color(0x00868a);
    private final Color pressedLowerColor = new Color(0xFFFFFF);
    private final Color rollOverUpperColor = new Color(0x004646);
    private final Color rollOverLowerColor = new Color(0xFFFFFF);
    private final Color normalColor = new Color(255, 65, 0);

    public StartPageButton() {
        this(null);
    }

    public StartPageButton(String text) {
        super(text);
        super.setContentAreaFilled(false);
        setBackground(new Color(0, 70, 70));

        setFont(new Font("DejaVu Serif", Font.PLAIN, 12));
        setForeground(normalColor);
        setMinimumSize(new Dimension(80, 40));
        setMaximumSize(new Dimension(80, 40));
        setPreferredSize(new Dimension(80, 40));
        setMargin(new Insets(0, 0, 0, 0));

        setBorder(BorderFactory.createEmptyBorder());
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (getModel().isPressed()) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            Color upperColor = pressedUpperColor;
            Color lowerColor = pressedLowerColor;
            GradientPaint gp = new GradientPaint(getWidth() / 2, 0, upperColor, getWidth() / 2, getHeight(), lowerColor);
            g2d.setPaint(gp);
            g2d.fillRect(0, 0, getWidth(), getHeight());

        } else if (getModel().isRollover()) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            Color upperColor = rollOverUpperColor;
            Color lowerColor = rollOverLowerColor;
            GradientPaint gp = new GradientPaint(getWidth() / 2, 0, upperColor, getWidth() / 2, getHeight(), lowerColor);
            g2d.setPaint(gp);
            g2d.fillRect(0, 0, getWidth(), getHeight());
        } else {
            g.setColor(getBackground());
        }
        g.fillRect(0, 0, getWidth(), getHeight());
        super.paintComponent(g);
    }

    @Override
    public void setContentAreaFilled(boolean b) {
    }
}
