/* (C) 2020 Edward Harman */
package org.ethelred.temperature_monitor2

import groovy.transform.Canonical
import groovy.util.logging.Log

/**
 * record an alert that was sent so we don't send the same one repeatedly
 */
@Canonical
@Log
class Alert extends Model {
    AlertType type
    String message

    void send(senders) {
        log.info("Sending alert ${message}")
        senders.each { it.send(subject: "[temperature bot] ${message}", body: message) }
    }
}
