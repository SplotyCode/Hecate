package io.github.splotycode.hecate.flow;

import jdk.internal.org.objectweb.asm.tree.AbstractInsnNode;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author David (_Esel)
 */
public class MethodFlow {
    private static List<Collection<BlockPart>> buildFlowHierarchy(Blocks blocks, AbstractInsnNode instruction) {
        List<Collection<BlockPart>> routes = new ArrayList<>();
        Block block = blocks.block(instruction);
        List<BlockPart> stack = new ArrayList<>();
        stack.add(new BlockPart(block, block.instructionsAbove(instruction)));
        collectRoutes(routes, block, stack);
        return routes;
    }

    private static void collectRoutes(List<Collection<BlockPart>> routes, Block block, List<BlockPart> stack) {
        if (block.input.isEmpty()) {
            routes.add(stack);
        }
        for (Block input : block.input) {
            List<BlockPart> copy = new ArrayList<>(stack);
            copy.add(new BlockPart(input, input.instructions()));
            collectRoutes(routes, input, copy);
        }
    }
}
