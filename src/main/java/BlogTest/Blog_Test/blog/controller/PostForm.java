package BlogTest.Blog_Test.blog.controller;

public class PostForm {
    private Long id;
    private Long memberId;
    private String title;
    private String content;
    public Long getId() {return id;}
    public String getTitle() {return title;}
    public String getContent() {return content;}
    public Long getMemberId() {return memberId;}
    public void setTitle(String title) {this.title = title;}
    public void setContent(String content) {this.content = content;}
}
