package BlogTest.Blog_Test.member.service;

import BlogTest.Blog_Test.member.domain.Member;
import BlogTest.Blog_Test.member.repository.SpringDataJpaMemberRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class MemberService {

    private final SpringDataJpaMemberRepository springDataJpaMemberRepository;

    public MemberService(SpringDataJpaMemberRepository springDataJpaMemberRepository) {
        this.springDataJpaMemberRepository = springDataJpaMemberRepository;
    }
    /*
     * 회원가입
     */
    public Long join (Member member){
        validateDuplicateMember(member); //중복 회원 검증
        springDataJpaMemberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        springDataJpaMemberRepository.findByName(member.getName())
            .ifPresent(m -> {
                throw  new IllegalStateException("이미 존재하는 회원입니다.");
            });
    }
    /*
     * 전체 회원 조회
     */
    public List<Member> findMembers() {

        return springDataJpaMemberRepository.findAll();
    }
    public Optional<Member> findOne(Long memberId){
        return springDataJpaMemberRepository.findById(memberId);
    }
    /*
     * 단일 회원 조회
     */
    public String findMember(long id) {
        Optional<Member> member = springDataJpaMemberRepository.findById(id);
        return member.get().getName();
    }

    public Optional<Member> findByName(String name) {
        return springDataJpaMemberRepository.findByName(name);
    }

    public void deleteByName(String name) {
        Member member = springDataJpaMemberRepository.findByName(name)
                .orElseThrow(() -> new NoSuchElementException("No member found with name: " + name));
        springDataJpaMemberRepository.delete(member);
    }
    public Member changeName(String name, String newName) {
        Member member = springDataJpaMemberRepository.findByName(name)
                .orElseThrow(() -> new NoSuchElementException("No member found with name: " + name));
        member.setName(newName);
        return springDataJpaMemberRepository.save(member);
    }
}