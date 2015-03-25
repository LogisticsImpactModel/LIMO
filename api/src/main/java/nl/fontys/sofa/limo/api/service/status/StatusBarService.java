package nl.fontys.sofa.limo.api.service.status;

import java.awt.Component;

/**
 *
 * @author Pascal Lindner
 */
public interface StatusBarService {

    public static final int STATE_NONE = 0;
    public static final int STATE_ERROR = 1;
    public static final int STATE_SUCCESS = 2;
    public static final int STATE_FAIL = 3;

    public static final int ACTION_NULL = 0;
    public static final int ACTION_CREATE = 1;
    public static final int ACTION_UPDATE = 2;
    public static final int ACTION_DELETE = 3;
    public static final int ACTION_FOUND = 4;
    public static final int ACTION_ADD = 5;
    public static final int ACTION_CONNECT = 6;

    public void setMessage(String msg, int action, int statusState, Exception e);

    public Component getComponent();
}
