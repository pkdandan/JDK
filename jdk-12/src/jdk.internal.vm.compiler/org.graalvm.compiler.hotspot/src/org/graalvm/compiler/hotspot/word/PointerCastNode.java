/*
 * Copyright (c) 2014, 2018, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */


package org.graalvm.compiler.hotspot.word;

import static org.graalvm.compiler.nodeinfo.NodeCycles.CYCLES_0;
import static org.graalvm.compiler.nodeinfo.NodeSize.SIZE_0;

import org.graalvm.compiler.core.common.type.Stamp;
import org.graalvm.compiler.graph.Node;
import org.graalvm.compiler.graph.NodeClass;
import org.graalvm.compiler.hotspot.word.HotSpotOperation.HotspotOpcode;
import org.graalvm.compiler.nodeinfo.NodeInfo;
import org.graalvm.compiler.nodes.NodeView;
import org.graalvm.compiler.nodes.ValueNode;
import org.graalvm.compiler.nodes.calc.FloatingNode;
import org.graalvm.compiler.nodes.spi.LIRLowerable;
import org.graalvm.compiler.nodes.spi.NodeLIRBuilderTool;

import jdk.vm.ci.meta.Value;

/**
 * Cast between Word and metaspace pointers exposed by the {@link HotspotOpcode#FROM_POINTER} and
 * {@link HotspotOpcode#TO_KLASS_POINTER} operations.
 */
@NodeInfo(cycles = CYCLES_0, size = SIZE_0)
public final class PointerCastNode extends FloatingNode implements LIRLowerable, Node.ValueNumberable {

    public static final NodeClass<PointerCastNode> TYPE = NodeClass.create(PointerCastNode.class);
    @Input ValueNode input;

    public PointerCastNode(Stamp stamp, ValueNode input) {
        super(TYPE, stamp);
        this.input = input;
    }

    public ValueNode getInput() {
        return input;
    }

    @Override
    public void generate(NodeLIRBuilderTool generator) {
        Value value = generator.operand(input);
        assert value.getValueKind().equals(generator.getLIRGeneratorTool().getLIRKind(stamp(NodeView.DEFAULT))) : "PointerCastNode shouldn't change the LIRKind";

        generator.setResult(this, value);
    }
}
