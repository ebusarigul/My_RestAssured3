package Campus;
import org.apache.commons.lang3.RandomStringUtils;


public class Methods {

    public static String getRandomName(){
        return RandomStringUtils.randomAlphabetic(8).toLowerCase();
    }

    public static String getRandomCode(){
        return RandomStringUtils.randomNumeric(4).toLowerCase();
    }

    public static String getRandomShortName(){
        return RandomStringUtils.randomAlphabetic(5).toLowerCase();
    }

    public static int getRandomInt(){
        return (int)((Math.random()*100)+100);
    }
}
