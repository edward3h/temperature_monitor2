/* (C) 2020 Edward Harman */
package org.ethelred.temperature_monitor2

/**
 * I haven't figured out a way to automatically test email yet, so I run this process manually
 */
class ManualTestMailer {
    static void main(String[] args) {
        new Mail()
                .send(subject: "Testing Email", body: "please ignore")
    }
}
