package backend.thryve.epoch.model;

import backend.thryve.epoch.dto.DataSourceDTO;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "\"data_source\"")
public class DataSource {

  @Id
  Long id;

  @ManyToOne
  User user;

  @OneToMany(mappedBy = "id", cascade = CascadeType.ALL)
  List<Event> data;

  public DataSource(DataSourceDTO dataSourceDTO) {
    this.id = dataSourceDTO.getDataSource();
  }

}
