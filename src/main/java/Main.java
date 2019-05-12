import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import javax.mail.Message;
import java.awt.*;
import java.io.*;
import java.util.Calendar;

public class Main {

    static private Gui gui;
    static private Config config = new Config("foo@gmail.com",
            "foo");

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                gui = new Gui();
            }
        });
    }

    public static void reply(int day, int month, int hour, int minute){
        try {
            Mailbox mailbox = new Mailbox(config);
            mailbox.connectToMailbox();
            int unreadedMessages = mailbox.getUnreadMessages().length;
            while(unreadedMessages == mailbox.getUnreadMessages().length) {
                Thread.sleep(10000);
            }
            Message lastMessage = mailbox.getUnreadMessages()[unreadedMessages];
            InputStream inputStream = mailbox.getAttachment(lastMessage); // Input z maila*/
            //FileInputStream inputStream = new FileInputStream(new File("wokanda.xlsx")); // Input z pliku
            ExcelEditor excelEditor = new ExcelEditor(inputStream);
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.MONTH,month-1);
            calendar.set(Calendar.DAY_OF_MONTH,day);
            calendar.set(Calendar.HOUR_OF_DAY,hour);
            calendar.set(Calendar.MINUTE,minute);
            excelEditor.signExcel(calendar);
            mailbox.sendMail(lastMessage.getReplyTo()[0],lastMessage.getSubject(),inputStream);
            mailbox.closeMailbox();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
