package org.ethelred.temperature_monitor2

import io.github.cdimascio.dotenv.Dotenv

import javax.mail.Message
import javax.mail.Session
import javax.mail.Transport
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage

/**
 * TODO
 *
 * @author eharman* @since 2020-08-01
 */
class Mail {
    def env = Dotenv.load()
    String subject
    String body

    def send() {
        Session session = Session.getDefaultInstance(new Properties())

        MimeMessage message = new MimeMessage(session)
        message.setFrom(env.MAIL_FROM)
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(env.MAIL_TO))
        message.setSubject(subject)
        message.setText(body)

        Transport transport = session.getTransport("smtp")
        transport.connect(env.MAIL_HOST, env.MAIL_PORT, env.MAIL_USER, env.MAIL_PASS)
        transport.sendMessage(message, message.getAllRecipients())
        transport.close()
    }
}
