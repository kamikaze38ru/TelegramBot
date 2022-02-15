import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;

public class Weather {

    //8cda7657f5220b2fdea04ff2f4a56a83
    public static String getWeather(Model model, String city) throws IOException {
        URL url = new URL("https://api.openweathermap.org/data/2.5/weather?q="+ city + "&units=metric&lang=ru&appid=8cda7657f5220b2fdea04ff2f4a56a83");

        Scanner scanner = new Scanner((InputStream) url.getContent());
        String result = "";
        while (scanner.hasNext()) {
            result += scanner.nextLine();
        }

        JSONObject object = new JSONObject(result);
        model.setName(object.getString("name"));

        JSONObject main = object.getJSONObject("main");
        model.setTemp(main.getDouble("temp"));
        model.setHumidity(main.getDouble("humidity"));

        JSONArray jsonArr = object.getJSONArray("weather");
        for ( int i = 0; i < jsonArr.length(); i ++) {
            JSONObject obj = jsonArr.getJSONObject(i);
            model.setIcon((String) obj.get("icon"));
            model.setMain((String) obj.get("description"));
        }

        return "Город: " + model.getName() + "\n" +
                "Температура: " + model.getTemp() + "C" + " \n" +
                "Влажность: " + model.getHumidity() +"%" + "\n" +
                "На улице: " + model.getMain() + "\n" +
                "https://openweathermap.org/img/wn/" + model.getIcon() + ".png";
    }

}
