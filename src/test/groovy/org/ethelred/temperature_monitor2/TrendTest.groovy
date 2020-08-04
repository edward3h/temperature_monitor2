/* (C) 2020 Edward Harman */
package org.ethelred.temperature_monitor2

import spock.lang.Specification

/**
 * Test Trend class
 */
class TrendTest extends Specification {
    def "check some sample values"(List input, def c, def expectRising, def expectFalling, def expectCross) {
        when:
        def trend = new Trend(input)

        then:
        trend.rising == expectRising
        trend.falling == expectFalling
        trend.cross(c) == expectCross

        where:
        input | c | expectRising | expectFalling | expectCross
        [0.0]| 0 | false | false | false
        [1.0, 2.0, 3.0]| 2 | true | false | true
        [1.9, 2.0, 2.1]| 2 | true | false | true
        [1.9, 2.0, 2.1]| 3 | true | false | false
        [2.1, 2.0, 1.9]| 2 | false | true | true
    }
}
