package io.github.splotycode.hecate.stack;

import io.github.splotycode.hecate.tree.HType;

/**
 * @author David (_Esel)
 */
public class StackContext {
    private final HType[] locales;

    public StackContext(int localSize) {
        locales = new HType[localSize];
    }

    public void local(int index, HType element) {
        locales[index] = element;
    }

    public HType local(int index) {
        return locales[index];
    }
}
