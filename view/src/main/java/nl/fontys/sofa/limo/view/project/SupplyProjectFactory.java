/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.fontys.sofa.limo.view.project;

import java.io.IOException;
import org.netbeans.api.project.Project;
import org.netbeans.spi.project.ProjectFactory;
import org.netbeans.spi.project.ProjectState;
import org.openide.filesystems.FileObject;
import org.openide.util.lookup.ServiceProvider;

/**
 *
 * @author nilsh
 */
@ServiceProvider(service = ProjectFactory.class)
public class SupplyProjectFactory implements ProjectFactory {

    public static final String PROJECT_FILE = "supply.lsp";

    @Override
    public boolean isProject(FileObject projectDirectory) {
        return projectDirectory.getFileObject(PROJECT_FILE) != null;
    }

    @Override
    public Project loadProject(FileObject dir, ProjectState state) throws IOException {
        return isProject(dir) ? new SupplyProject(dir, state) : null;
    }

    @Override
    public void saveProject(Project prjct) throws IOException, ClassCastException {
        return;
    }

}
