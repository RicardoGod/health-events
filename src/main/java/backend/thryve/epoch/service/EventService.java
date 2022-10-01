package backend.thryve.epoch.service;

import backend.thryve.epoch.dto.EventDTO;
import backend.thryve.epoch.model.DataSource;
import backend.thryve.epoch.model.Event;
import backend.thryve.epoch.repository.EventRepository;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EventService {

  @Autowired
  EventRepository eventRepository;

  Event storeEvent(EventDTO eventDTO, DataSource dataSource) {
    Event event = new Event(eventDTO);
    event.setDataSource(dataSource);
    return eventRepository.save(event);
  }

  List<Event> findEventsFromDataSource(DataSource dataSource) {
    return eventRepository.findEventsFromDataSource(dataSource.getId());
  }

  List<Event> findEventsFromDataSourceWithinInterval(DataSource dataSource, Long fromDateEpoch, Long toDateEpoch) {
    return eventRepository.findEventsFromDataSourceWithinInterval(dataSource.getId(), fromDateEpoch, toDateEpoch);
  }

  List<EventDTO> createEventListDTO(List<Event> events) {
    return events.stream()
        .map(EventService::createEventDTO)
        .toList();
  }

  static EventDTO createEventDTO(Event event) {
    EventDTO eventDTO = new EventDTO();
    eventDTO.setStartTimestampUnix(event.getStartTimestampUnix());
    eventDTO.setEndTimestampUnix(event.getEndTimestampUnix());
    eventDTO.setCreatedAtUnix(event.getCreatedAtUnix());
    eventDTO.setDynamicValueType(event.getDynamicValueType());
    eventDTO.setValue(event.getValue());
    eventDTO.setValueType(event.getValueType());
    return eventDTO;
  }
}
