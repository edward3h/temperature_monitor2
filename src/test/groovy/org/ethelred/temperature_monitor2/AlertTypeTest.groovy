/* (C) 2020 Edward Harman */
package org.ethelred.temperature_monitor2

import static org.ethelred.temperature_monitor2.AlertType.*

import spock.lang.Specification

/**
 * test choosing the alert type properly
 */
class AlertTypeTest extends Specification {
	def "test evaluate"(Range threshold, List temps, def expectType) {
		given:
		def trend = new Trend(temps)

		when:
		def result = evaluate(threshold, trend)

		then:
		result?.type == expectType

		where:
		threshold | temps | expectType
		50..75 | [43.1, 44.5, 45.6]| null
		50..75 | [53.1, 54.5, 55.6]| null
		50..75 | [77.1, 79.5, 81.6]| null
		50..75 | [45.1, 47.5, 51.6]| rising_low
		50..75 | [45.1, 60.5, 77.6]| rising_high
		50..75 | [71.1, 73.5, 76.6]| rising_high
		50..75 | [76.9, 75.4, 73.8]| falling_high
		50..75 | [51.6, 50.3, 49.7]| falling_low
	}
}
