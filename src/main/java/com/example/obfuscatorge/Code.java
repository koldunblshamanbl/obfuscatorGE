package com.example.obfuscatorge;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Code {

    // Reference fields
    private static final Logger logger = LogManager.getLogger(Code.class);
    private final Encoder encoder = new Encoder();

    // Instance fields
    private String code;
    private final Map<String, List<String>> foundEntities = new HashMap<String, List<String>>();
    private final String specialReplacementString = "@@@@@";

    /**
     * Creates new Code object representing code in file
     *
     * @param code Code in file
     */
    public Code(String code) {
        this.code = code;
    }

    // Getters
    public String getCode() {
        return code;
    }

    public List<String> getMentionsOf(Entities entities) {
        String entitiesName = entities.getName();
        return foundEntities.get(entitiesName);
    }

    public List<String> getEncodedMentionsOf(Entities entities) {
        List<String> encodedMentions = getMentionsOf(entities).stream().map(encoder::encode).collect(Collectors.toList());
        return encodedMentions;
    }

    // Methods

    /**
     * Finds specified entities and saves them in 'foundEntities' variable for future manipulation
     *
     * @param entitiesArray Entities that need to be found and remembered
     */
    private void findAndSave(Entities... entitiesArray) {

        for (Entities entities : entitiesArray) {

            //Initializing new list in map for found mentions of these entities
            foundEntities.put(entities.getName(), new ArrayList<String>());

            for (Pattern regularExpression : entities.getRegularExpressions()) {

                Matcher codeMatcher = regularExpression.matcher(code);

                while (codeMatcher.find()) {
                    /*
                     *  Due to complexity of capturing exact entities with regular expression 'Match' object
                     *  contains a lot of 'scrap' together with needed part which is contained in group 1
                     */
                    String essentialPartOfMatch = codeMatcher.group(1);

                    // Preserving found entities
                    getMentionsOf(entities).add(essentialPartOfMatch);

                    logger.info(String.format("Mention of %s has been found: %s", entities.getName(), codeMatcher.group(1)));
                }

                if (getMentionsOf(entities).isEmpty()) {
                    logger.info(String.format("No mentions of %s has been found", entities.getName()));
                }
            }
        }
    }

    /**
     * Replaces all mentions of specified entities (found using the 'findAndSave' method) with their encoded version
     *
     * @param entitiesArray Entities that need to be encoded
     */
    public void encodeMentionsOf(Entities... entitiesArray) {

        findAndSave(entitiesArray);

        for (Entities entities : entitiesArray) {

            List<String> mentions = getMentionsOf(entities);

            // If method 'findAndSave' wasn't invoked or no entities were found
            if (mentions == null || mentions.isEmpty()) {
                logger.info(String.format("No mentions of %s to encode", entities.getName()));
                return;
            }

            for (String mention : mentions) {
                String encodedVersion = encoder.encode(mention);

                // Replacing mentions with their encoded version
                // '\b' meta symbols are required to match only full words
                code = code.replaceAll("\\b" + mention + "\\b", encodedVersion);
            }

            logger.info(String.format("Mentions of %s have been encoded", entities.getName()));
        }
    }

    /**
     * Deletes all specified Entities
     *
     * @param entitiesArray Entities that need to be deleted
     */
    public void delete(Entities... entitiesArray) {

        for (Entities entities : entitiesArray) {

            for (Pattern regExp : entities.getRegularExpressions()) {
                Matcher codeMatcher = regExp.matcher(code);

                // If no matches were found in code
                if (!codeMatcher.find()) {
                    logger.info(String.format("No mentions of %s to delete", entities.getName()));
                    return;
                }

                // Deleting all substrings matching the regular expression of entity
                code = codeMatcher.replaceAll("");
            }

            logger.info(String.format("Mentions of %s have been deleted", entities.getName()));
        }
    }

    /**
     * Conceals specified entities (found using the 'findAndSave' method) that do not require obfuscation with
     * special string in order to avoid errors caused by their unrestricted (therefore non-predictable) syntax
     *
     * @param entitiesArray Entities that need to be concealed
     */
    public void conceal(Entities... entitiesArray) {

        findAndSave(entitiesArray);

        int counter = 1;

        for (Entities entities : entitiesArray) {

            List<String> mentions = getMentionsOf(entities);

            // If method 'findAndSave' wasn't invoked or no entities were found
            if (mentions == null || mentions.isEmpty()) {
                logger.info(String.format("No mentions of %s to conceal", entities.getName()));
                return;
            }

            for (Pattern regExp : entities.getRegularExpressions()) {

                StringBuilder codeWithConcealedEntities = new StringBuilder();
                Matcher codeMatcher = regExp.matcher(code);

                while (codeMatcher.find()) {

                    // Concealing mentions by replacing them with specialReplacementString in order to avoid errors
                    codeMatcher.appendReplacement(codeWithConcealedEntities,
                            specialReplacementString + counter);
                    counter++;
                }

                codeMatcher.appendTail(codeWithConcealedEntities);
                code = codeWithConcealedEntities.toString();
            }

            logger.info(String.format("Mentions of %s have been concealed", entities.getName()));
        }
    }

    /**
     * Reveals specified entities that were concealed previously
     *
     * @param entitiesArray Entities that need to be revealed
     */
    public void reveal(Entities... entitiesArray) {

        int counter = 1;

        for (Entities entities : entitiesArray) {

            List<String> mentions = getMentionsOf(entities);

            // If method 'findAndSave' wasn't invoked or no entities were found
            if (mentions == null || mentions.isEmpty()) {
                logger.info(String.format("No mentions of %s to reveal", entities.getName()));
                return;
            }

            for (String mention : mentions) {

                // Revealing previously concealed mentions
                // \d meta symbol in negative-lookahead is required to match only full sequence of digits
                code = code.replaceAll(specialReplacementString + counter + "(?!\\d)",
                        Matcher.quoteReplacement(mention));
                counter++;
            }

            logger.info(String.format("Mentions of %s has been revealed", entities.getName()));
        }
    }
}

