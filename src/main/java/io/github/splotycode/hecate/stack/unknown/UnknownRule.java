package io.github.splotycode.hecate.stack.unknown;

import java.util.Objects;
import java.util.function.Function;

/**
 * @author David (_Esel)
 */
public interface UnknownRule<T> {
    static <T> UnknownRule<T> definitely(T value) {
        return new UnknownRule<T>() {
            @Override
            public MatchResult matches(T against) {
                return Objects.equals(value, against) ? MatchResult.YES : MatchResult.NO;
            }

            @Override
            public UnknownRule<T> transform(Function<T, T> function) {
                return definitely(function.apply(value));
            }
        };
    }

    MatchResult matches(T value);
    UnknownRule<T> transform(Function<T, T> function);
}
