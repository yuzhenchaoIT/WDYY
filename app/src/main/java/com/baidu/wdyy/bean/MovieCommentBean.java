package com.baidu.wdyy.bean;

public class MovieCommentBean {

    /**
     * commentContent : 挺好看的
     * commentHeadPic : http://172.17.8.100/images/movie/head_pic/2019-01-11/20190111135020.png
     * commentId : 1810
     * commentTime : 1547196025000
     * commentUserId : 4007
     * commentUserName : 北野
     * greatNum : 0
     * hotComment : 0
     * isGreat : 0
     * replyNum : 0
     */

    private String commentContent;
    private String commentHeadPic;
    private int commentId;
    private long commentTime;
    private int commentUserId;
    private String commentUserName;
    private int greatNum;
    private int hotComment;
    private int isGreat;
    private int replyNum;

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }

    public String getCommentHeadPic() {
        return commentHeadPic;
    }

    public void setCommentHeadPic(String commentHeadPic) {
        this.commentHeadPic = commentHeadPic;
    }

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public long getCommentTime() {
        return commentTime;
    }

    public void setCommentTime(long commentTime) {
        this.commentTime = commentTime;
    }

    public int getCommentUserId() {
        return commentUserId;
    }

    public void setCommentUserId(int commentUserId) {
        this.commentUserId = commentUserId;
    }

    public String getCommentUserName() {
        return commentUserName;
    }

    public void setCommentUserName(String commentUserName) {
        this.commentUserName = commentUserName;
    }

    public int getGreatNum() {
        return greatNum;
    }

    public void setGreatNum(int greatNum) {
        this.greatNum = greatNum;
    }

    public int getHotComment() {
        return hotComment;
    }

    public void setHotComment(int hotComment) {
        this.hotComment = hotComment;
    }

    public int getIsGreat() {
        return isGreat;
    }

    public void setIsGreat(int isGreat) {
        this.isGreat = isGreat;
    }

    public int getReplyNum() {
        return replyNum;
    }

    public void setReplyNum(int replyNum) {
        this.replyNum = replyNum;
    }
}
