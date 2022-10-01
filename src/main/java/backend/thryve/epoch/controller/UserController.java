package backend.thryve.epoch.controller;

import backend.thryve.epoch.dto.HeartRateDTO;
import backend.thryve.epoch.exceptions.InvalidDateException;
import backend.thryve.epoch.service.UserService;
import backend.thryve.epoch.util.DateTimeUtil;
import java.time.LocalDateTime;
import javax.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

  @Autowired
  UserService userService;

  @GetMapping("/heart-rate/avg")
  @ResponseStatus(HttpStatus.OK)
  public HeartRateDTO getAverageRate(
      @RequestParam @NotNull Long userId,
      @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fromDate,
      @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime toDate) {

    if (DateTimeUtil.isDateIntervalNotValid(fromDate, toDate)) {
      throw new InvalidDateException();
    }

    return userService.getAverageHeartRate(userId, fromDate, toDate);
  }


}
