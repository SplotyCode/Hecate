package io.github.splotycode.hecate.instruction;

import io.github.splotycode.hecate.instruction.flow.Compare;
import io.github.splotycode.hecate.instruction.flow.CompareOperation;
import io.github.splotycode.hecate.instruction.flow.Label;
import io.github.splotycode.hecate.instruction.flow.ReturnVoid;
import io.github.splotycode.hecate.instruction.local.Load;
import io.github.splotycode.hecate.instruction.local.Store;
import io.github.splotycode.hecate.instruction.stack.Push;
import io.github.splotycode.hecate.tree.primitive.HPrimitiveType;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.IntInsnNode;
import org.objectweb.asm.tree.VarInsnNode;

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
        register(node -> new Load(((VarInsnNode) node).var), Load.OP_CORES);
        register(node -> new Store(((VarInsnNode) node).var), Store.OP_CORES);
        register(node -> new Push<>(HPrimitiveType.BYTE, (byte) ((IntInsnNode) node).operand), 0x10);
        register(() -> new Compare(CompareOperation.LESS_EQUALS), 0xa4);
        register(ReturnVoid::new, 0xb1);
        register(Label::new, 0xffffffff);
    }

    public static Instruction create(AbstractInsnNode node) {
        int opCode = node.getOpcode();
        Function<AbstractInsnNode, Instruction> factory = factories.get(opCode);
        if (factory == null) {
            throw new IllegalStateException(String.format("Unknown instruction opcode=%s, type=%s", Integer.toHexString(opCode), node.getClass().getSimpleName()));
        }
        return factory.apply(node);
    }

    private static void register(Function<AbstractInsnNode, Instruction> factory, int... opCodes) {
        for (int opCode : opCodes) {
            factories.put(opCode, factory);
        }
    }

    private static void register(Supplier<Instruction> factory, int... opCodes) {
        register(node -> factory.get(), opCodes);
    }
}
