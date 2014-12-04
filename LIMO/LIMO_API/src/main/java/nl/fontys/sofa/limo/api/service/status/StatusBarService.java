/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.fontys.sofa.limo.api.service.status;

import java.awt.Component;

public interface StatusBarService {

    public static final int STATE_NONE = 0;
    public static final int STATE_SUCCESS = 1;
    public static final int STATE_FAIL = 2;

    public static final int ACTION_CREATE = 0;
    public static final int ACTION_UPDATE = 0;
    public static final int ACTION_DELETE = 0;
    public static final int ACTION_FOUND = 0;

    public void setMessage(String msg, int action, int statusState, Exception e);

    public Component getComponent();
}
