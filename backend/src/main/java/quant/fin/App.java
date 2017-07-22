package quant.fin;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static spark.Spark.*;

public class App{

    private static final EcbDao dao = new EcbDao();

    public static void main( String[] args ){
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .create();

        get("/eurusd", (req, res) -> dao.getRates(CurrencyPair.EURUSD), gson::toJson);
    }

    static class LocalDateAdapter implements JsonSerializer<LocalDate> {
        public JsonElement serialize(LocalDate date, Type typeOfSrc, JsonSerializationContext context) {
            return new JsonPrimitive(date.format(DateTimeFormatter.ISO_LOCAL_DATE));
        }
    }
}
