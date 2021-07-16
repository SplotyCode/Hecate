package io.github.splotycode.hecate.tree;

import io.github.splotycode.hecate.stack.unknown.Unknown;

/**
 * @author David (_Esel)
 */
public class HPrimitive<W> extends HType {
    private final HPrimitiveType type;
    private final Unknown<W> value = new Unknown<>();

    public HPrimitive(HPrimitiveType type) {
        this.type = type;
    }
}
