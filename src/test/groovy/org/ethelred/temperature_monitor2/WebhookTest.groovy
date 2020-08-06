/* (C) 2020 Edward Harman */
package org.ethelred.temperature_monitor2

import spock.lang.Specification
import groovy.json.JsonSlurper

/**
 * test webhook using echo service
 */
class WebhookTest extends Specification {
    def "send webhook message to echo"() {
        given:
        def sender = new Webhook(url: "https://postman-echo.com/post")

        when:
        sender.send(body: bodyText)

        then:
        sender.responseCode == 200
        sender.responseText

        when:
        def json = new JsonSlurper().parseText(sender.responseText)

        then:
        json.form.content == bodyText

        where:
        bodyText | _
        "Hello, world!" | _
    }
}
