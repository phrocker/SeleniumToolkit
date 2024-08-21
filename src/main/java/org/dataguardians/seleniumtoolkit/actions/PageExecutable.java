package org.dataguardians.seleniumtoolkit.actions;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.dataguardians.seleniumtoolkit.actions.capabilities.Variables;

/**
 * Abstract Class to represent a page action
 */
@SuperBuilder(toBuilder = true)
@Getter
@Setter
@NoArgsConstructor
public abstract class  PageExecutable {

    /**
     * Determines if variable is present.
     * @param name potential variable name.
     * @return true if variable is present
     */
    protected static boolean isVariable(String name){
        return name.contains("${");
    }

    /**
     * Get the value of a variable
     * @param name the name of the variable
     * @param defaultValue the default value
     * @return  the value of the variable
     */
    protected static String getVariableValue(String name, String defaultValue){
        return Variables.replaceVariables(name,PageMapping.registeredData);
    }
}
