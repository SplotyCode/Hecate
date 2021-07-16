package io.github.splotycode.hecate.instruction.constant;

import io.github.splotycode.hecate.instruction.Instruction;
import io.github.splotycode.hecate.stack.StackContext;
import io.github.splotycode.hecate.tree.HObject;
import io.github.splotycode.hecate.tree.HType;

/**
 * @author David (_Esel)
 */
public class AConstNull extends Instruction {
    public AConstNull() {
        super(0);
    }

    private HObject createNull() {
        HObject object = new HObject();
        object.isNull().definitely(true);
        return object;
    }

    @Override
    public HType[] outputElements(StackContext context, HType... inputs) {
        return new HType[] {createNull()};
    }
}
