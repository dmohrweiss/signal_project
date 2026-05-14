package com.alerts;

/**
 * Repeated Alert Decorator: Adds priority information to alerts.
 * Indicates alerts that need urgent attention.
 */
public class PriorityAlertDecorator extends AlertDecorator {
    private String priorityLevel;

    public PriorityAlertDecorator(Alert decoratedAlert, String priorityLevel) {
        super(decoratedAlert);
        this.priorityLevel = priorityLevel;
    }

    @Override
    public String getCondition() {
        return "[" + priorityLevel + "] " + decoratedAlert.getCondition();
    }

    public String getPriorityLevel() {
        return priorityLevel;
    }
}
