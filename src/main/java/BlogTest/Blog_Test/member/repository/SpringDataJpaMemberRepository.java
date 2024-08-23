package BlogTest.Blog_Test.member.repository;

import BlogTest.Blog_Test.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpringDataJpaMemberRepository extends JpaRepository<Member, Long>{
    Optional<Member> findByName(String name);
}