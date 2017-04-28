import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class CheckMonad implements Monad<Request, CheckMonad> {
    private Request value;
    private List<String> errors;
    private String currentError;

    public List<String> getErrors() {
        return errors;
    }

    public String getCurrentError() {
        return currentError;
    }

    public CheckMonad(Request value, List<String> errors) {
        this.value = value;
        this.errors = errors == null ? new ArrayList<>() : errors;
    }

    public CheckMonad(Request value) {
        this.value = value;
        errors = new ArrayList<>();
    }

    @Override
    public <R> CheckMonad map(Function<Request, R> f) {
        R apply = f.apply(value);
        if (apply != null) {
            if (apply instanceof String) {
                errors.add(currentError = (String) apply);
            } else throw new IllegalArgumentException();
        }
        return new CheckMonad(value, errors);
    }

    public CheckMonad map(Checks check) {
        return map(check.getErrorMappingFunction());
    }


    @Override
    public CheckMonad flatMap(Function<Request, CheckMonad> f) {
        return f.apply(value);
    }


}
