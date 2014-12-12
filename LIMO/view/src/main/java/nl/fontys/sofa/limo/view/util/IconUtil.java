package nl.fontys.sofa.limo.view.util;

import java.awt.Image;
import java.io.IOException;
import java.util.HashMap;
import javax.imageio.ImageIO;
import nl.fontys.sofa.limo.domain.component.event.Event;
import nl.fontys.sofa.limo.domain.component.hub.Hub;
import nl.fontys.sofa.limo.domain.component.leg.Leg;
import nl.fontys.sofa.limo.domain.component.procedure.ProcedureCategory;
import nl.fontys.sofa.limo.domain.component.type.HubType;
import nl.fontys.sofa.limo.domain.component.type.LegType;
import org.openide.util.Exceptions;

/**
 *
 * @author Dominik Kaisers <d.kaisers@student.fontys.nl>
 */
public final class IconUtil {

    private static boolean isSetUp = false;
    private static HashMap<Class, IconHolder> typeIcons;
    private static HashMap<UI_ICON, Image> uiIcons;

    private IconUtil() {
    }

    /**
     * Get icon by class. (for nodes.)
     *
     * @param clazz Class.
     * @param type Icon type (size and color).
     * @return Icon.
     */
    public static Image getIcon(Class clazz, int type) {
        if (!isSetUp) {
            setUp();
        }

        if (!typeIcons.containsKey(clazz)) {
            return null;
        }

        IconHolder ih = typeIcons.get(clazz);
        Image i = ih.getIcon(type);
        return i;
    }

    /**
     * Get an icon by the UI_ICON enum.
     *
     * @param uiIcon Desired ui icon.
     * @return Icon.
     */
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

    /**
     * Set up method for all known icons.
     */
    private static void setUp() {
        typeIcons = new HashMap<>();

        typeIcons.put(ProcedureCategory.class, new IconHolder("ProcedureCategory"));
        typeIcons.put(Leg.class, new IconHolder("LegType"));
        typeIcons.put(LegType.class, new IconHolder("LegType"));
        typeIcons.put(Hub.class, new IconHolder("HubType"));
        typeIcons.put(HubType.class, new IconHolder("HubType"));
        typeIcons.put(Event.class, new IconHolder("Event"));

        uiIcons = new HashMap<>();

        isSetUp = true;
    }

    /**
     * Private class for a class' different typeIcons. Hold an Image for each
     * possible icon. Uses Lazy Loading.
     */
    private static class IconHolder {

        private static final String ICON_COLOR_16X16 = "_16x16.png";
        private static final String ICON_COLOR_32X32 = "_32x32.png";
        private static final String ICON_MONO_16X16 = "_SW_16x16.png";
        private static final String ICON_MONO_32X32 = "_SW_32x32.png";

        private final String filename;
        private final HashMap<Integer, Image> iconsPerType;

        /**
         * Construct new IconHolder.
         *
         * @param filename Filename of icon without extension.
         */
        public IconHolder(String filename) {
            this.filename = filename;
            this.iconsPerType = new HashMap<>();
        }

        /**
         * Get Icon for specified type of BeanInfo. Used for nodes.
         *
         * @param type BeanInfo.Type.
         * @return Image icon.
         */
        public Image getIcon(int type) {
            iconsPerType.put(type, loadImage(getIconType(type)));
            return iconsPerType.get(type);
        }

        /**
         * Get the icon name extension by type.
         *
         * @param type Type.
         * @return Icon file name extension.
         */
        private String getIconType(int type) {
            switch (type) {
                case 1:
                    return ICON_COLOR_16X16;
                case 2:
                    return ICON_COLOR_32X32;
                case 3:
                    return ICON_MONO_16X16;
                case 4:
                    return ICON_MONO_32X32;
                default:
                    return "";
            }
        }

        /**
         * Load image.
         *
         * @param extension File extension.
         * @return Image.
         */
        private Image loadImage(String extension) {
            try {
                return ImageIO.read(getClass().getClassLoader().getResource("icons/" + filename + extension));
            } catch (IOException ex) {
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
