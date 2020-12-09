package me.brennan.detector.detection.impl;

import me.brennan.detector.Detector;
import me.brennan.detector.detection.AbstractDetection;
import me.brennan.detector.Flag;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;

/**
 * @author Brennan
 * @since 12/8/2020
 **/
public class ClassLoaderDetection extends AbstractDetection {

    public ClassLoaderDetection() {
        super("ClassLoaderDetection");
    }

    @Override
    public void detect(ClassNode classNode) {
        for(Object object : classNode.methods) {
            final MethodNode methodNode = (MethodNode) object;
            for(AbstractInsnNode abstractInsnNode : methodNode.instructions.toArray()) {
                if(abstractInsnNode instanceof MethodInsnNode) {
                    final MethodInsnNode methodInsnNode = (MethodInsnNode) abstractInsnNode;
                    if(methodInsnNode.name.equalsIgnoreCase("defineClass"))
                        Detector.addFlag(new Flag(Flag.FlagLevel.ALERT, "Found a DEFINE_CLASS call, could be defining a external class!", classNode.name));
                }
            }
        }

        if(classNode.superName.equalsIgnoreCase("java/lang/ClassLoader"))
            Detector.addFlag(new Flag(Flag.FlagLevel.WARN, "Found a custom classloader!", classNode.name));
    }
}
