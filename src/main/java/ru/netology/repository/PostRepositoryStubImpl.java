package ru.netology.repository;

import org.springframework.stereotype.Repository;
import ru.netology.exception.NotFoundException;
import ru.netology.model.Post;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class PostRepositoryStubImpl implements PostRepository {

    private final ConcurrentHashMap<Long, Post> REPO = new ConcurrentHashMap<>();
    private long countPosts = 0L;

    public List<Post> all() {
        if (REPO.isEmpty()) {
            return Collections.emptyList();
        }
        var repoWithoutDelete = new LinkedList<Post>();
        for (Post post : REPO.values()) {
            if (!post.isDelete()) {
                repoWithoutDelete.add(post);
            }
        }
        return repoWithoutDelete;
    }

    public Optional<Post> getById(long id) {
        if (REPO.isEmpty()) {
            return Optional.empty();
        }
        if (REPO.get(id).isDelete()) {
            throwEx();
        }
        return Optional.ofNullable(REPO.get(id));
    }

    public Post save(Post post) {
        if (post.getId() != 0) {
            if (!REPO.containsKey(post.getId()) ||
                    !REPO.get(post.getId()).isDelete()) {
                throwEx();
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
            throwEx();
        }
        REPO.get(id).setDelete(true);
        countPosts--;
    }

    private void throwEx() {
        throw new NotFoundException("Post with such ID does not exist");
    }
}
