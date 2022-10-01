package backend.thryve.epoch.util;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import lombok.experimental.UtilityClass;

@UtilityClass
public class DateTimeUtil {

  public boolean isDateIntervalNotValid(LocalDateTime fromDate, LocalDateTime toDate) {
    return isFromDateNotValid(fromDate) || isFromDateAfterToDate(fromDate, toDate);
  }

  private boolean isFromDateNotValid(LocalDateTime fromDate) {
    return fromDate != null && fromDate.isAfter(LocalDateTime.now());
  }

  private boolean isFromDateAfterToDate(LocalDateTime fromDate, LocalDateTime toDate) {
    return fromDate != null && toDate != null && fromDate.isAfter(toDate);
  }

  public Long getEpochValue(LocalDateTime localDateTime) {
    return localDateTime.atZone(ZoneOffset.systemDefault()).toInstant().toEpochMilli();
  }
}

