package nl.fontys.sofa.limo.view.status;

import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import nl.fontys.sofa.limo.api.service.status.StatusBarService;
import org.openide.util.Lookup;

public class NewFunctionExceptionHandler extends Handler {

    @Override
    public void publish(LogRecord record) {
        if (record != null && record.getMessage() != null) {
            // This is an uncaught exception being thrown.
            Exception e = new Exception("Error", record.getThrown());
            if (record.getLevel() == Level.SEVERE || record.getLevel() == Level.FINE) {
                if (record.getMessage() != null) {
                    e = new Exception(record.getMessage(), record.getThrown());
                }
                Lookup.getDefault().lookup(StatusBarService.class).setMessage(record.getMessage(), StatusBarService.ACTION_NULL, StatusBarService.STATE_ERROR, e);
            }
        }
    }

    @Override
    public void flush() {

    }

    @Override
    public void close() throws SecurityException {
    }
}
