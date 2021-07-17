package io.github.splotycode.hecate.instruction.stack;

import io.github.splotycode.hecate.instruction.Instruction;
import io.github.splotycode.hecate.stack.StackContext;
import io.github.splotycode.hecate.tree.HType;
import io.github.splotycode.hecate.tree.primitive.HPrimitive;
import io.github.splotycode.hecate.tree.primitive.HPrimitiveType;

/**
 * @author David (_Esel)
 */
public class Push<W> extends Instruction {
    private final HPrimitiveType type;
    private final W object;

    public Push(HPrimitiveType type, W object) {
        super(0);
        this.type = type;
        this.object = object;
    }

    @Override
    public String toString() {
        return "Push{" +
                "type=" + type +
                ", object=" + object +
                '}';
    }

    private HPrimitive<W> create() {
        HPrimitive<W> primitive = new HPrimitive<>(type);
        primitive.getValue().definitely(object);
        return primitive;
    }

    @Override
    public HType[] outputElements(StackContext context, HType... inputs) {
        return new HType[] {create()};
    }
}
