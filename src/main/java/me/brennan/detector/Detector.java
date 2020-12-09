package me.brennan.detector;

import me.brennan.detector.detection.Detection;
import me.brennan.detector.detection.impl.ClassLoaderDetection;
import me.brennan.detector.detection.impl.ReflectionDetection;
import me.brennan.detector.detection.impl.WebConnectionDetection;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.tree.ClassNode;

import java.io.File;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * @author Brennan
 * @since 12/8/2020
 **/
public class Detector {
    public static final List<Flag> flags = new LinkedList<>();

    public Detector(File inputJar) {
        try {
            final List<Detection> detections = new LinkedList<>();

            detections.add(new ClassLoaderDetection());
            detections.add(new ReflectionDetection());
            detections.add(new WebConnectionDetection());

            JarFile jarFile = new JarFile(inputJar);
            final Enumeration<JarEntry> entries = jarFile.entries();

            final List<ClassNode> classNodes = new LinkedList<>();

            while (entries.hasMoreElements()) {
                JarEntry entry = entries.nextElement();

                try (InputStream in = jarFile.getInputStream(entry)) {
                    if (entry.getName().endsWith(".class")) {
                        ClassReader reader = new ClassReader(in);
                        ClassNode classNode = new ClassNode();
                        reader.accept(classNode, 0);
                        classNodes.add(classNode);
                    }
                }
            }

            System.out.println("[Detector] Running detectors..");
            for(ClassNode classNode : classNodes) {
                detections.forEach(detection -> detection.detect(classNode));
            }

            for(Flag flag : flags) {
                System.out.printf("[Detector] [%s] %s, CLASS: %s %n", flag.getLevel().name(), flag.getMessage(), flag.getKlazz());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void addFlag(Flag flag) {
        flags.add(flag);
    }
}
