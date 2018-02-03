/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilitarios;

import java.util.Properties;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author Henrique
 */
public class EnviarEmail {

    final String EMAIL = "systemautonet@gmail.com";
    final String SENHA = "systemPet";

    Properties props() {
        Properties props = new Properties();
        /**
         * Parâmetros de conexão com servidor Gmail
         */
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");

        return props;
    }

    public void enviarEmail(String remetente, String conteudo, String assunto) {

        Properties props = props();

        Session session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(EMAIL, SENHA);
            }
        });
        session.setDebug(true);
        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(EMAIL)); //Remetente

            Address[] toUser = InternetAddress //Destinatário(s)
                    .parse(remetente);

            message.setRecipients(Message.RecipientType.TO, toUser);
            message.setSubject(assunto);//Assunto
          //  message.setText(conteudo);
           message.setContent(conteudo, "text/html; charset=utf-8");
            /**
             * Método para enviar a mensagem criada
             */
            Transport.send(message);

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

}
