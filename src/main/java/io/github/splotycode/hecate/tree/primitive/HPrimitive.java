package io.github.splotycode.hecate.tree.primitive;

import io.github.splotycode.hecate.stack.unknown.Unknown;
import io.github.splotycode.hecate.tree.HType;

/**
 * @author David (_Esel)
 */
public class HPrimitive<W> implements HType {
    private final HPrimitiveType type;
    private final Unknown<W> value = new Unknown<>();

    public HPrimitive(HPrimitiveType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "HPrimitive{" +
                "type=" + type +
                ", value=" + value +
                '}';
    }

    public Unknown<W> getValue() {
        return value;
    }
}
