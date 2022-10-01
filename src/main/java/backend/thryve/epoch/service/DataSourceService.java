package backend.thryve.epoch.service;

import backend.thryve.epoch.dto.DataSourceDTO;
import backend.thryve.epoch.dto.EventDTO;
import backend.thryve.epoch.model.DataSource;
import backend.thryve.epoch.model.Event;
import backend.thryve.epoch.model.User;
import backend.thryve.epoch.repository.DataSourceRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DataSourceService {

  @Autowired
  DataSourceRepository dataSourceRepository;

  @Autowired
  EventService eventService;

  public DataSource storeDataSource(DataSourceDTO dataSourceDTO, User user) {
    final DataSource dataSource = dataSourceRepository.save(new DataSource(dataSourceDTO));

    List<Event> events = dataSourceDTO.getData().stream()
        .map(eventDTO -> eventService.storeEvent(eventDTO, dataSource))
        .toList();

    dataSource.setUser(user);
    dataSource.setData(events);
    return dataSource;
  }

  public List<DataSource> getDataSourcesFromUser(Long userId) {
    return dataSourceRepository.findDataSourcesFromUser(userId);
  }

  public DataSourceDTO createDataSourceDTO(Long dataSourceId, List<EventDTO> eventsDTO) {
    DataSourceDTO dataSourceDTO = new DataSourceDTO();
    dataSourceDTO.setDataSource(dataSourceId);
    dataSourceDTO.setData(eventsDTO);
    return dataSourceDTO;
  }

}
