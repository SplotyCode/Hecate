package io.github.splotycode.hecate.instruction.local;

import io.github.splotycode.hecate.instruction.Instruction;
import io.github.splotycode.hecate.stack.StackContext;
import io.github.splotycode.hecate.tree.HType;

/**
 * @author David (_Esel)
 */
public class Store extends Instruction {
    public static final int[] OP_CORES = new int[] {0x36};

    private final int index;

    public Store(int index) {
        super(1);
        this.index = index;
    }

    @Override
    public String toString() {
        return "Store{" +
                "index=" + index +
                '}';
    }

    @Override
    public HType[] outputElements(StackContext context, HType... inputs) {
        context.local(index, inputs[0]);
        return new HType[0];
    }
}
