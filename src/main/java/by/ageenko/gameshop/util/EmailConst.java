package by.ageenko.gameshop.util;

public class EmailConst {
    public static final String companyEmail = "Ceageenk02@gmail.com";
    public static final String companyName = "GameShop";
    public static final String subject = "Please verify your registration";
    public static final String content = "Dear [[name]],<br>"
            + "Please click the link below to verify your registration:<br>"
            + "<h3><a href=\"[[URL]]\" target=\"_self\">VERIFY</a></h3>"
            + "Thank you,<br>"
            + "Your company name.";;
}
