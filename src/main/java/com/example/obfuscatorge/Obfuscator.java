package com.example.obfuscatorge;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Obfuscator {

    // Reference fields
    private static final Logger logger = LogManager.getLogger(Obfuscator.class);

    // Methods

    /**
     * Implements the whole process of file obfuscation
     *
     * @param file The file to be obfuscated
     * @return List that contains name of obfuscated file (encoded version of code's main class' name)
     * and the code itself
     * @throws IOException Can't read the file's content
     * @throws IndexOutOfBoundsException No main class was found in code
     */
    public List<String> obfuscate(File file) throws IOException, IndexOutOfBoundsException {

        // Creating Code object
        Code code = new Code(Files.readString(file.toPath()));
        logger.info(String.format("Working with %s", file.toPath()));

        /*
            Preparing code for analyzing by deletion or concealment of entities that contain unrestricted syntax
            and do not require obfuscation
        */
        code.conceal(Entities.IMPORTS, Entities.STRING_VALUES);
        code.delete(Entities.COMMENTS);

        // Replacing found mentions with their encoded version
        code.encodeMentionsOf(Entities.MAIN_CLASS, Entities.CLASSES, Entities.METHODS, Entities.VARIABLES);

        // Obtaining encoded name of file
        String encodedFileName = code.getEncodedMentionsOf(Entities.MAIN_CLASS).get(0);

        // Reveal previously concealed entities
        code.reveal(Entities.IMPORTS, Entities.STRING_VALUES);

        // Finishing work
        logger.info(String.format("Done working with %s", file.toPath()));
        List<String> obfuscatedCode = new ArrayList<String>(List.of(encodedFileName, code.getCode()));

        return obfuscatedCode;
    }
}
