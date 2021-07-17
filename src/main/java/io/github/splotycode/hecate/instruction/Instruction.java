package io.github.splotycode.hecate.instruction;

import io.github.splotycode.hecate.stack.StackContext;
import io.github.splotycode.hecate.tree.HType;

/**
 * @author David (_Esel)
 */
public abstract class Instruction {
    private final int inputElements;

    public Instruction(int inputElements) {
        this.inputElements = inputElements;
    }

    public int getInputElements() {
        return inputElements;
    }

    public abstract HType[] outputElements(StackContext context, HType... inputs);
}
