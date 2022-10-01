package backend.thryve.epoch.repository;

import backend.thryve.epoch.model.DataSource;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DataSourceRepository extends JpaRepository<DataSource, Long> {

  @Query("SELECT d FROM DataSource d "
      + "JOIN d.user u "
      + "WHERE u.id = :userId ")
  List<DataSource> findDataSourcesFromUser(
      @Param("userId") Long userId);
}
