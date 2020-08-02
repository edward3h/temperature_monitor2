package org.ethelred.temperature_monitor2

/**
 */
class ManualTestMailer {
    static void main(String[] args) {
        new Mail(subject: "Testing Email", body: "please ignore")
        .send()
    }
}
