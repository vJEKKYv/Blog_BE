package BlogTest.Blog_Test.member.service;

import BlogTest.Blog_Test.member.domain.Member;
import BlogTest.Blog_Test.member.repository.MemberRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional
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

    boolean validateDuplicateMember(Member member) {
        Member memberCompare = memberRepository.findByName(member.getName());
        if (memberCompare==null)    return true;
        else return false;
    }
    //회원가입
    public Member login(String name, String pasword){
        Member member = findByName(name);
        if (member==null)   return null;
        else if (member.getPassword().equals(pasword))   return member;
        else return null;
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
    public Member findMember(long id) {
        Member member = memberRepository.findById(id).get();
        return member;
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
    //정보 수정
    public boolean changeMemberInfo(Member member) {
        Member MemberInfo = memberRepository.findById(member.getId()).get();

        if (MemberInfo!=null) {
            MemberInfo.setName(member.getName());
            MemberInfo.setPassword(member.getPassword());
            memberRepository.save(MemberInfo);
            return true;
        } else return false;
    }
}