import java.util.Optional;

public class Test {
    public static void main(String[] args) {
        new CheckMonad(new Request("hsdfasdfsdfsdfdsf", 444))
                .map(Checks.STRING_LENGTH_UPPER_BOUND_5)
                .map(Checks.STRING_LENGTH_UPPER_BOUND_10)
                .map(Checks.STRING_LENGTH_LOWER_BOUND)
                .map(Checks.STRING_CONTAINS_HELLO)
                .map(Checks.NUMBER_OVERFLOW)
                .map(Checks.NUMBER_DIVISOR_2)
                .map(Checks.NUMBER_DIVISOR_4)
                .getErrors().stream().forEach(System.out::println);

        System.out.println(Optional.of("hello")
                .flatMap(x -> Optional.of(x + "world")
                        .flatMap(y -> Optional.of(y + "!"))));

    }


}
