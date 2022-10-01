package backend.thryve.epoch.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@JsonInclude(Include.NON_NULL)
public class HeartRateDTO {

  Long dataSourceId;
  Long value;
  Long dynamicValueType;
  Long startTimestampUnix;
  Long endTimestampUnix;

  public HeartRateDTO(Long averageHeartRate, long heartRateValueType) {
    this.value = averageHeartRate;
    this.dynamicValueType = heartRateValueType;
  }
}
