package io.github.splotycode.hecate.flow;

import org.objectweb.asm.tree.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.objectweb.asm.Opcodes.*;

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
        for (Block currentBlock : blocks) {
            this.currentBlock = currentBlock;
            AbstractInsnNode end = currentBlock.endNode;
            if (end instanceof JumpInsnNode) {
                JumpInsnNode jin = (JumpInsnNode) end;
                if (end.getOpcode() == GOTO) {
                    connect(jin.label);
                } else {
                    connect(jin.label, Connection.Type.ACTIVE);
                    connect(jin.getNext());
                }
            } else if (end instanceof TableSwitchInsnNode) {
                TableSwitchInsnNode switchNode = (TableSwitchInsnNode) end;
                connectSwitch(switchNode.dflt, switchNode.labels);
            } else if (end instanceof LookupSwitchInsnNode) {
                LookupSwitchInsnNode switchNode = (LookupSwitchInsnNode) end;
                connectSwitch(switchNode.dflt, switchNode.labels);
            } else if (end instanceof LabelNode) {
                connect(end);
            }
        }
    }

    private void connect(AbstractInsnNode instruction) {
        connect(instruction, Connection.Type.DEFAULT);
    }

    private void connect(AbstractInsnNode instruction, Connection.Type type) {
        connect(nodeToBlock.get(instruction), type);
    }
    
    private void connect(Block block, Connection.Type type) {
        block.input.add(new Connection(currentBlock, type));
    }
    
    private void connectSwitch(LabelNode defaultBranch, List<LabelNode> labels) {
        if (defaultBranch != null) {
            connect(defaultBranch);
        }
        for (LabelNode label : labels) {
            connect(label);
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
            if (ain instanceof LineNumberNode) {
                continue;
            }
            ensureBlock();
            nodeToBlock.put(ain, currentBlock);
            if (ain instanceof LabelNode) {
                currentBlock.label = (LabelNode) ain;
                continue;
            }
            int op = ain.getOpcode();
            if (op >= IRETURN && op <= RETURN || ain instanceof JumpInsnNode || op == LOOKUPSWITCH || op == TABLESWITCH) {
                endBlock(ain);
                continue;
            }
            if (ain.getNext() != null && (ain.getNext() instanceof LabelNode)) {
                endBlock(ain.getNext());
                continue;
            }
            currentBlock.nodes.add(ain);
        }
    }
}
