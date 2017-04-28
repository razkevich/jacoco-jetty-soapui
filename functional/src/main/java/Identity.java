import java.util.function.Function;

public class Identity<T> implements Monad<T, Identity<?>> {
    private final T value;

    public T getValue() {
        return value;
    }

    Identity(T value) {
        this.value = value;
    }

    public <R> Identity<R> map(Function<T, R> f) {
        return new Identity<>(f.apply(value));
    }

    @Override
    public Identity<?> flatMap(Function<T, Identity<?>> f) {
        return new Identity<>(f.apply(value));
    }
}