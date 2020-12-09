package me.brennan.detector.detection;

import org.objectweb.asm.tree.ClassNode;

/**
 * @author Brennan
 * @since 12/8/2020
 **/
public interface Detection {

    void detect(ClassNode classNode);

}
