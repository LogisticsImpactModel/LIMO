/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.fontys.sofa.limo.api.service.status;

import java.awt.Component;

public interface StatusBarService{
     public void setMessage(String msg,int action ,int statusState);
     public Component getComponent();
}