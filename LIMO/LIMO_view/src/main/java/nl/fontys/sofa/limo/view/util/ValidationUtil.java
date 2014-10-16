package nl.fontys.sofa.limo.view.util;

import org.netbeans.validation.api.Problem;
import org.netbeans.validation.api.ui.ValidationUI;
import org.netbeans.validation.api.ui.swing.ValidationPanel;
import org.openide.DialogDescriptor;
import org.openide.NotificationLineSupport;

/**
 *
 * @author Sebastiaan Heijmann
 */

public class ValidationUtil {
 
   private ValidationUtil() {
      // default constructor suppressed for non-instantiability
   }
 
   public static DialogDescriptor createDialogDescriptor(ValidationPanel vp, Object innerPane, String title) {
      final DialogDescriptor dd = new DialogDescriptor(innerPane, title);
 
      ValidationUI okButtonEnabler = new ValidationUI() {
         private NotificationLineSupport nls = dd.createNotificationLineSupport();
 
         public void showProblem(Problem problem) {
            if (problem != null) {
               switch (problem.severity()) {
                  case FATAL:
                     nls.setErrorMessage(problem.getMessage());
                     dd.setValid(false);
                     break;
                  case WARNING:
                     nls.setWarningMessage(problem.getMessage());
                     dd.setValid(true);
                     break;
                  default:
                     nls.setInformationMessage(problem.getMessage());
                     dd.setValid(true);
               }
            } else {
               nls.clearMessages();
               dd.setValid(true);
            }
         }
 
         public void clearProblem() {
            showProblem(null);
         }
      };
 
      vp.getValidationGroup().addUI(okButtonEnabler);
      vp.getValidationGroup().performValidation();
      return dd;
   }
}