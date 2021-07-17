package io.github.splotycode.hecate.stack;

import io.github.splotycode.hecate.flow.BlockPart;
import io.github.splotycode.hecate.instruction.Instruction;
import io.github.splotycode.hecate.tree.HType;

import java.util.*;

/**
 * @author David (_Esel)
 */
public class StackContext {
    private final HType[] locales;
    private final Deque<HType> stack = new LinkedList<>();

    public StackContext(int localSize) {
        locales = new HType[localSize];
    }

    public void local(int index, HType element) {
        locales[index] = element;
    }

    public void execute(Collection<BlockPart> flow) {
        for (BlockPart part : flow) {
            for (Instruction instruction : part.getInstructions()) {
                execute(instruction);
            }
        }
    }

    public HType pop() {
        return stack.pop();
    }

    @Override
    public String toString() {
        return "StackContext{" +
                "locales=" + Arrays.toString(locales) +
                ", stack=" + stack +
                '}';
    }

    private void execute(Instruction instruction) {
        HType[] arguments = new HType[instruction.getInputElements()];
        for (int i = 0; i < arguments.length; i++) {
            arguments[i] = stack.pop();
        }
        HType[] output = instruction.outputElements(this, arguments);
        for (HType push : output) {
            stack.push(push);
        }
    }

    public HType local(int index) {
        return locales[index];
    }
}
