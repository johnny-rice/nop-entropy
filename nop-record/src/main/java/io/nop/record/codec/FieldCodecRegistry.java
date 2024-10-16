/**
 * Copyright (c) 2017-2024 Nop Platform. All rights reserved.
 * Author: canonical_entropy@163.com
 * Blog:   https://www.zhihu.com/people/canonical-entropy
 * Gitee:  https://gitee.com/canonical-entropy/nop-entropy
 * Github: https://github.com/entropy-cloud/nop-entropy
 */
package io.nop.record.codec;

import io.nop.api.core.annotations.core.GlobalInstance;
import io.nop.record.codec._gen.FieldBinaryCodecRegistrar;
import io.nop.record.codec.impl.BitmapTagBinaryCodec;
import io.nop.record.codec.impl.FixedLengthAsciiIntCodec;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@GlobalInstance
public class FieldCodecRegistry {
    public static FieldCodecRegistry DEFAULT = new FieldCodecRegistry();

    static {
        FieldBinaryCodecRegistrar.registerWordType(DEFAULT);
        DEFAULT.registerTextCodec("FLAI", FixedLengthAsciiIntCodec.INSTANCE);
        DEFAULT.registerBinaryCodec("FLAI", FixedLengthAsciiIntCodec.INSTANCE);
        DEFAULT.registerTagBinaryCodec("bitmap128", BitmapTagBinaryCodec.INSTANCE);
    }

    private final Map<String, IFieldTextCodec> textEncoders = new ConcurrentHashMap<>();

    private final Map<String, IFieldBinaryCodec> binaryEncoders = new ConcurrentHashMap<>();

    private final Map<String, IFieldTagBinaryCodec> tagBinaryEncoders = new ConcurrentHashMap<>();

    private final Map<String, IFieldTagTextCodec> tagTextEncoders = new ConcurrentHashMap<>();

    public void registerTextCodec(String name, IFieldTextCodec encoder) {
        textEncoders.put(name, encoder);
    }

    public void unregisterTextCodec(String name, IFieldTextCodec encoder) {
        textEncoders.remove(name, encoder);
    }

    public void registerBinaryCodec(String name, IFieldBinaryCodec encoder) {
        binaryEncoders.put(name, encoder);
    }

    public void unregisterBinaryCodec(String name, IFieldBinaryCodec encoder) {
        binaryEncoders.remove(name, encoder);
    }

    public void registerTagTextCodec(String name, IFieldTagTextCodec encoder) {
        tagTextEncoders.put(name, encoder);
    }

    public void unregisterTagTextCodec(String name, IFieldTagTextCodec encoder) {
        tagTextEncoders.remove(name, encoder);
    }

    public void registerTagBinaryCodec(String name, IFieldTagBinaryCodec encoder) {
        tagBinaryEncoders.put(name, encoder);
    }

    public void unregisterTagBinaryCodec(String name, IFieldTagBinaryCodec encoder) {
        tagBinaryEncoders.remove(name, encoder);
    }

    public IFieldTagTextCodec getTagTextCodec(String name) {
        return tagTextEncoders.get(name);
    }

    public IFieldTagBinaryCodec getTagBinaryCodec(String name) {
        return tagBinaryEncoders.get(name);
    }

    public IFieldTextCodec getTextCodec(String name) {
        return textEncoders.get(name);
    }

    public IFieldBinaryCodec getBinaryCodec(String name) {
        return binaryEncoders.get(name);
    }
}
