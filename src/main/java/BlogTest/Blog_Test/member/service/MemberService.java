package BlogTest.Blog_Test.member.service;

import BlogTest.Blog_Test.member.domain.Member;
import BlogTest.Blog_Test.member.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }
    /*
     * 회원가입
     */
    public Long join (Member member){
        validateDuplicateMember(member); //중복 회원 검증
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        memberRepository.findByName(member.getName())
            .ifPresent(m -> {
                throw  new IllegalStateException("이미 존재하는 회원입니다.");
            });
    }
    /*
     * 전체 회원 조회
     */
    public List<Member> findMembers() {

        return memberRepository.findAll();
    }
    public Optional<Member> findOne(Long memberId){
        return memberRepository.findById(memberId);
    }
    /*
     * 단일 회원 조회
     */
    public String findMember(long id) {
        Optional<Member> member = memberRepository.findById(id);
        return member.get().getName();
    }

    public Optional<Member> findByName(String name) {
        return memberRepository.findByName(name);
    }

    public void deleteByName(String name) {
        Member member = memberRepository.findByName(name)
                .orElseThrow(() -> new NoSuchElementException("No member found with name: " + name));
        memberRepository.delete(member);
    }
    public Member changeName(String name, String newName) {
        Member member = memberRepository.findByName(name)
                .orElseThrow(() -> new NoSuchElementException("No member found with name: " + name));
        member.setName(newName);
        return memberRepository.save(member);
    }
}