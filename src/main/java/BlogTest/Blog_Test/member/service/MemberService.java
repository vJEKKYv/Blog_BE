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
        boolean check = validateDuplicateMember(member); //중복 회원 검증
        if (check){
            memberRepository.save(member);
            return member.getId();
        }
        else {
            return (long)-1;
        }
    }

    private boolean validateDuplicateMember(Member member) {
        Member memberCompare = memberRepository.findByName(member.getName());
        if (member.getId()==memberCompare.getId())
            return false;
        else return true;
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

    public Member findByName(String name) {
        return memberRepository.findByName(name);
    }

    public void deleteByName(String name) {
        Member member = memberRepository.findByName(name);
        if (member == null){
            throw new NoSuchElementException("No member found with name: " + name);
        }
        memberRepository.delete(member);
    }
    public Member changeName(String name, String newName) {
        Member member = memberRepository.findByName(name);
        if (member == null){
            throw new NoSuchElementException("No member found with name: " + name);
        }
        member.setName(newName);
        return memberRepository.save(member);
    }
}