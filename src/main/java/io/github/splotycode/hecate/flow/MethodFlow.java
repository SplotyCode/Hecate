package io.github.splotycode.hecate.flow;

import org.objectweb.asm.tree.AbstractInsnNode;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author David (_Esel)
 */
public class MethodFlow {
    public static List<Collection<BlockPart>> buildFlowHierarchy(Blocks blocks, AbstractInsnNode instruction) {
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
            List<BlockPart> copy = new ArrayList<>();
            copy.add(new BlockPart(input, input.instructions()));
            copy.addAll(stack);
            collectRoutes(routes, input, copy);
        }
    }
}
