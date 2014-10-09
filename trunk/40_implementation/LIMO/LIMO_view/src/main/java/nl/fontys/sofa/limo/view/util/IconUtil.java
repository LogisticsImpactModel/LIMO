package nl.fontys.sofa.limo.view.util;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import javax.imageio.ImageIO;
import nl.fontys.sofa.limo.domain.category.CostCategory;
import nl.fontys.sofa.limo.domain.category.TimeCategory;
import nl.fontys.sofa.limo.domain.component.Event;
import nl.fontys.sofa.limo.domain.types.HubType;
import nl.fontys.sofa.limo.domain.types.LegType;

/**
 *
 * @author Dominik Kaisers <d.kaisers@student.fontys.nl>
 */
public class IconUtil {
    
    private static boolean isSetUp = false;
    private static HashMap<Class, IconHolder> icons;
    
    private IconUtil() {}
    
    public static Image getIcon(Class clazz, int type) {
        if (!isSetUp) {
            setUp();
        }
        
        IconHolder ih = icons.get(clazz);
        Image i = ih.getIcon(type);
        return i;
    }
    
    private static void setUp() {
        icons = new HashMap<>();
        
        icons.put(TimeCategory.class, new IconHolder("TimeCategory"));
        icons.put(CostCategory.class, new IconHolder("CostCategory"));
        icons.put(LegType.class, new IconHolder("LegType"));
        icons.put(HubType.class, new IconHolder("HubType"));
        icons.put(Event.class, new IconHolder("Event"));
        
        isSetUp = true;
    }
    
    /**
     * Private class for a class' different icons. Hold an Image for each possible icon.
     * Uses Lazy Loading.
     */
    private static class IconHolder {
        private static final String ICON_COLOR_16x16 = "_16x16.png";
        private static final String ICON_COLOR_32x32 = "_16x16.png";
        private static final String ICON_MONO_16x16 = "_SW_16x16.png";
        private static final String ICON_MONO_32x32 = "_SW_16x16.png";
        
        private final String filename;
        private final HashMap<Integer, Image> iconsPerType;

        /**
         * Construct new IconHolder.
         * @param filename Filename of icon without extension.
         */
        public IconHolder(String filename) {
            this.filename = filename;
            this.iconsPerType = new HashMap<>();
        }
        
        /**
         * Get Icon for specified type of BeanInfo. Used for nodes.
         * @param type BeanInfo.Type.
         * @return Image icon.
         */
        public Image getIcon(int type) {
            switch (type) {
                case 1:
                    if (!iconsPerType.containsKey(type))
                        iconsPerType.put(type, loadImage(ICON_COLOR_16x16));
                    return iconsPerType.get(type);
                    
                case 2:
                    if (!iconsPerType.containsKey(type))
                        iconsPerType.put(type, loadImage(ICON_COLOR_32x32));
                    return iconsPerType.get(type);
                    
                case 3:
                    if (!iconsPerType.containsKey(type))
                        iconsPerType.put(type, loadImage(ICON_MONO_16x16));
                    return iconsPerType.get(type);
                    
                case 4:
                    if (!iconsPerType.containsKey(type))
                        iconsPerType.put(type, loadImage(ICON_MONO_32x32));
                    return iconsPerType.get(type);
            }
            
            return null;
        }
           
        private Image loadImage(String extension) {
            try {
                return ImageIO.read(getClass().getClassLoader().getResource("icons/"+filename+extension));
            } catch (IOException ex) {
                ex.printStackTrace();
                return null;
            }
        }
    }
    
}
