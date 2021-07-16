package io.github.splotycode.hecate.instruction;

import io.github.splotycode.hecate.instruction.load.ALoad;
import jdk.internal.org.objectweb.asm.tree.AbstractInsnNode;
import jdk.internal.org.objectweb.asm.tree.VarInsnNode;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author David (_Esel)
 */
public class Instructions {
    private static final Map<Integer, Function<AbstractInsnNode, Instruction>> factories = new HashMap<>();

    static {
        register(0x19, node -> new ALoad(((VarInsnNode) node).var));
    }

    public static Instruction create(AbstractInsnNode node) {
        int opCode = node.getOpcode();
        Function<AbstractInsnNode, Instruction> factory = factories.get(opCode);
        if (factory == null) {
            throw new IllegalStateException(String.format("Unknown instruction opcode=%d, type=%s", opCode, node.getClass().getSimpleName()));
        }
        return factory.apply(node);
    }

    private static void register(int opCode, Function<AbstractInsnNode, Instruction> factory) {
        factories.put(opCode, factory);
    }

    private static void register(int opCode, Supplier<Instruction> factory) {
        register(opCode, node -> factory.get());
    }
}
