package io.github.splotycode.hecate.flow;

import jdk.internal.org.objectweb.asm.tree.*;

import java.lang.annotation.Retention;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static jdk.internal.org.objectweb.asm.Opcodes.*;
import static jdk.internal.org.objectweb.asm.Opcodes.TABLESWITCH;

/**
 * @author David (_Esel)
 */
public class Blocks {
    public static Blocks parse(List<AbstractInsnNode> nodes) {
        Blocks blocks = new Blocks(nodes);
        blocks.parse();
        return blocks;
    }

    private final List<AbstractInsnNode> nodes;
    private final List<Block> blocks = new ArrayList<>();
    private final Map<AbstractInsnNode, Block> nodeToBlock = new HashMap<>();
    private Block currentBlock;

    public Blocks(List<AbstractInsnNode> nodes) {
        this.nodes = nodes;
    }

    public List<Block> blocks() {
        return blocks;
    }

    public Block block(AbstractInsnNode node) {
        return nodeToBlock.get(node);
    }

    private void parse() {
        collectBlocks();
        connectBlocks();
    }

    private void connectBlocks() {
        for (Block b : blocks) {
            AbstractInsnNode end = b.endNode;
            if (end instanceof JumpInsnNode) {
                JumpInsnNode jin = (JumpInsnNode) end;
                Block blockAtLabel = nodeToBlock.get(jin.label);
                if (end.getOpcode() == GOTO) {
                    blockAtLabel.input.add(b);
                } else {
                    Block blockAfter = nodeToBlock.get(jin.getNext());
                    blockAtLabel.input.add(b);
                    blockAfter.input.add(b);
                }
            } else if (end instanceof TableSwitchInsnNode) {
                TableSwitchInsnNode tsin = (TableSwitchInsnNode) end;
                if (tsin.dflt != null) {
                    Block blockAtDefault = nodeToBlock.get(tsin.dflt);
                    blockAtDefault.input.add(b);
                }
                for (LabelNode l : tsin.labels) {
                    Block blockAtCase = nodeToBlock.get(l);
                    blockAtCase.input.add(b);
                }
            } else if (end instanceof LookupSwitchInsnNode) {
                LookupSwitchInsnNode lsin = (LookupSwitchInsnNode) end;
                if (lsin.dflt != null) {
                    Block blockAtDefault = nodeToBlock.get(lsin.dflt);
                    blockAtDefault.input.add(b);
                }
                for (LabelNode l : lsin.labels) {
                    Block blockAtCase = nodeToBlock.get(l);
                    blockAtCase.input.add(b);
                }
            } else if (end instanceof LabelNode) {
                Block blockAtNext = nodeToBlock.get(end);
                blockAtNext.input.add(b);
            }
        }
    }

    private void ensureBlock() {
        if (currentBlock == null) {
            currentBlock = new Block();
        }
    }

    private void endBlock(AbstractInsnNode last) {
        currentBlock.endNode = last;
        blocks.add(currentBlock);
        currentBlock = null;
    }

    private void collectBlocks() {
        for (AbstractInsnNode ain : nodes) {
            ensureBlock();
            if (ain instanceof LabelNode) {
                currentBlock.label = (LabelNode) ain;
            }
            currentBlock.nodes.add(ain);
            nodeToBlock.put(ain, currentBlock);
            int op = ain.getOpcode();
            if (op >= IRETURN && op <= RETURN || ain instanceof JumpInsnNode || op == LOOKUPSWITCH || op == TABLESWITCH) {
                endBlock(ain);
                continue;
            }
            if (ain.getNext() != null && (ain.getNext() instanceof LabelNode)) {
                endBlock(ain.getNext());
            }
        }
    }

}
