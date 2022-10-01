package backend.thryve.epoch.model;

import backend.thryve.epoch.dto.EventDTO;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
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
@Table(name = "\"event\"")
public class Event {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long id;

  @Column(name = "`start_timestamp_unix`")
  Long startTimestampUnix;
  @Column(name = "`end_timestamp_unix`")
  Long endTimestampUnix;
  @Column(name = "`created_at_unix`")
  Long createdAtUnix;
  @Column(name = "`dynamic_value_type`")
  Long dynamicValueType;
  @Column(name = "`value`")
  String value;
  @Column(name = "`value_type`")
  String valueType;

  @ManyToOne
  DataSource dataSource;

  public Event(EventDTO eventDTO) {
    this.startTimestampUnix = eventDTO.getStartTimestampUnix();
    this.endTimestampUnix = eventDTO.getEndTimestampUnix();
    this.createdAtUnix = eventDTO.getCreatedAtUnix();
    this.dynamicValueType = eventDTO.getDynamicValueType();
    this.value = eventDTO.getValue();
    this.valueType = eventDTO.getValueType();
  }
}
