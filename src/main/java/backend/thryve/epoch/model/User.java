package backend.thryve.epoch.model;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
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
@Table(name = "\"user\"")
public class User {

  @Id
  Long id;

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
  List<DataSource> dataSources;

  public User(Long id) {
    this.id = id;
  }

}
