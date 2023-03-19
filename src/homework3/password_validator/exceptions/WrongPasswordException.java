package homework3.password_validator.exceptions;

public class WrongPasswordException extends Exception{
    public WrongPasswordException(String message) {
        super(message);
    }
    public WrongPasswordException() {
        super();
    }
}
