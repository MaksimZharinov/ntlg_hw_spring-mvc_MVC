package ru.netology.repository;

import org.springframework.stereotype.Repository;
import ru.netology.exception.NotFoundException;
import ru.netology.model.Post;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class PostRepositoryStubImpl implements PostRepository {

    private final ConcurrentHashMap<Long, Post> REPO = new ConcurrentHashMap<>();
    private long countPosts = 0L;

    public List<Post> all() {
        if (REPO.isEmpty()) {
            return Collections.emptyList();
        }
        return (List<Post>) REPO.values();
    }

    public Optional<Post> getById(long id) {
        if (REPO.isEmpty()) {
            return Optional.empty();
        }
        return Optional.ofNullable(REPO.get(id));
    }

    public Post save(Post post) {
        if (post.getId() != 0) {
            if (!REPO.containsKey(post.getId())) {
                throw new NotFoundException("Post with such ID does not exist");
            }
            return REPO.replace(post.getId(), post);
        }
        post.setId(countPosts + 1);
        REPO.put(post.getId(), post);
        countPosts++;
        return post;
    }

    public void removeById(long id) {
        if (!REPO.containsKey(id)) {
            throw new NotFoundException("Post with such ID does not exist");
        }
        REPO.remove(id);
        countPosts--;
    }
}
