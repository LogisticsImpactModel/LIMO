package nl.fontys.sofa.limo.view.custom.pane;

import org.netbeans.api.visual.graph.GraphScene;
import org.netbeans.api.visual.widget.Widget;

/**
 * The GraphScene where the chain is build on.
 *
 * @author Sebastiaan Heijmann
 */
public class ChainScene extends GraphScene{

	@Override
	protected Widget attachNodeWidget(Object n) {
		return null;
	}

	@Override
	protected Widget attachEdgeWidget(Object e) {
		return null;
	}

	@Override
	protected void attachEdgeSourceAnchor(Object e, Object n, Object n1) {
	}

	@Override
	protected void attachEdgeTargetAnchor(Object e, Object n, Object n1) {
	}

}
