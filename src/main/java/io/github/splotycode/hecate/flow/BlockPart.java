package io.github.splotycode.hecate.flow;

import io.github.splotycode.hecate.instruction.Instruction;

import java.util.Collection;

/**
 * @author David (_Esel)
 */
public class BlockPart {
    private final Block block;
    private final Collection<Instruction> instructions;

    public BlockPart(Block block, Collection<Instruction> instructions) {
        this.block = block;
        this.instructions = instructions;
    }

    @Override
    public String toString() {
        return "BlockPart{" +
                "instructions=" + instructions +
                '}';
    }
}
