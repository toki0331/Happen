package com.example.toki.merge_include;

/**
 * Created by toki on 2017/12/16.
 */

public class AlbumRecycleView {

    private String profileImage;
    private String name;
    private String time;
    private String text;
    private String postImage;
    private String timekey;

    private String rep1;

    private String rep2;


    AlbumRecycleView(String profileImage, String name,String time,String text,String postImage,String timekey,String rep1,String rep2) {
        this.profileImage = profileImage;
        this.name = name;
        this.time=time;
        this.text=text;
        this.postImage=postImage;
        this.timekey=timekey;
        this.rep1=rep1;
        this.rep2=rep2;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {this.name = name;}
    public String getTime() {return time;}
    public void setTime(String time) {this.time=time;}
    public String getText() {
        return text;
    }
    public void setText(String text) {this.text=text;}
    public String getPostImage() {
        return postImage;
    }

    public String getTimekey() {
        return timekey;
    }
    public void setTimekey(String timekey) {
        this.timekey=timekey;}
    public String getrep1() {
        return rep1;
    }
    public String getrep2() {
        return rep2;
    }

}
