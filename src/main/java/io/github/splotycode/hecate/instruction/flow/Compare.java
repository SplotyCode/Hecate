package io.github.splotycode.hecate.instruction.flow;

import io.github.splotycode.hecate.instruction.Instruction;
import io.github.splotycode.hecate.stack.StackContext;
import io.github.splotycode.hecate.tree.HType;

/**
 * @author David (_Esel)
 */
public class Compare extends Instruction {
    private final CompareOperation operation;

    public Compare(CompareOperation operation) {
        super(2);
        this.operation = operation;
    }

    @Override
    public String toString() {
        return "Compare{" +
                "operation=" + operation +
                '}';
    }

    @Override
    public HType[] outputElements(StackContext context, HType... inputs) {
        return new HType[0];
    }
}
