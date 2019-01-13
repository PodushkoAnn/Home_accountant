package sample;

import java.io.*;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

public class Rates {

    private static final String CB_URL = "http://www.cbr.ru/scripts/XML_daily.asp?date_req=";
    private static final String RATES_PATH = "src/sample/resources/rates.xml";

    public static void getRateFromCBRF() {
        try {
            URL url = new URL( CB_URL + getTodayDate());
            System.out.println(url);

            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
            BufferedWriter writer = new BufferedWriter(new FileWriter(RATES_PATH));

            char[] buf = new char[1024];
            int len;
            while((len=reader.read(buf))>0){
                writer.write(buf,0,len);
            }
            reader.close();
            writer.close();
        } catch (IOException | NumberFormatException e) {
            System.out.println(e.getMessage());
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public static String getTodayDate() throws ParseException {
        String s = LocalDate.now().toString();
        SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
        Date date = dt.parse(s);
        SimpleDateFormat dt1 = new SimpleDateFormat("dd/MM/yyyy");
        return dt1.format(date);
    }
}
