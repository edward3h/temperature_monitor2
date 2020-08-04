/* (C) 2020 Edward Harman */
package org.ethelred.temperature_monitor2

import groovy.json.JsonSlurper
import io.github.cdimascio.dotenv.Dotenv

/**
 *
 */
class OpenWeatherSource {
    def getTemps() {
        def env = Dotenv.load()
        def params = [
            appid: env.OPENWEATHER_ID,
            lat: env.LAT,
            lon: env.LON,
            exclude: "minutely,hourly,daily",
            units: "imperial"
        ]
        def query = params.collect { it }.join '&'
        def url = "https://api.openweathermap.org/data/2.5/onecall?${query}".toURL()
        def json = new JsonSlurper().parse(url)
        return [
            new Temperature(
            kind: 'thirdParty',
            source: 'openweathermap.org',
            temp: json?.current?.temp,
            outside: true
            )
        ]
    }
}
