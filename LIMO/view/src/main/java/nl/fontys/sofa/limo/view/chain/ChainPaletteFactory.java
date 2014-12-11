package nl.fontys.sofa.limo.view.chain;

import javax.swing.Action;
import nl.fontys.sofa.limo.api.exception.ServiceNotFoundException;
import nl.fontys.sofa.limo.view.node.factory.CategoryChildFactory;
import nl.fontys.sofa.limo.view.node.root.CategoryRootNode;
import org.netbeans.spi.palette.PaletteActions;
import org.netbeans.spi.palette.PaletteController;
import org.netbeans.spi.palette.PaletteFactory;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.util.Lookup;

/**
 * This class is responsible for creating the
 * {@link org.netbeans.spi.palette.PaletteController}.
 * <p>
 * This controller is injected into the lookup of the
 * {@link nl.fontys.sofa.limo.view.topcomponent.ChainBuilderTopComponent} which
 * then displays the complete palette.
 *
 * @author Sebastiaan Heijmann
 */
public final class ChainPaletteFactory {

    private ChainPaletteFactory() {
    }

    /**
     * Create a PaletteController containing a
     * {@link nl.fontys.sofa.limo.view.node.root.CategoryRootNode} and it's
     * children (the displayable categories created by
     * {@link  nl.fontys.sofa.limo.view.node.factory.CategoryChildFactory}).
     *
     * @return PaletteController - the palette controller
     * @throws ServiceNotFoundException - thrown when a service cannot be found
     * to retrieve the data models from.
     */
    public static PaletteController createPalette() throws ServiceNotFoundException {
        Children children = Children.create(new CategoryChildFactory(), true);
        AbstractNode paletteRoot = new CategoryRootNode(children);
        paletteRoot.setName("Palette");
        PaletteController controller = PaletteFactory.createPalette(
                paletteRoot,
                new PaletteActions() {

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
        return controller;
    }
}
