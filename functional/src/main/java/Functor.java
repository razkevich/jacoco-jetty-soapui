import java.util.function.Function;

interface Functor<T, F extends Functor<?, ?>> {
    <R> Functor map(Function<T, R> f);
}
