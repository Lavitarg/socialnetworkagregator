package instagram.service;

import me.postaddict.instagram.scraper.Instagram;
import me.postaddict.instagram.scraper.cookie.CookieHashSet;
import me.postaddict.instagram.scraper.cookie.DefaultCookieJar;
import me.postaddict.instagram.scraper.interceptor.ErrorInterceptor;
import me.postaddict.instagram.scraper.model.Account;
import me.postaddict.instagram.scraper.model.Comment;
import me.postaddict.instagram.scraper.model.Media;
import me.postaddict.instagram.scraper.model.PageObject;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.Collectors;

import static instagram.controller.GreetingController.currentTime;

public class TestInstagram {
    public static void main(String[] args) throws IOException {

    }
    public static ArrayList<Media> getPath(String name) throws IOException {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addNetworkInterceptor(loggingInterceptor)
                .addInterceptor(new ErrorInterceptor())
                .cookieJar(new DefaultCookieJar(new CookieHashSet()))
                .build();
        Instagram instagram = new Instagram(httpClient);

        Account account = instagram.getAccountByUsername(name);
        PageObject<Media> medias = account.getMedia();
        ArrayList<Media> path = (ArrayList<Media>) medias.getNodes();
        if(path == null || path.size() == 0) return new ArrayList<Media>();
        path = path.stream().filter(media -> media.getTakenAtTimestamp() >= currentTime).collect(Collectors.toCollection(ArrayList::new));
        path = path.stream().map(media -> {
            try {
                String result = "https://www.instagram.com/p/" + media.getShortcode();
                Media media1 = instagram.getMediaByCode(media.getShortcode());
                return media1;
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
            catch (RuntimeException e) {
                System.out.println(e.getMessage());
            }
            return media;
        }).collect(Collectors.toCollection(ArrayList::new));
        return path;

    }
}
