package org.dataguardians.seleniumtoolkit.actions;

/**
 * Enum to define the action type
 */
public enum ActionEnum {

    /**
     * All actions are performed before any validation
     */
    ALL_ACTIONS_THEN_ALL_VALIDATION,
    /**
     * Each action is performed before each validation
     */
    EACH_ACTION_THEN_EACH_VALIDATION;
}
