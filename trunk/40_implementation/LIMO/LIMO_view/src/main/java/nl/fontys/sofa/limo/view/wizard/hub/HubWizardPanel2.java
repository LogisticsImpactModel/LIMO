///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package nl.fontys.sofa.limo.view.wizard.hub;
//
//import javax.swing.event.ChangeListener;
//import nl.fontys.sofa.limo.domain.component.Hub;
//import nl.fontys.sofa.limo.domain.types.HubType;
//import org.openide.WizardDescriptor;
//import org.openide.util.HelpCtx;
//
//public class HubWizardPanel2 implements WizardDescriptor.Panel<WizardDescriptor> {
//
//    /**
//     * The visual component that displays this panel. If you need to access the
//     * component from this class, just use getComponent().
//     */
//    private HubVisualPanel2 component;
//
//    // Get the visual component for the panel. In this template, the component
//    // is kept separate. This can be more efficient: if the wizard is created
//    // but never displayed, or not all panels are displayed, it is better to
//    // create only those which really need to be visible.
//    @Override
//    public HubVisualPanel2 getComponent() {
//        if (component == null) {
//            component = new HubVisualPanel2();
//        }
//        return component;
//    }
//
//    @Override
//    public HelpCtx getHelp() {
//        // Show no Help button for this panel:
//        return HelpCtx.DEFAULT_HELP;
//        // If you have context help:
//        // return new HelpCtx("help.key.here");
//    }
//
//    @Override
//    public boolean isValid() {
//        // If it is always OK to press Next or Finish, then:
//        return true;
//        // If it depends on some condition (form filled out...) and
//        // this condition changes (last form field filled in...) then
//        // use ChangeSupport to implement add/removeChangeListener below.
//        // WizardDescriptor.ERROR/WARNING/INFORMATION_MESSAGE will also be useful.
//    }
//
//    @Override
//    public void addChangeListener(ChangeListener l) {
//    }
//
//    @Override
//    public void removeChangeListener(ChangeListener l) {
//    }
//
//    @Override
//    public void readSettings(WizardDescriptor wiz) {
//        Hub hub = (Hub) wiz.getProperty("hubCopy");
//        if (hub != null) {
//            getComponent().updateLabel(hub.getIdentifier(), hub.getIcon());
//        }
//    }
//
//    @Override
//    public void storeSettings(WizardDescriptor wiz) {
//        wiz.putProperty("hub", getComponent().getHub());
//    }
//
//}
