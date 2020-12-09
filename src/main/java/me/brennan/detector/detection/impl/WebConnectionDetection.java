package me.brennan.detector.detection.impl;

import me.brennan.detector.Detector;
import me.brennan.detector.detection.AbstractDetection;
import me.brennan.detector.Flag;
import org.objectweb.asm.tree.*;

import java.util.Arrays;

/**
 * @author Brennan
 * @since 12/8/2020
 **/
public class WebConnectionDetection extends AbstractDetection {
    private final String[] classes = new String[] {
            "java/net/HttpURLConnection",
            "java/net/HttpsURLConnection",
            "java/net/Socket",
            "java/net/InetSocketAddress"
    };

    private final String[] methods = new String[] {
            "java/net/URL:openConnection:()Ljava/net/URLConnection;",
            "java/net/URL:openStream:()Ljava/io/InputStream;"
    };

    public WebConnectionDetection() {
        super("WebConnectionDetection");
    }

    @Override
    public void detect(ClassNode classNode) {
        for(Object object : classNode.methods) {
            final MethodNode methodNode = (MethodNode) object;
            for(AbstractInsnNode abstractInsnNode : methodNode.instructions.toArray()) {
                if(abstractInsnNode instanceof MethodInsnNode) {
                    final MethodInsnNode methodInsnNode = (MethodInsnNode) abstractInsnNode;
                    if(Arrays.stream(methods).anyMatch(clazz -> clazz.equalsIgnoreCase(methodInsnNode.desc))) {
                        Detector.addFlag(new Flag(Flag.FlagLevel.WARN, "Found a Web Connection!", classNode.name));
                    }
                } else if(abstractInsnNode instanceof TypeInsnNode) {
                    final TypeInsnNode typeInsnNode = (TypeInsnNode) abstractInsnNode;
                    if(Arrays.stream(classes).anyMatch(clazz -> clazz.equalsIgnoreCase(typeInsnNode.desc))) {
                        Detector.addFlag(new Flag(Flag.FlagLevel.WARN, "Found a Web Connection!", classNode.name));
                    }
                }
            }
        }
    }
}
