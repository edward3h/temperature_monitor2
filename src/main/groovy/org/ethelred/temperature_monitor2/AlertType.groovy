/* (C) 2020 Edward Harman */
package org.ethelred.temperature_monitor2

/**
 * TODO
 *
 * @author eharman* @since 2020-07-31
 */
enum AlertType {
    rising_high({"Temperature is rising ${it}"}),
    falling_high({"Temperature is falling ${it}"}),
    rising_low({"Temperature is rising ${it}"}),
    falling_low({"Temperature is falling ${it}"});

    def getMessage;

    AlertType(def getMessage) {
        this.getMessage = getMessage
    }

    Alert create(def current) {
        new Alert(type: this, message: getMessage(current))
    }

    static Alert evaluate(Range threshold, Trend temps) {
        def alertType = null
        if (temps.rising && temps.cross(threshold.to)) {
            alertType = rising_high
        } else if (temps.rising && temps.cross(threshold.getFrom())) {
            alertType = rising_low
        } else if (temps.falling && temps.cross(threshold.getFrom())) {
            alertType = falling_low
        } else if (temps.falling && temps.cross(threshold.getTo())) {
            alertType = falling_high
        }

        if (alertType) {
            return alertType.create(temps.end)
        }

        return null
    }
}