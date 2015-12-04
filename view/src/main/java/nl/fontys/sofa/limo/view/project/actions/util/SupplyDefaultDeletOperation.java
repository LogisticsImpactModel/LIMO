/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.fontys.sofa.limo.view.project.actions.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.netbeans.spi.project.DeleteOperationImplementation;
import org.openide.filesystems.FileObject;

/**
 *
 * @author nilsh
 */
public class SupplyDefaultDeletOperation implements DeleteOperationImplementation {

    @Override
    public void notifyDeleting() throws IOException {
    }

    @Override
    public void notifyDeleted() throws IOException {
    }

    @Override
    public List<FileObject> getMetadataFiles() {
        return new ArrayList<>();
    }

    @Override
    public List<FileObject> getDataFiles() {
        return new ArrayList<>();
    }

}
