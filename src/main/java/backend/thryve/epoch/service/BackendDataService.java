package backend.thryve.epoch.service;

import backend.thryve.epoch.dto.BackendDataDTO;
import backend.thryve.epoch.dto.DataSourceDTO;
import backend.thryve.epoch.dto.EventDTO;
import backend.thryve.epoch.exceptions.UserNotFoundException;
import backend.thryve.epoch.model.DataSource;
import backend.thryve.epoch.model.Event;
import backend.thryve.epoch.model.User;
import backend.thryve.epoch.util.DateTimeUtil;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BackendDataService {

  @Autowired
  UserService userService;

  @Autowired
  DataSourceService dataSourceService;

  @Autowired
  EventService eventService;

  @Transactional(rollbackOn = {Exception.class})
  public void storeBackendDataList(List<BackendDataDTO> backendDataList) {
    backendDataList.forEach(this::storeBackendData);
  }

  private void storeBackendData(BackendDataDTO backendDataDTO) {
    User user = userService.storeUser(backendDataDTO.getAuthenticationToken());
    backendDataDTO.getDataSources()
        .forEach(dataSourceDTO -> dataSourceService.storeDataSource(dataSourceDTO, user));
  }

  public List<BackendDataDTO> getBackendDataDTO(LocalDateTime fromDate, LocalDateTime toDate) {
    List<User> users = userService.getAllUsers();

    return users.stream()
        .map(User::getId)
        .map(userId -> getBackendDataDTO(userId, fromDate, toDate))
        .toList();
  }

  public BackendDataDTO getBackendDataDTO(Long userId, LocalDateTime fromDate, LocalDateTime toDate) {
    if (userService.userDoesNotExist(userId)) {
      throw new UserNotFoundException();
    }

    List<DataSource> dataSourcesFromUser = dataSourceService.getDataSourcesFromUser(userId);

    List<DataSourceDTO> dataSourcesDTO = new ArrayList<>();
    for (DataSource dataSource : dataSourcesFromUser) {
      List<Event> eventsFromDataSource = getEventsFromDataSource(dataSource, fromDate, toDate);
      List<EventDTO> eventListDTO = eventService.createEventListDTO(eventsFromDataSource);
      DataSourceDTO dataSourceDTO = dataSourceService.createDataSourceDTO(dataSource.getId(), eventListDTO);
      dataSourcesDTO.add(dataSourceDTO);
    }

    return createBackendDataDTO(userId, dataSourcesDTO);
  }

  private List<Event> getEventsFromDataSource(DataSource dataSource, LocalDateTime fromDate, LocalDateTime toDate) {
    if (fromDate != null && toDate != null) {
      Long fromDateEpoch = DateTimeUtil.getEpochValue(fromDate);
      Long toDateEpoch = DateTimeUtil.getEpochValue(toDate);
      return eventService.findEventsFromDataSourceWithinInterval(dataSource, fromDateEpoch, toDateEpoch);
    } else {
      return eventService.findEventsFromDataSource(dataSource);
    }
  }

  public BackendDataDTO createBackendDataDTO(Long userId, List<DataSourceDTO> dataSourceDTOs) {
    BackendDataDTO backendDataDTO = new BackendDataDTO();
    backendDataDTO.setAuthenticationToken(String.valueOf(userId));
    backendDataDTO.setDataSources(dataSourceDTOs);
    return backendDataDTO;
  }

}
