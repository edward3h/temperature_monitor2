/* (C) 2020 Edward Harman */
package org.ethelred.temperature_monitor2

import groovy.time.TimeCategory

class App {
    static void main(String[] args) {
        new App().run()
    }

    def threshold = 50..75

    def tempSources = [
        new OpenWeatherSource()
    ]

    void run() {
        use(TimeCategory) {
            new Store().withCloseable { store ->
                def temps = tempSources.collectMany { it.temps }
                store << temps
                def lastAlert = store.lastAlert
                def external = store.recentOutsideTemps
                if (!external || external.size() < 2) return null
                def trend = new Trend(external*.temp)
                def alert = AlertType.evaluate(threshold, trend)
                if (alert && alert.type != lastAlert?.type) {
                    store << alert
                    alert.send()
                }
                null
            }
        }
    }
}
