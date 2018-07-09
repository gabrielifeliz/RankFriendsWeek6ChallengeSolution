package com.example.demo.model;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Set;

@Entity
public class Friend {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotEmpty
    @Size(min = 1, max = 255)
    private String firstName;

    @NotEmpty
    @Size(min = 1, max = 255)
    private String lastName;

/*    @NotEmpty
    @Size(min = 1, max = 255)
    private String nickname;*/

    private String image;

    @NotNull
    @Min(1)
    @Max(10)
    private int rating = 1;

    @ManyToOne
    private AppUser myFriend;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

/*    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }*/

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public AppUser getMyFriend() {
        return myFriend;
    }

    public void setMyFriend(AppUser myFriend) {
        this.myFriend = myFriend;
    }
}
