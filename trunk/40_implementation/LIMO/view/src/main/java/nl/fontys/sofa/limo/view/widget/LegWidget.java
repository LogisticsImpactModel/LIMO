package nl.fontys.sofa.limo.view.widget;

import java.awt.BasicStroke;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.util.Map;
import javax.swing.AbstractAction;
import javax.swing.JPopupMenu;
import nl.fontys.sofa.limo.domain.component.leg.Leg;
import nl.fontys.sofa.limo.domain.component.leg.MultiModeLeg;
import nl.fontys.sofa.limo.domain.component.leg.ScheduledLeg;
import nl.fontys.sofa.limo.view.chain.ChainGraphScene;
import nl.fontys.sofa.limo.view.node.bean.AbstractBeanNode;
import static nl.fontys.sofa.limo.view.util.IconUtil.getScaledImageFromIcon;
import nl.fontys.sofa.limo.view.util.LIMOResourceBundle;
import org.netbeans.api.visual.action.ActionFactory;
import org.netbeans.api.visual.action.PopupMenuProvider;
import org.netbeans.api.visual.anchor.AnchorShape;
import org.netbeans.api.visual.anchor.PointShape;
import org.netbeans.api.visual.layout.LayoutFactory;
import org.netbeans.api.visual.widget.ConnectionWidget;
import org.netbeans.api.visual.widget.ImageWidget;
import org.netbeans.api.visual.widget.Scene;
import org.netbeans.api.visual.widget.Widget;

/**
 * Widget responsible for displaying a Leg in a GraphScene. LegWidgets can be
 * added to a connection widget.
 *
 * @author Sebastiaan Heijmann
 */
public class LegWidget extends ConnectionWidget implements BasicWidget {

  private Map<Leg, Double> legs;
  private final AbstractBeanNode legNode;

  public LegWidget(Scene scene, AbstractBeanNode legNode) {
    super(scene);
    this.legNode = legNode;
    setChildLegWidgets();
    setTargetAnchorShape(AnchorShape.TRIANGLE_FILLED);
    setStroke(new BasicStroke(3.0f));
    setEndPointShape(PointShape.SQUARE_FILLED_BIG);
  }

  @Override
  public void addActions(ChainGraphScene scene) {
    getActions().addAction(scene.createObjectHoverAction());
    getActions().addAction(scene.createSelectAction());
//        getActions().addAction(scene.reconnectAction);
    getActions().addAction(ActionFactory.createPopupMenuAction(new LegWidget.WidgetPopupMenu()));
  }

  private void setChildLegWidgets() {
    Leg leg = getLeg();
    if (leg instanceof MultiModeLeg) {
      setMultiModeLegWidgets(leg);
    } else if (leg instanceof ScheduledLeg) {
      setScheduledLegWidgets(leg);
    } else {
      setNormalLegWidgets(leg);
    }
  }

  private void setNormalLegWidgets(Leg leg) {
    ImageWidget iw = new ImageWidget(getScene());
    iw.setImage(getScaledImageFromIcon(leg.getIcon()));
    iw.getActions().addAction(ActionFactory.createPopupMenuAction(new LegWidget.WidgetPopupMenu()));
    this.setConstraint(iw, LayoutFactory.ConnectionWidgetLayoutAlignment.TOP_RIGHT, 10);
    this.addChild(iw);
  }

  private void setScheduledLegWidgets(Leg leg) {
    ScheduledLeg sl = (ScheduledLeg) leg;
    setNormalLegWidgets(sl);
    setNormalLegWidgets(sl.getAlternative());
  }

  private void setMultiModeLegWidgets(Leg leg) {
    MultiModeLeg mml = (MultiModeLeg) leg;
    legs = mml.getLegs();
    for (Map.Entry<Leg, Double> entry : legs.entrySet()) {
      setNormalLegWidgets(entry.getKey());
    }
  }

  @Override
  public boolean drop(ChainGraphScene scene, Widget widget, Point point) {
    throw new UnsupportedOperationException(LIMOResourceBundle.getString("NOT_DROPPABLE"));
  }

  public Leg getLeg() {
    return legNode.getLookup().lookup(Leg.class);
  }

  @Override
  public void propertyChange(PropertyChangeEvent pce) {
    throw new UnsupportedOperationException(LIMOResourceBundle.getString("NOT_SUPPORTED")); //To change body of generated methods, choose Tools | Templates.
  }

  /**
   * The popup menu when right clicked on this widget.
   */
  private class WidgetPopupMenu implements PopupMenuProvider {

    @Override
    public JPopupMenu getPopupMenu(Widget widget, Point localLocation) {
      JPopupMenu popup = new JPopupMenu();
      popup.add(new AbstractAction(LIMOResourceBundle.getString("DELETE")) {

        @Override
        public void actionPerformed(ActionEvent ae) {
          ChainGraphScene scene = (ChainGraphScene) getScene();
          scene.removeEdge(legNode);
        }
      });
      return popup;
    }
  }
}
