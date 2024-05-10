package id.ac.ui.cs.advprog.backendbuysell.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.ObjectError;

import java.util.List;

@Getter
@Setter
public class FieldValidationException extends RuntimeException{
    private List<ObjectError> errors;

    public FieldValidationException(List<ObjectError> errors, String message){
        super(message);
        this.errors = errors;
    }

    public FieldValidationException(){}
}
