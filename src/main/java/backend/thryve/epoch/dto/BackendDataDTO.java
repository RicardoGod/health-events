package backend.thryve.epoch.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class BackendDataDTO {

  String authenticationToken;
  List<DataSourceDTO> dataSources;

}
