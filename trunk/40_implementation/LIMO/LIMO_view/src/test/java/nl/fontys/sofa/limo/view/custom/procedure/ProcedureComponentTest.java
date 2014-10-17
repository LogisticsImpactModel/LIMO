package nl.fontys.sofa.limo.view.custom.procedure;

import java.util.ArrayList;
import javax.swing.JFrame;
import nl.fontys.sofa.limo.domain.component.procedure.Procedure;
import nl.fontys.sofa.limo.domain.component.procedure.ProcedureResponsibilityDirection;
import nl.fontys.sofa.limo.domain.component.procedure.TimeType;
import nl.fontys.sofa.limo.domain.component.procedure.value.RangeValue;
import nl.fontys.sofa.limo.domain.component.procedure.value.SingleValue;
import nl.fontys.sofa.limo.view.custom.ProcedureComponent;

/**
 * @author Matthias Brück
 */
public class ProcedureComponentTest {
    
    public static void main(String[] args) {
        new ProcedureComponentTest();
    }
    
    public ProcedureComponentTest(){
        JFrame frame = new JFrame("TEST");
        frame.setSize(650, 250);
        ArrayList<Procedure> procedures =  new ArrayList<>();
        procedures.add(new Procedure("TEST", "TESTA", new SingleValue(2), new RangeValue(3, 5), TimeType.MINUTES, ProcedureResponsibilityDirection.INPUT));
        procedures.add(new Procedure("ABCDEF", "GHIJKL", new SingleValue(10), new RangeValue(2, 19), TimeType.MINUTES, ProcedureResponsibilityDirection.INPUT));
        frame.add(new ProcedureComponent(procedures));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
