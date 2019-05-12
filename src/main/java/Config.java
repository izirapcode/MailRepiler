import java.util.Properties;

public class Config {

    private final String username;
    private final String password;
    private final String host = "imap.gmail.com";
    private final Properties props = System.getProperties();


    Config(String username,String password){
        this.username = username;
        this.password = password;
        props.setProperty("mail.store.protocol", "imaps");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
    }

    public Properties getProps() {
        return props;
    }


    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getHost() {
        return host;
    }


}
