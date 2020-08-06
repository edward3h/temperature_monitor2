/* (C) 2020 Edward Harman */
package org.ethelred.temperature_monitor2

import groovy.time.TimeCategory
import io.github.cdimascio.dotenv.Dotenv

class App {
    static void main(String[] args) {
        new App().run()
    }

    def env = Dotenv.load()

    def threshold = 50..75

    def tempSources = [
        new OpenWeatherSource()
    ]

    def senders = [
        new Mail(),
        new Webhook(url: env.WEBHOOK_URL)
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
                    alert.send(senders)
                }
                null
            }
        }
    }
}
