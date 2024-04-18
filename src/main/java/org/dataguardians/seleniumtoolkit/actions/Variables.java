package org.dataguardians.seleniumtoolkit.actions.capabilities;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Variables {

    public static String replaceVariables(String text, Map<String, String> variables) {
        return replaceVariables(text, variables, null);
    }
    public static String replaceVariables(String text, Map<String, String> variables, String defaultValue) {
        // Pattern to match ${variableName}
        Pattern pattern = Pattern.compile("\\$\\{([^}]+)\\}");
        Matcher matcher = pattern.matcher(text);

        // StringBuffer to store the result
        StringBuffer result = new StringBuffer();

        while (matcher.find()) {
            // Extract variable name
            String variableName = matcher.group(1).trim();

            // Get the value for the variable from the map
            String replacement = variables.get(variableName);

            // If the variable is not found in the map, keep the original placeholder
            System.out.println(variableName + " = " + replacement);
            if (replacement == null) {
                if (defaultValue != null)
                    replacement = defaultValue;
                else
                    // Keep the original placeholder (e.g. ${variableName}
                    replacement = matcher.group(0);
                System.out.println("leaving as " + replacement);
            }

            // Replace the variable placeholder in the result
            matcher.appendReplacement(result, Matcher.quoteReplacement(replacement));
        }

        // Append the rest of the text
        matcher.appendTail(result);

        return result.toString();
    }
}
