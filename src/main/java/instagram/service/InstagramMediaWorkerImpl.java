package instagram.service;

import me.postaddict.instagram.scraper.Instagram;
import me.postaddict.instagram.scraper.cookie.CookieHashSet;
import me.postaddict.instagram.scraper.cookie.DefaultCookieJar;
import me.postaddict.instagram.scraper.interceptor.ErrorInterceptor;
import me.postaddict.instagram.scraper.model.Account;
import me.postaddict.instagram.scraper.model.Media;
import me.postaddict.instagram.scraper.model.PageObject;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InstagramMediaWorkerImpl implements InstagramMediaWorker{
    //public final String result = "https://www.instagram.com/p/";
    @Override
    public List<Media> getMedia(String name) throws IOException {
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

        List<Media> resultMedias = (ArrayList<Media>) medias.getNodes();

        if(resultMedias == null || resultMedias.size() == 0) return Collections.emptyList();
        resultMedias = resultMedias
                .stream()
                .filter(media -> media.getTakenAtTimestamp() >= this.getDate())
                .collect(Collectors.toList());
        //не всегда достаются ссылки на video,поэтому приходится обрабатывать Runtime очень плохо
        resultMedias = resultMedias
                .stream()
                .map(media -> {
            try {
                Media media1 = instagram.getMediaByCode(media.getShortcode());
                return media1;
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
            catch (RuntimeException e) {
                System.out.println(e.getMessage());
            }
            return media;
        }).collect(Collectors.toList());
        return resultMedias;
    }

    @Override
    public long getDate() {
        Date date = new Date();
        return date.getTime()- 20 * 24 * 60 * 60 * 1000;
    }
}
