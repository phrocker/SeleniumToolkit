package org.dataguardians.seleniumtoolkit;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.dataguardians.seleniumtoolkit.capabilities.Variables;

@SuperBuilder(toBuilder = true)
@Getter
@Setter
@NoArgsConstructor
public abstract class  PageExecutable {

    protected static boolean isVariable(String name){
        return name.contains("${");
    }

    protected static String getVariableValue(String name, String defaultValue){
        return Variables.replaceVariables(name,PageMapping.registeredData);
    }
}
