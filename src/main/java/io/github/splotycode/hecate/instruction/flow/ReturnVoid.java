package io.github.splotycode.hecate.instruction.flow;

import io.github.splotycode.hecate.instruction.Instruction;
import io.github.splotycode.hecate.stack.StackContext;
import io.github.splotycode.hecate.tree.HType;

/**
 * @author David (_Esel)
 */
public class ReturnVoid extends Instruction {
    public ReturnVoid() {
        super(0);
    }

    @Override
    public String toString() {
        return "ReturnVoid{}";
    }

    @Override
    public HType[] outputElements(StackContext context, HType... inputs) {
        return new HType[0];
    }
}
