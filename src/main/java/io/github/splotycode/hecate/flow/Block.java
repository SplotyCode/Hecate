package io.github.splotycode.hecate.flow;

import io.github.splotycode.hecate.instruction.Instruction;
import io.github.splotycode.hecate.instruction.Instructions;
import jdk.internal.org.objectweb.asm.tree.AbstractInsnNode;
import jdk.internal.org.objectweb.asm.tree.LabelNode;

import java.util.*;

/**
 * @author David (_Esel)
 */
public class Block {
    LabelNode label;
    List<Block> input = new ArrayList<>();
    AbstractInsnNode endNode;
    List<AbstractInsnNode> nodes = new ArrayList<>();

    public List<Instruction> instructions() {
        List<Instruction> instructions = new ArrayList<>();
        for (AbstractInsnNode node : nodes) {
            instructions.add(Instructions.create(node));
        }
        return instructions;
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
