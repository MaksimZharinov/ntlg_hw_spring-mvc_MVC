package ru.netology.controller;

import org.springframework.web.bind.annotation.*;
import ru.netology.exception.NotFoundException;
import ru.netology.model.Post;
import ru.netology.service.PostService;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {
  private final PostService service;

  public PostController(PostService service) {
    this.service = service;
  }

  @GetMapping
  public List<Post> all() {
    return service.all();
  }

  @GetMapping("/{id}")
  public Post getById(@PathVariable long id) throws NotFoundException {
    try {
      return service.getById(id);
    } catch (NotFoundException e) {
      throw new NotFoundException();
    }
  }

  @PostMapping
  public Post save(@RequestBody Post post) throws NotFoundException {
    try {
      return service.save(post);
    } catch (NotFoundException e) {
      throw new NotFoundException();
    }
  }

  @DeleteMapping("/{id}")
  public void removeById(@PathVariable long id) throws NotFoundException {
    try {
      service.removeById(id);
    } catch (NotFoundException e) {
      throw new NotFoundException();
    }
  }
}
