/* (C) 2020 Edward Harman */
package org.ethelred.temperature_monitor2

/**
 * send to webhook (e.g. Discord)
 */
class Webhook {
    String url

    // visible for testing
    def responseCode
    def responseText

    def send(params) {
        def post = new URL(url)
        def connection = post.openConnection()
        connection.with {
            doOutput = true
            outputStream.withWriter {w ->
                w << "content=${params.body}"
            }
            responseText = content.text
        }
        this.responseCode = connection.responseCode
    }
}
