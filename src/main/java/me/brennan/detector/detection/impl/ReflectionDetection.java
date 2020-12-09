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
public class ReflectionDetection extends AbstractDetection {

    public ReflectionDetection() {
        super("ReflectionDetection");
    }

    @Override
    public void detect(ClassNode classNode) {
        for(Object object : classNode.methods) {
            final MethodNode methodNode = (MethodNode) object;
            for(AbstractInsnNode abstractInsnNode : methodNode.instructions.toArray()) {
                if(abstractInsnNode instanceof MethodInsnNode) {
                    final MethodInsnNode methodInsnNode = (MethodInsnNode) abstractInsnNode;
                    if(methodInsnNode.name.equalsIgnoreCase("getDeclaredField")) {
                        Detector.addFlag(new Flag(Flag.FlagLevel.WARN, "Tried to access a field using Reflections", classNode.name));
                    } else if(methodInsnNode.name.equalsIgnoreCase("getDeclaredMethod")) {
                        Detector.addFlag(new Flag(Flag.FlagLevel.WARN, "Tried to access a method using Reflections", classNode.name));
                    }
                }
            }
        }
    }
}
