package edu.upc.dsa.minimo2;

public class User {

    private static User userinstance;
    //Attributes
    private String login;
    private int public_repos;
    private int followers;
    private int following;
    private String avatar_url;

    //Singleton
    public User(){}

    public static synchronized User getInstance(){
        if(userinstance==null) {
            userinstance = new User();
        }
        return userinstance;
    }

    public User(String login, int repos, int followers, int following, String avatar_url) {
        this.login = login;
        this.public_repos = repos;
        this.followers = followers;
        this.following = following;
        this.avatar_url = avatar_url;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String name) {
        this.login = name;
    }

    public int getPublic_repos() {
        return public_repos;
    }

    public void setPublic_repos(int public_repos) {
        this.public_repos = public_repos;
    }

    public int getFollowers() {
        return followers;
    }

    public void setFollowers(int followers) {
        this.followers = followers;
    }

    public int getFollowing() {
        return following;
    }

    public void setFollowing(int following) {
        this.following = following;
    }

    public String getAvatar_url() {
        return avatar_url;
    }

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }
}
