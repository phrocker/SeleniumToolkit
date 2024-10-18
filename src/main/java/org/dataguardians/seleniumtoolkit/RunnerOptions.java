package org.dataguardians.seleniumtoolkit;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RunnerOptions {
    @Builder.Default
    boolean disableCookies = false;

    @Builder.Default
    boolean headless = false;

    @Builder.Default
    List<String> disabledCookies = new ArrayList<>();
}
