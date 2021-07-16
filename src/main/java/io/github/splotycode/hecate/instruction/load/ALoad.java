package io.github.splotycode.hecate.instruction.load;

import io.github.splotycode.hecate.instruction.Instruction;
import io.github.splotycode.hecate.stack.StackContext;
import io.github.splotycode.hecate.tree.HType;

/**
 * @author David (_Esel)
 */
public class ALoad extends Instruction {
    private final int index;

    public ALoad(int index) {
        super(0);
        this.index = index;
    }

    @Override
    public HType[] outputElements(StackContext context, HType... inputs) {
        return new HType[] {context.local(index)};
    }
}
