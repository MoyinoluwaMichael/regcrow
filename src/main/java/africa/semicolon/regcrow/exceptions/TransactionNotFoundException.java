package africa.semicolon.regcrow.exceptions;

public class TransactionNotFoundException extends RegCrowException{
    public TransactionNotFoundException(String message) {
        super(message);
    }
}
