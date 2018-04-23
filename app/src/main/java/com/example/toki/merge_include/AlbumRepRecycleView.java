package com.example.toki.merge_include;

/**
 * Created by toki on 2017/12/30.
 */

public class AlbumRepRecycleView {
        private String profileImage;
        private String name;
        private String time;
        private String text;
        private String postImage;


    AlbumRepRecycleView(String profileImage, String name, String time, String text, String postImage) {
            this.profileImage = profileImage;
            this.name = name;
            this.time=time;
            this.text=text;
            this.postImage=postImage;

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


}
