package com.alerts;

/**
 * Repeated Alert Decorator: Checks and repeats alerts over intervals.
 * Adds repeat notification functionality to alerts.
 */
public class RepeatedAlertDecorator extends AlertDecorator {
    private int repeatCount;
    private long repeatInterval;

    public RepeatedAlertDecorator(Alert decoratedAlert, int repeatCount, long repeatInterval) {
        super(decoratedAlert);
        this.repeatCount = repeatCount;
        this.repeatInterval = repeatInterval;
    }

    @Override
    public String getCondition() {
        return decoratedAlert.getCondition() + " (Repeat x" + repeatCount + " every " + repeatInterval + "ms)";
    }

    public int getRepeatCount() {
        return repeatCount;
    }

    public long getRepeatInterval() {
        return repeatInterval;
    }
}
