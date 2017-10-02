package com.tbt.thebeertracker.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class User {
    @Id
    @GeneratedValue
    private int id;

   // @NotNull
    @Size(min=3, max=25)
    private String firstName;

  //  @NotNull
    @Size(min=3, max=25)
    private String lastName;

    @NotNull
    @Size(min=3, max=25)
    private String hash; //we will implement hash later. Use like  a string password now.

    @NotNull
    @Size(min=3, max=25)
    private String userName;

    private LocalDateTime dateOfBirth;

    @NotNull
    private LocalDateTime dateOfCreation;

    @OneToMany
    @JoinColumn(name ="user_id")
    private List<BeerFeedback> beerFeedbacks = new ArrayList<>();

    @OneToMany
    @JoinColumn(name ="user_id")
    private List<LocationFeedback> locationFeedbacks = new ArrayList<>();

    @OneToMany
    @JoinColumn(name ="user_id")
    private List<BeerDrink> beerDrinks = new ArrayList<>();

    @ManyToMany
    private List<Beer> favoriteBeers;

    public User() {
        dateOfCreation = LocalDateTime.now();
        List<BeerFeedback> beerFeedbacks = new ArrayList<>();
        List<LocationFeedback> locationFeedbacks = new ArrayList<>();
        List<BeerDrink> beerDrinks = new ArrayList<>();
        List<Beer> beers = new ArrayList<>();
        this.beerFeedbacks = beerFeedbacks;
        this.locationFeedbacks = locationFeedbacks;
        this.beerDrinks = beerDrinks;
     //   this.favoriteBeers = beers;
       // this.dateOfBirth = LocalDateTime.now();
    }

    public User(String hash, String userName) {
        this();
        this.hash = hash;
        this.userName = userName;

    }
}
