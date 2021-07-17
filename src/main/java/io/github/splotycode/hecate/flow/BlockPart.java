package io.github.splotycode.hecate.flow;

import io.github.splotycode.hecate.instruction.Instruction;

import java.util.Collection;

/**
 * @author David (_Esel)
 */
public class BlockPart {
    private final Connection connection;
    private final Collection<Instruction> instructions;

    public BlockPart(Connection connection, Collection<Instruction> instructions) {
        this.connection = connection;
        this.instructions = instructions;
    }

    public Collection<Instruction> getInstructions() {
        return instructions;
    }

    @Override
    public String toString() {
        return "BlockPart{" +
                "instructions=" + instructions +
                '}';
    }
}
