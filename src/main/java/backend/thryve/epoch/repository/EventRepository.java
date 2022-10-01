package backend.thryve.epoch.repository;

import backend.thryve.epoch.model.Event;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

  @Query("SELECT e FROM Event e "
      + "JOIN e.dataSource d "
      + "JOIN d.user u "
      + "WHERE u.id = :userId "
      + "  AND e.startTimestampUnix > :fromDate "
      + "  AND e.endTimestampUnix < :toDate")
  List<Event> findEventsFromUserWithinInterval(
      @Param("userId") Long userId,
      @Param("fromDate") Long fromDate,
      @Param("toDate") Long toDate);

  @Query("SELECT e FROM Event e "
      + "JOIN e.dataSource d "
      + "JOIN d.user u "
      + "WHERE u.id = :userId ")
  List<Event> findEventsFromUser(
      @Param("userId") Long userId);

  @Query("SELECT e FROM Event e "
      + "JOIN e.dataSource d "
      + "WHERE d.id = :dataSourceId "
      + "  AND e.startTimestampUnix > :fromDate "
      + "  AND e.endTimestampUnix < :toDate")
  List<Event> findEventsFromDataSourceWithinInterval(
      @Param("dataSourceId") Long dataSourceId,
      @Param("fromDate") Long fromDate,
      @Param("toDate") Long toDate);

  @Query("SELECT e FROM Event e "
      + "JOIN e.dataSource d "
      + "WHERE d.id = :dataSourceId ")
  List<Event> findEventsFromDataSource(
      @Param("dataSourceId") Long dataSourceId);


}
