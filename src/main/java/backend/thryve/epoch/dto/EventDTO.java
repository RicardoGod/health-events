package backend.thryve.epoch.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class EventDTO {

  Long startTimestampUnix;
  Long endTimestampUnix;
  Long createdAtUnix;
  Long dynamicValueType;
  String value;
  String valueType;


}
