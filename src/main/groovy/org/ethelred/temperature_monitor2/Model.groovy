package org.ethelred.temperature_monitor2

import java.time.LocalDateTime

/**
 * TODO
 *
 * @author eharman* @since 2020-08-01
 */
class Model {
    Date created_at

    def setCreated_at(String value) {
        created_at = LocalDateTime.parse(value, 'yyyy-MM-dd HH:mm:ss').toDate()
    }
}
