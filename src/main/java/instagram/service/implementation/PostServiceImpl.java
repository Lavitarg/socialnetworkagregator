package instagram.service.implementation;

import instagram.entity.InstagramProfile;
import instagram.entity.Subscriber;
import instagram.model.InstagramPost;
import instagram.repository.InstagramProfileRepo;
import instagram.repository.SubscriprionsRepo;
import instagram.repository.UserRepo;
import instagram.service.InstagramMediaWorker;
import instagram.service.PostService;
import lombok.RequiredArgsConstructor;
import me.postaddict.instagram.scraper.model.Media;
import org.springframework.jmx.export.naming.IdentityNamingStrategy;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor

public class PostServiceImpl implements PostService {
    private final InstagramProfileRepo profileRepo;
    private final SubscriprionsRepo subscribersRepo;
    private final InstagramMediaWorker instagramMediaWorker;

    @Override
    public InstagramProfile getInstagramProfile(Long id) {
        return profileRepo.findByUserId(id);
    }

    @Override
    public List<Media> getMediaByUserId(Long id) throws IOException {
        InstagramProfile profile = this.getInstagramProfile(id);
        List<Subscriber> subscribeItems = subscribersRepo.findAllByProfileId(profile.getId());
        List<Media> result = new ArrayList<>();
        for (Subscriber nick : subscribeItems) {
            List<Media> mediaList = instagramMediaWorker.getMedia(nick.getName());
            result.addAll(mediaList);
        }
        return result;
    }

    @Override
    public List<InstagramPost> getPostByUserId(Long id) throws IOException {
        List<Media> result = this.getMediaByUserId(id);
        return result.stream().map(media -> {
            InstagramPost post = new InstagramPost(media);
            return post;
        }).collect(Collectors.toList());
    }
}
