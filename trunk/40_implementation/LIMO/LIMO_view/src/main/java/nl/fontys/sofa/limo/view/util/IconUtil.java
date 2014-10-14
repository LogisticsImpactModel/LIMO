package nl.fontys.sofa.limo.view.util;

import java.awt.Event;
import java.awt.Image;
import java.io.IOException;
import java.util.HashMap;
import javax.imageio.ImageIO;
import nl.fontys.sofa.limo.domain.component.process.ProcedureCategory;
import nl.fontys.sofa.limo.domain.component.type.HubType;
import nl.fontys.sofa.limo.domain.component.type.LegType;
import org.openide.util.Exceptions;

/**
 *
 * @author Dominik Kaisers <d.kaisers@student.fontys.nl>
 */
public class IconUtil {
    
    private static boolean isSetUp = false;
    private static HashMap<Class, IconHolder> typeIcons;
    private static HashMap<UI_ICON, Image> uiIcons;
    
    private IconUtil() {}
    
    public static Image getIcon(Class clazz, int type) {
        if (!isSetUp) {
            setUp();
        }
        
        IconHolder ih = typeIcons.get(clazz);
        Image i = ih.getIcon(type);
        return i;
    }
    
    public static Image getIcon(UI_ICON uiIcon) {
        if (!isSetUp) {
            setUp();
        }
        
        if (!uiIcons.containsKey(uiIcon)) {
            try {
                uiIcons.put(uiIcon, ImageIO.read(uiIcon.getClass().getClassLoader().getResource("ui/" + uiIcon.filename + ".png")));
            } catch (IOException ex) {
                Exceptions.printStackTrace(ex);
            }
        }
        
        return uiIcons.get(uiIcon);
    }
    
    private static void setUp() {
        typeIcons = new HashMap<>();
        
        typeIcons.put(ProcedureCategory.class, new IconHolder("ProcedureCategory"));
        typeIcons.put(LegType.class, new IconHolder("Leg"));
        typeIcons.put(LegType.class, new IconHolder("LegType"));
        typeIcons.put(HubType.class, new IconHolder("Hub"));
        typeIcons.put(HubType.class, new IconHolder("HubType"));
        typeIcons.put(Event.class, new IconHolder("Event"));
        
        uiIcons = new HashMap<>();
        
        isSetUp = true;
    }
    
    /**
     * Private class for a class' different typeIcons. Hold an Image for each possible icon.
     * Uses Lazy Loading.
     */
    private static class IconHolder {
        private static final String ICON_COLOR_16x16 = "_16x16.png";
        private static final String ICON_COLOR_32x32 = "_32x32.png";
        private static final String ICON_MONO_16x16 = "_SW_16x16.png";
        private static final String ICON_MONO_32x32 = "_SW_32x32.png";
        
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
    
    public static enum UI_ICON {
        
        ADD("add"),
        CANCEL("cancel"),
        EDIT("edit"),
        REFRESH("refresh"),
        SEARCH("search"),
        TRASH("trash"),
        VALID("valid"),
        WARN("warning"),
        WARN2("warning2");
        
        private final String filename;

        private UI_ICON(String filename) {
            this.filename = filename;
        }

        public String getFilename() {
            return filename;
        }
    }
    
}
