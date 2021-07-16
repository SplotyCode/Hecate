package io.github.splotycode.hecate.flow;

import io.github.splotycode.hecate.instruction.Instruction;
import io.github.splotycode.hecate.tree.HType;
import jdk.internal.org.objectweb.asm.tree.AbstractInsnNode;
import jdk.internal.org.objectweb.asm.tree.InsnList;

import java.util.*;

/**
 * @author David (_Esel)
 */
public class MethodFlow {
    private static List<Collection<Instruction>> buildFlowHierarchy(Blocks blocks, AbstractInsnNode instruction) {
        List<Collection<Instruction>> routes = new ArrayList<>();
        Block block = blocks.block(instruction);
        List<Instruction> current = block.instructionsAbove(instruction);
        collectRoutes(routes, block, current);
        return routes;
    }

    private static void collectRoutes(List<Collection<Instruction>> routes, Block block, List<Instruction> stack) {
        routes.add(stack);
        for (Block input : block.input) {
            collectRoutes(routes, input, input.prepend(stack));
        }
    }
}
