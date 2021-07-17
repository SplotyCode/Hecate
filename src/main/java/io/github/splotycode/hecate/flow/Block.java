package io.github.splotycode.hecate.flow;

import io.github.splotycode.hecate.instruction.Instruction;
import io.github.splotycode.hecate.instruction.Instructions;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.LabelNode;

import java.util.*;

/**
 * @author David (_Esel)
 */
public class Block {
    LabelNode label;
    List<Connection> input = new ArrayList<>();
    AbstractInsnNode endNode;
    List<AbstractInsnNode> nodes = new ArrayList<>();

    public List<Instruction> instructions() {
        List<Instruction> instructions = new ArrayList<>();
        for (AbstractInsnNode node : nodes) {
            instructions.add(Instructions.create(node));
        }
        return instructions;
    }

    @Override
    public String toString() {
        return "Block{" +
                "nodes=" + instructions() +
                '}';
    }

    public List<Instruction> instructionsAbove(AbstractInsnNode above) {
        List<Instruction> instructions = new LinkedList<>();
        for (AbstractInsnNode node : nodes) {
            if (node == above) {
                break;
            }
            instructions.add(Instructions.create(node));
        }
        return instructions;
    }
}
