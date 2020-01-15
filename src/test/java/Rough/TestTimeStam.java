package Rough;

import java.util.Date;

public class TestTimeStam {

    public static void main(String[] args) {

        Date d = new Date();
        String shreenshotName = d.toString().replace(":","_").replace(" ","_")+".jpg";

        System.out.println(shreenshotName);

    }

}
