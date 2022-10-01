package backend.thryve.epoch.controller;

import backend.thryve.epoch.dto.BackendDataDTO;
import backend.thryve.epoch.exceptions.InvalidDateException;
import backend.thryve.epoch.service.BackendDataService;
import backend.thryve.epoch.service.EventService;
import backend.thryve.epoch.util.DateTimeUtil;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/events")
public class EventController {

  @Autowired
  BackendDataService backendDataService;

  @Autowired
  EventService eventService;

  @PostMapping("/populate")
  @ResponseStatus(HttpStatus.CREATED)
  public ResponseEntity<String> receiveDatasource(
      @RequestBody List<BackendDataDTO> backendDataDTO) {

    backendDataService.storeBackendDataList(backendDataDTO);
    return new ResponseEntity<>("Datasource persisted!", HttpStatus.ACCEPTED);
  }

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public List<BackendDataDTO> getEvents(
      @RequestParam(required = false) Long userId,
      @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fromDate,
      @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime toDate) {

    if (DateTimeUtil.isDateIntervalNotValid(fromDate, toDate)) {
      throw new InvalidDateException();
    }

    if (userId != null) {
      return Collections.singletonList(backendDataService.getBackendDataDTO(userId, fromDate, toDate));
    } else {
      return backendDataService.getBackendDataDTO(fromDate, toDate);
    }
  }
}
