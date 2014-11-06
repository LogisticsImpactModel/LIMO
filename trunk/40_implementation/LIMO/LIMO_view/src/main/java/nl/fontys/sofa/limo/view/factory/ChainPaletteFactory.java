package nl.fontys.sofa.limo.view.factory;

import javax.swing.Action;
import nl.fontys.sofa.limo.api.exception.ServiceNotFoundException;
import nl.fontys.sofa.limo.view.node.CategoryRootNode;
import org.netbeans.spi.palette.PaletteActions;
import org.netbeans.spi.palette.PaletteController;
import org.netbeans.spi.palette.PaletteFactory;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.util.Lookup;

/**
 * Creates a palette to display items where a chain can be build from.
 *
 * @author Sebastiaan Heijmann
 */
public class ChainPaletteFactory {

	/**
	 * Create a PaletteController containing Category Nodes and default actions
	 * for the Palette. <p>Children of these Categories can be used to build
	 * a chain.
	 *
	 * @return PaletteController - the palette controller
	 * @throws ServiceNotFoundException - thrown when a service cannot be found
	 * to retrieve the datamodels from.
	 */
	public static PaletteController createPalette() throws ServiceNotFoundException {
		Children children
				= Children.create(new CategoryChildFactory(), true);
		AbstractNode paletteRoot = new CategoryRootNode(children);
		paletteRoot.setName("Palette");
		return PaletteFactory.createPalette(paletteRoot, new PaletteActions() {

			@Override
			public Action[] getImportActions() {
				return null;
			}

			@Override
			public Action[] getCustomPaletteActions() {
				return null;
			}

			@Override
			public Action[] getCustomCategoryActions(Lookup lookup) {
				return null;
			}

			@Override
			public Action[] getCustomItemActions(Lookup lookup) {
				return null;
			}

			@Override
			public Action getPreferredAction(Lookup lookup) {
				return null;
			}
		});
	}
}
