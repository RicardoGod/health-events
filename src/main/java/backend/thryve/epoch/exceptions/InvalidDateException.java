package backend.thryve.epoch.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "The date is invalid!")
public class InvalidDateException extends IllegalArgumentException {

}
