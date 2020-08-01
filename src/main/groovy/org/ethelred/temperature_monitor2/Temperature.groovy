package org.ethelred.temperature_monitor2

import java.time.LocalDateTime
import java.time.ZoneId

/**
 * TODO
 *
 * @author eharman* @since 2020-07-31
 */
class Temperature extends Model {
    String kind
    String source
    Double temp
    Boolean outside
    String mode
    String modesetting

    def toArray() {
        "kind source temp outside mode modesetting"
                .split()
                .collect { getProperty(it)}
                .toArray()
                .tap { println it }
    }
}
