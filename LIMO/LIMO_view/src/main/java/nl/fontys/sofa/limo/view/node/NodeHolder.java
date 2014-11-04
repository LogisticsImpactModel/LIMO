package nl.fontys.sofa.limo.view.node;

import java.awt.Image;

/**
 * Holds an image for the node to display in a chain.
 *
 * @author Sebastiaan Heijmann
 */
public class NodeHolder {
   private Image image;
    
    public NodeHolder(Image image) {
        this.image = image;
    }
    
    public Image getImage() {
        return image;
    }

}
