import java.util.function.Function;

public enum Checks {
    STRING_LENGTH_UPPER_BOUND_5(x -> x.getString().length() > 5 ? "STRING_LENGTH_UPPER_BOUND_5" : null),
    STRING_LENGTH_UPPER_BOUND_10(x -> x.getString().length() > 10 ? "STRING_LENGTH_UPPER_BOUND_10" : ""),
    STRING_LENGTH_LOWER_BOUND(x -> x.getString().length() < 3 ? "STRING_LENGTH_LOWER_BOUND" : null),
    STRING_CONTAINS_HELLO(x -> x.getString().contains("hello") ? "STRING_CONTAINS_HELLO" : null),
    NUMBER_OVERFLOW(x -> x.getInteger() > 100 ? "NUMBER_OVERFLOW" : null),
    NUMBER_DIVISOR_2(x -> x.getInteger() % 2 == 0 ? "NUMBER_DIVISOR_2" : null),
    NUMBER_DIVISOR_4(x -> x.getInteger() % 4 == 0 ? "NUMBER_DIVISOR_4" : null);

    private Function<Request, ?> errorMappingFunction;

    public Function<Request, ?> getErrorMappingFunction() {
        return errorMappingFunction;
    }

    Checks(Function<Request, ?> errorMappingFunction) {
        this.errorMappingFunction = errorMappingFunction;

    }
}
