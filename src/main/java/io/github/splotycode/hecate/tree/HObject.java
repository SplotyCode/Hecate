package io.github.splotycode.hecate.tree;

import io.github.splotycode.hecate.stack.unknown.Unknown;

import java.util.HashMap;

/**
 * @author David (_Esel)
 */
public class HObject implements HType {
    //private final Map<String, HField> declaredFields = new HashMap<>();
    private final Unknown<Boolean> array = new Unknown<>();
    private final Unknown<Boolean> isNull = new Unknown<>();

    public Unknown<Boolean> isNull() {
        return isNull;
    }
}
