package BlogTest.Blog_Test.blog.repository;

import BlogTest.Blog_Test.blog.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
    Optional<Post> findByTitle(String title);
    List<Post> findByMemberId(Long id);
}
