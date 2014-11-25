package nl.fontys.sofa.limo.view.widget;

import java.io.IOException;
import javax.imageio.ImageIO;
import org.netbeans.api.visual.widget.ImageWidget;
import org.netbeans.api.visual.widget.Scene;

/**
 * ImageWidget which visually sets the start of the supply chain in a scene.
 *
 * @author Sebastiaan Heijmann
 */
public class StartWidget extends ImageWidget {

    public StartWidget(Scene scene) throws IOException {
        super(scene);
        setImage(ImageIO.read(getClass().getResourceAsStream("/icons/start-flag.png")));
    }

}
