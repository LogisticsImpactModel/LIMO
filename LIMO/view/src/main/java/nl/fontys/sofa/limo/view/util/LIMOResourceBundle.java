package nl.fontys.sofa.limo.view.util;

import java.text.MessageFormat;
import java.util.ResourceBundle;

/**
 *
 * @author Matthias Brück
 */
public final class LIMOResourceBundle {

    private LIMOResourceBundle() {
    }

    private static final ResourceBundle BUNDLE = ResourceBundle.getBundle("nl/fontys/sofa/limo/view/Bundle");

    public static String getString(String name) {
        return BUNDLE.getString(name);
    }

    public static String getString(String name, Object... params) {
        String string = getString(name);
        return MessageFormat.format(string, params);
    }
}
