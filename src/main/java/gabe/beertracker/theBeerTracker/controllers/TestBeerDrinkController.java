package gabe.beertracker.theBeerTracker.controllers;

import gabe.beertracker.theBeerTracker.models.Beer;
import gabe.beertracker.theBeerTracker.models.BeerDrink;
import gabe.beertracker.theBeerTracker.models.User;
import gabe.beertracker.theBeerTracker.models.data.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


@Controller
@RequestMapping(value = "")
public class TestBeerDrinkController {

    @Autowired
    private BeerDao beerDao;
    @Autowired
    private BeerDrinkDao beerDrinkDao;
    @Autowired
    private BeerFeedbackDao beerFeedbackDao;
    @Autowired
    private BeerTagDao beerTagDao;
    @Autowired
    private LocationDao locationDao;
    @Autowired
    private LocationFeedbackDao locationFeedbackDao;
    @Autowired
    private UserDao userDao;


    @RequestMapping(value = "test3")

    public String test
        //       ()
    (Model model) {

/*        BeerTag newBeerTag1 = new BeerTag("tag3 light2");
        beerTagDao.save(newBeerTag1);
        BeerTag newBeerTag2 = new BeerTag("tag4 dark2");
        beerTagDao.save(newBeerTag2);
        Location newLocation = new Location("location2", -25.363, 131.044);
        locationDao.save(newLocation);
        List<BeerTag> listBeerTags = new ArrayList<>();
        listBeerTags.add(newBeerTag1);
        Beer newBeer = new Beer("beer3",listBeerTags,newLocation);
        beerDao.save(newBeer);
        listBeerTags.add(newBeerTag2);
        Beer newBeer2 = new Beer("beer4",listBeerTags,newLocation);
        beerDao.save(newBeer2);
        User newUser1 = new User("hash3", "username3");
        User newUser2 = new User("hash4", "username4");
        userDao.save(newUser1);
        userDao.save(newUser2);
        BeerDrink newBeerDrink = new BeerDrink(LocalDateTime.now(), newBeer, newUser2, newLocation);
        beerDrinkDao.save(newBeerDrink);*/
        // model.addAttribute("beers", beerDao.findAll());

        model.addAttribute("beers", beerDao.findAll());
        // model.addAttribute("divider", " tags: ");
        User newUser1 = new User("hash3", "username3");
        User newUser2 = new User("hash4", "username4");
        userDao.save(newUser1);
        userDao.save(newUser2);
        model.addAttribute("tags", beerTagDao.findAll());
        BeerDrink newBeerDrink = new BeerDrink(LocalDateTime.now(), beerDao.findOne(3), userDao.findOne(1), locationDao.findOne(3));
        beerDrinkDao.save(newBeerDrink);
        BeerDrink newBeerDrink2 = new BeerDrink(LocalDateTime.now(), beerDao.findOne(3), userDao.findOne(1), locationDao.findOne(3));
        beerDrinkDao.save(newBeerDrink2);
        BeerDrink newBeerDrink3 = new BeerDrink(LocalDateTime.now(), beerDao.findOne(4), userDao.findOne(1), locationDao.findOne(4));
        beerDrinkDao.save(newBeerDrink3);
        BeerDrink newBeerDrink4 = new BeerDrink(LocalDateTime.now(), beerDao.findOne(5), userDao.findOne(1), locationDao.findOne(5));
        beerDrinkDao.save(newBeerDrink4);
        BeerDrink newBeerDrink5= new BeerDrink(LocalDateTime.now(), beerDao.findOne(6), userDao.findOne(1), locationDao.findOne(3));
        beerDrinkDao.save(newBeerDrink5);

  //      return "test/index3";
        return "login";

    }


    @RequestMapping(value = "test4")
    public String test2  (Model model) {
        int userId = 1; //You should have userID before. I use it only for test
        User currentUser = userDao.findOne(userId); //Get user object
        List<BeerDrink> listofDrinks = currentUser.getBeerDrinks(); //Get a list of drinks for given user
        ArrayList<Beer> drunkBeers = new ArrayList<>(); // Here we will have a list of drunk beers without duplicates
        for (BeerDrink drink : listofDrinks){
            if (!drunkBeers.contains(drink.getBeer())){
                drunkBeers.add(drink.getBeer());
            }

            model.addAttribute("drunkbeers", drunkBeers); // here we already should have a list of drunk beers
        }

        return "test/index3";

    }

    @RequestMapping(value = "test6")
    public String test6  (Model model) {
        int userId = 1; //You should have userID before. I use it only for test
        User currentUser = userDao.findOne(userId); //Get user object
        List<Beer>  notTriedBeers = beerDao.getBeersNotTriedByUserName(currentUser.getUserName());
        List<Beer>  notTriedBeers2 = beerDao.getBeersNotTriedByUserId(currentUser.getId());

            model.addAttribute("drunkbeers", notTriedBeers); // here we already should have a list of drunk beers
            model.addAttribute("drunkbeers2", notTriedBeers2); // here we already should have a list of drunk beers


        return "test/index3";
    }

}

