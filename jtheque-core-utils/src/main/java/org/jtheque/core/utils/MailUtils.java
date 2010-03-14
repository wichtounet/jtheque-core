package org.jtheque.core.utils;

/*
 * This file is part of JTheque.
 *
 * JTheque is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License.
 *
 * JTheque is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with JTheque.  If not, see <http://www.gnu.org/licenses/>.
 */

import org.jtheque.utils.bean.Email;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.util.Date;
import java.util.Properties;

/**
 * Utility class for send mail.
 *
 * @author Baptiste Wicht
 */
public final class MailUtils {
    /**
     * Construct a new MailUtils. This class isn't instanciable.
     */
    private MailUtils() {
        super();
    }

    /**
     * Send an email.
     *
     * @param email The email to send.
     * @param host  The host to send the message.
     * @throws MessagingException Can throw this exception if an exception occurs during the send of the mail
     */
    public static void send(Email email, String host) throws MessagingException {
        MimeMessage msg = createMessage(host);

        configureMessage(email, msg);

        Multipart mp = new MimeMultipart();

        MimeBodyPart mbp1 = new MimeBodyPart();
        mbp1.setText(email.getMessage());

        mp.addBodyPart(mbp1);

        if (!email.getAttachedFiles().isEmpty()) {
            attachFiles(email, mp);
        }

        msg.setContent(mp);

        Transport.send(msg);
    }

    /**
     * Configure the email.
     *
     * @param email The email to get the infos from.
     * @param msg   The msg to configure.
     * @throws MessagingException Throw if an error occurs during the treatment.
     */
    private static void configureMessage(Email email, MimeMessage msg) throws MessagingException {
        msg.setFrom(new InternetAddress(email.getFrom()));

        for (String to : email.getTo()) {
            msg.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
        }

        msg.setSubject(email.getSubject());
        msg.setSentDate(new Date());
    }

    /**
     * Create a message with a specific host.
     *
     * @param host The host to use.
     * @return The mime message.
     */
    private static MimeMessage createMessage(String host) {
        Properties props = new Properties();
        props.setProperty("mail.smtp.host", host);

        Session session = Session.getInstance(props, null);
        session.setDebug(false);

        return new MimeMessage(session);
    }

    /**
     * Attach files to the message.
     *
     * @param email The email to get the files from.
     * @param mp    The multi part message.
     * @throws MessagingException Throw if an error occurs during the attaching files process.
     */
    private static void attachFiles(Email email, Multipart mp) throws MessagingException {
        for (File f : email.getAttachedFiles()) {
            BodyPart messageBodyPart = new MimeBodyPart();

            DataSource source = new FileDataSource(f);
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName(source.getName());

            mp.addBodyPart(messageBodyPart);
        }
    }
}