package tech.damko.videoteka.service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;

import tech.damko.videoteka.model.Movie;

public class ImdbService {

    private HttpURLConnection connection = null;
    private final String myKey = "IBVCAVULO2CQTOQEE6VQ";

    public ImdbService(){}

    public Movie getMovieFromImdb(Movie movie){

        try
        {
            URL url = new URL("https://imdb-api.com/en/API/Ratings/"+myKey+"/tt1375666");
            connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoInput(true);

            InputStream stream = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
            StringBuilder responce = new StringBuilder();
            String line=null;
            while((line=reader.readLine())!=null)
            {
                responce.append(line);
                responce.append("\r");
            }
            reader.close();
            String result = responce.toString();
            System.out.print(result);

            JSONObject object = new JSONObject(result);

            String id = object.getString("imDbId");
            String title = object.getString("title");
            String realeseYear = object.getString("year");
            String ratings = object.getString("imDb");

            System.out.println("Movie id "+id);
            System.out.println("Movie title "+title);
            System.out.println("Movie year "+realeseYear);
            System.out.println("Movie Ratings "+ratings);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return movie;
    }

}
