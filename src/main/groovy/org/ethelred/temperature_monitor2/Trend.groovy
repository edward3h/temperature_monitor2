/* (C) 2020 Edward Harman */
package org.ethelred.temperature_monitor2

/**
 * TODO
 *
 * @author eharman* @since 2020-08-01
 */
class Trend {
    Number start
    Number end

    Trend(List l) {
        start = l.first()
        end = l.last()
    }

    def getRising() {
        end > start
    }

    def getFalling() {
        start > end
    }

    def cross(def value) {
        [start, end].min() < value && [start, end].max() > value
    }
}
