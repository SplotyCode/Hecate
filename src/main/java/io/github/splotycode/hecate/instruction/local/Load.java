package io.github.splotycode.hecate.instruction.local;

import io.github.splotycode.hecate.instruction.Instruction;
import io.github.splotycode.hecate.stack.StackContext;
import io.github.splotycode.hecate.tree.HType;

/**
 * @author David (_Esel)
 */
public class Load extends Instruction {
    public static final int[] OP_CORES = new int[] {0x19, 0x15};

    private final int index;

    public Load(int index) {
        super(0);
        this.index = index;
    }

    @Override
    public String toString() {
        return "Load{" +
                "index=" + index +
                '}';
    }

    @Override
    public HType[] outputElements(StackContext context, HType... inputs) {
        return new HType[] {context.local(index)};
    }
}
