package com.example.obfuscatorge;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;


public class Entities {

    // Regular expressions for entities
    public static final Pattern MAIN_CLASS_REG_EXP =
            Pattern.compile("public\\s+class\\s+([A-Za-z_][A-Za-z0-9_]*).*\\{");
    public static final Pattern CLASSES_REG_EXP =
            Pattern.compile("(?<!public\\s)\\b(?:(?:class)|(?:enum)|(?:interface))\\s+" +
                    "([A-Za-z_][A-Za-z0-9_]*)\\b");
    public static final Pattern METHODS_REG_EXP =
            Pattern.compile("(?<!@Override)\\s+\\n+.*\\b[A-Za-z0-9<>]+(?<!new|else)\\s+(\\w+)(?<!\\bmain)\\s*\\([^)]*\\)\\s*(?:\\{|;)");
    public static final Pattern VARIABLES_REG_EXP =
            Pattern.compile("(?!.*throws)(?:\\b[A-Za-z0-9\\[\\]<>]+\\s+|\\G(?!^),\\s*)([a-zA-Z_][a-zA-Z0-9_]*)" +
                    "(?:\\s*=\\s*[^,;]+)?(?=(?:\\s*,\\s*|\\s*;))");
    public static final Pattern SINGLE_LINE_COMMENTS_REG_EXP =
            Pattern.compile("(\\/\\/.*)");
    public static final Pattern MULTI_LINE_COMMENTS_REG_EXP =
            Pattern.compile("(/\\*.*?\\*/)", Pattern.DOTALL);
    public static final Pattern STRING_VALUES_REG_EXP =
            Pattern.compile("((?<!\\\\)\\\".*?(?<!\\\\)\\\")");
    public static final Pattern CHAR_VALUES_REG_EXP =
            Pattern.compile("('.*?')");
    public static final Pattern ARGUMENTS_REG_EXP =
            Pattern.compile("\\b(?!throws)[A-Za-z0-9\\[\\]]+\\s+(\\w+)\\s*(?:(?:,|\\)|:))");
    public static final Pattern IMPORTS_REG_EXP =
            Pattern.compile("((?:import\\s|package\\s).+;)");

    // Entities objects
    public static final Entities MAIN_CLASS =
            new Entities("Main Class", MAIN_CLASS_REG_EXP);
    public static final Entities CLASSES =
            new Entities("Classes", CLASSES_REG_EXP);
    public static final Entities METHODS =
            new Entities("Methods", METHODS_REG_EXP);
    public static final Entities VARIABLES =
            new Entities("Variables", VARIABLES_REG_EXP, ARGUMENTS_REG_EXP);
    public static final Entities COMMENTS =
            new Entities("Comments", SINGLE_LINE_COMMENTS_REG_EXP, MULTI_LINE_COMMENTS_REG_EXP);
    public static final Entities STRING_VALUES =
            new Entities("String Values", STRING_VALUES_REG_EXP, CHAR_VALUES_REG_EXP);
    public static final Entities IMPORTS =
            new Entities("Imports", IMPORTS_REG_EXP);

    // Instance fields
    private final String name;
    private final List<Pattern> regularExpressions = new ArrayList<Pattern>();

    /**
     * Creates new Entities object that represents entities in code that need to be manipulated
     *
     * @param name               String representation of the entities
     * @param regularExpressions List of regular expressions that match different appearances of the entities
     */
    Entities(String name, Pattern... regularExpressions) {
        this.regularExpressions.addAll(Arrays.asList(regularExpressions));
        this.name = name;
    }

    //Getters
    public String getName() {
        return name;
    }

    public List<Pattern> getRegularExpressions() {
        return regularExpressions;
    }


}
