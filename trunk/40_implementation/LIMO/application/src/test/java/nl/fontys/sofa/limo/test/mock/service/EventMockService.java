package nl.fontys.sofa.limo.test.mock.service;

import java.util.List;
import nl.fontys.sofa.limo.api.service.provider.EventService;
import nl.fontys.sofa.limo.domain.component.event.Event;
import org.openide.util.Lookup;
import org.openide.util.lookup.ServiceProvider;

@ServiceProvider(service = EventService.class)
public class EventMockService implements EventService {

    @Override
    public Lookup getLookup() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Event> findAll() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Event findById(String id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Event findByUniqueIdentifier(String uniqueIdentifier) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Event insert(Event entity) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean update(Event entity) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean delete(Event entity) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
