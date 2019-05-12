import org.apache.commons.io.FileUtils;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.search.FlagTerm;
import javax.mail.search.SearchTerm;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Mailbox {

    final Store store;
    final Config config;
    final Session session;

    public Mailbox(final Config config) throws NoSuchProviderException {
        this.config = config;
        Properties props = config.getProps();
        this.session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(config.getUsername(),
                                config.getPassword());
                    }
                });
        this.store = session.getStore("imaps");

    }

    void connectToMailbox() throws MessagingException {
        String host = config.getHost();
        String username = config.getUsername();
        String password = config.getPassword();
        store.connect(host, username, password);
    }

    void closeMailbox() throws MessagingException {
        store.close();
    }

    Message[] getUnreadMessages() throws MessagingException {
        Folder inbox = store.getFolder("Inbox");
        inbox.open(Folder.READ_ONLY);
        Flags flags = new Flags(Flags.Flag.SEEN);
        SearchTerm terms = new FlagTerm(flags, false);
        return inbox.search(terms);
    }

    void sendMail(Address to, String subject, InputStream inputStream) throws MessagingException, IOException {
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(config.getHost()));
        message.addRecipient(Message.RecipientType.TO, to);
        message.setSubject("Reply to :" + subject);


        BodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setText("This is message body");

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);

        messageBodyPart = new MimeBodyPart();
        File file = new File("temporary.xlsx");
        //FileUtils.copyInputStreamToFile(inputStream,file);
        DataSource source = new FileDataSource(file);
        messageBodyPart.setDataHandler(new DataHandler(source));
        messageBodyPart.setFileName("wokanda.xlsx");
        multipart.addBodyPart(messageBodyPart);

        message.setContent(multipart);
        Transport.send(message);
        file.delete();
    }

    InputStream getAttachment(Message message) throws IOException, MessagingException {
        Multipart multiPart = (Multipart) message.getContent();
        MimeBodyPart part = null;
        for (int i = 0; i < multiPart.getCount(); i++) {
            part = (MimeBodyPart) multiPart.getBodyPart(i);
            System.out.println(part.getFileName());
            if(part.getFileName() != null)
            if(part.getFileName().contains("wokanda"))
                break;
        }
        InputStream inputStream = part.getInputStream();
/*        OutputStream outputStream =  new FileOutputStream(new File("wokanda.xlsx"));
        int read = 0;
        byte[] bytes = new byte[1024];

        while ((read = inputStream.read(bytes)) != -1) {
            outputStream.write(bytes, 0, read);
        }
        System.out.println("Done!");
        outputStream.close();*/
        return inputStream;
    }

}
