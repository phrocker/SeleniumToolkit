package org.dataguardians.seleniumtoolkit.actions.configuration;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.dataguardians.seleniumtoolkit.actions.ActionEnum;
import org.dataguardians.seleniumtoolkit.actions.PageExecutable;

import java.util.ArrayList;
import java.util.List;

@SuperBuilder
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class PageExecutions {
    String name;
    String startUrl;

    String stopUrl;
    List<PageExecutable> actions;

    @Builder.Default
    ActionEnum actionEnum = ActionEnum.ALL_ACTIONS_THEN_ALL_VALIDATION;

    @Builder.Default
    List<PageExecutions> dependsOn = new ArrayList<>();
}
