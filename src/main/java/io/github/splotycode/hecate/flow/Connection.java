package io.github.splotycode.hecate.flow;

/**
 * @author David (_Esel)
 */
public class Connection {
    private final Block target;
    private final Type type;

    public Connection(Block target, Type type) {
        this.target = target;
        this.type = type;
    }

    public Block getTarget() {
        return target;
    }

    public Type getType() {
        return type;
    }

    public enum Type {
        DEFAULT,
        ACTIVE,
        ENTRY
    }
}
