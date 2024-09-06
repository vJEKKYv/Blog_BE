package BlogTest.Blog_Test.blog.repository;

import BlogTest.Blog_Test.blog.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByTitle(String title);
    List<Post> findByMemberId(Long id);

}
