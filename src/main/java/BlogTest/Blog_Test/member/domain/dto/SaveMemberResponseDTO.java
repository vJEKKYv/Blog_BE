package BlogTest.Blog_Test.member.domain.dto;

import BlogTest.Blog_Test.member.domain.Member;

public class SaveMemberResponseDTO {
    private long id;
    private String name;
    private String password;

    public long getId() {return id;}
    public void setId(long id){this.id = id;}
    public void setName(String name) {this.name = name;}
    public String getName() {return name;}
    public void setPassword(String password){this.password = password;}
    public String getPassword() {return password;}
}
