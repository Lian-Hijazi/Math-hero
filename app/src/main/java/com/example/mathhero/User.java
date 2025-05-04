package com.example.mathhero;

public class User {
        private String name;
        private String userName;
        private String phone;
        private String age;
        private int hint,level,score;

        public User(){}

    public User(String name, String userName, String phone, String age){
        this.name = name;
        this.userName = userName;
        this.phone = phone;
        this.age = age;
        this.hint = 0;
        this.level = 1;
        this.score = 5;
    }

    public void setHint(int hint) {
            this.hint = hint;
        }

        public void setLevel(int level) {
            this.level = level;
        }

        public void setScore(int score) {
            this.score = score;
        }

        public int getHint() {
            return hint;
        }

        public int getLevel() {
            return level;
        }

        public int getScore() {
            return score;
        }

        public String getName(){
            return name;
        }

        public String getAge() {
            return age;
        }

        public String getUserName() {
            return userName;
        }

        public String getPhone() {
            return phone;
        }
}
