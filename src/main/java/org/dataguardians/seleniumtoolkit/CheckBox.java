package org.dataguardians.seleniumtoolkit;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebElement;


@Slf4j
@SuperBuilder(toBuilder = true)
@Getter
@Setter
@NoArgsConstructor
public class CheckBox extends ClickButton {

    @Builder.Default
    Boolean keepChecked = false;

    @Override
    protected boolean checkState(@NonNull WebElement element){
        if (element.isSelected()) {
            if (keepChecked){
                return false;
            }
        }
        return true;
    }

}
