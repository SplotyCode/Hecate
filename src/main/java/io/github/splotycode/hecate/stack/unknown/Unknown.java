package io.github.splotycode.hecate.stack.unknown;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * @author David (_Esel)
 */
public class Unknown<T> {
    private final List<UnknownRule<T>> rules = new ArrayList<>();

    public Unknown<T> transform(Function<T, T> function) {
        Unknown<T> transformed = new Unknown<>();
        for (UnknownRule<T> rule : rules) {
            transformed.addRule(rule.transform(function));
        }
        return transformed;
    }

    @Override
    public String toString() {
        return "Unknown{" +
                "rules=" + rules +
                '}';
    }

    public void addRule(UnknownRule<T> rule) {
        rules.add(rule);
    }

    public void definitely(T value) {
        addRule(UnknownRule.definitely(value));
    }
}
