package BlogTest.Blog_Test.member.repository;

import BlogTest.Blog_Test.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface MemberRepository extends JpaRepository<Member, Long>{
    Member findByName(String name);
}