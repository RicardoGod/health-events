package backend.thryve.epoch.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "No events with given criteria!")
public class NoEventsFoundException extends IllegalArgumentException {

}
