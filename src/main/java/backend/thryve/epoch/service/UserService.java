package backend.thryve.epoch.service;


import backend.thryve.epoch.dto.HeartRateDTO;
import backend.thryve.epoch.exceptions.NoEventsFoundException;
import backend.thryve.epoch.exceptions.UserNotFoundException;
import backend.thryve.epoch.model.DataSource;
import backend.thryve.epoch.model.Event;
import backend.thryve.epoch.model.User;
import backend.thryve.epoch.repository.UserRepository;
import backend.thryve.epoch.util.DateTimeUtil;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  public static final long HEART_RATE_VALUE_TYPE = 3000L;
  @Autowired
  UserRepository userRepository;

  @Autowired
  DataSourceService dataSourceService;

  @Autowired
  EventService eventService;

  public HeartRateDTO getAverageHeartRate(Long userId, LocalDateTime fromDate, LocalDateTime toDate) {
    if (!userRepository.existsById(userId)) {
      throw new UserNotFoundException();
    }

    List<Event> events = dataSourceService.getDataSourcesFromUser(userId)
        .stream()
        .map(dataSource -> getEventsFromDataSource(dataSource, fromDate, toDate))
        .flatMap(List::stream)
        .toList();

    if (events.isEmpty()) {
      throw new NoEventsFoundException();
    }

    return getAverageHeartRateFromEvents(events);
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

  private HeartRateDTO getAverageHeartRateFromEvents(List<Event> events) {
    List<Event> heartRateEvents = getHeartRateEvents(events);
    Long averageHeartRate = getHeartRateAverage(heartRateEvents);

    return new HeartRateDTO(averageHeartRate, HEART_RATE_VALUE_TYPE);
  }

  private List<Event> getHeartRateEvents(List<Event> events) {
    return events.stream()
        .filter(event -> event.getDynamicValueType() == HEART_RATE_VALUE_TYPE)
        .toList();
  }

  private Long getHeartRateAverage(List<Event> heartRateEvents) {
    Long totalHeartRate = heartRateEvents.stream()
        .map(Event::getValue)
        .map(Long::valueOf)
        .reduce(0L, Long::sum);

    return totalHeartRate / heartRateEvents.size();
  }

  public boolean userDoesNotExist(Long userId) {
    return !userRepository.existsById(userId);
  }

  public List<User> getAllUsers() {
    return userRepository.findAll();
  }

  public User storeUser(String userId) {
    User entityUser = new User(Long.valueOf(userId));
    return userRepository.save(entityUser);
  }
}
