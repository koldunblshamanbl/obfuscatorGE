package com.example.obfuscatorge;

/*
 * This proxy main class is needed due to sad specificity of Java FX
 * that was explained in this CSR (https://bugs.openjdk.org/browse/JDK-8256422):
 *
 * "JavaFX is built and distributed as a set of named modules, each in its own modular jar file,
 * and the JavaFX runtime expects its classes to be loaded from a set of named javafx.* modules,
 * and does not support loading those modules from the classpath."
 *
 * And:
 * "...when the JavaFX classes are loaded from the classpath, it breaks encapsulation,
 * since we no longer get the benefit of the java module system."
 *
 * So I have to use this intermediate.
 */

public class Starter {
    public static void main(String[] args) {

        // Simply invoking REAL main method of REAL main class
        MainApplication.main(args);
    }
}
