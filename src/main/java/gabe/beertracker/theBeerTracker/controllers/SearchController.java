package gabe.beertracker.theBeerTracker.controllers;

import com.google.gson.Gson;
import gabe.beertracker.theBeerTracker.models.*;
import gabe.beertracker.theBeerTracker.models.data.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
public class SearchController {


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

    @RequestMapping(value = "test5", method = RequestMethod.GET)
    public String searchDisplay(Model model) {
        int userId = 1;
        float maxDistance = 20;
        Location currentLoc = locationDao.findOne(3); // a location of a user
        ArrayList<BeerTag> prefTags = new ArrayList<>();
        prefTags.add(beerTagDao.findOne(1));

        ArrayList<List<Beer>> filteredBeersByOneTag = new ArrayList<>();
        for (BeerTag prefTag : prefTags){
            filteredBeersByOneTag.add(beerDao.getBeersByTag(prefTag.getName()));
        }
        ArrayList<BeerAndOneLocation> filteredBeers = getBeersByTagsAndDistance(filteredBeersByOneTag, currentLoc, maxDistance);
        Location loc = locationDao.findOne(3);
        Collections.sort(filteredBeers, BeerAndOneLocation.BeerDistanceComparator);
        ArrayList<Location> myLocList= BeerAndOneLocation.locationsExtract(filteredBeers);

        final Gson gson = new Gson();
       // System.out.println("Original Java object : " + mylocs);
        String json = gson.toJson(myLocList.toArray());
        System.out.println("json : " + json);
        model.addAttribute("locations", json);

        model.addAttribute("userLocation",gson.toJson(currentLoc));

        model.addAttribute("searchResults", filteredBeers);
        model.addAttribute("title", "search results for " + userDao.findOne(userId).getUserName());
        model.addAttribute("prefTags", prefTags);
        model.addAttribute("allTags", beerTagDao.findAll());
        model.addAttribute("states2", beerTagDao.findAll());
        model.addAttribute("allTag", beerTagDao.findAll());
        return "test/map3";
    }

    @RequestMapping(value = "test5", method = RequestMethod.POST)
    public String searchPost(@RequestParam int tagId, @RequestParam String myPosition, @RequestParam String tags, Model model) {
        int userId = 1;
        float maxDistance = 200;
        final Gson gson = new Gson();
        System.out.println("myPosition=" + myPosition);

        Location currentLoc = new Location();

        if (myPosition.length()>0){
            SimpleLocation userPosJS = new SimpleLocation();
            userPosJS = gson.fromJson(myPosition, SimpleLocation.class);
            System.out.println("userPosJS=" + userPosJS.getLat());

            currentLoc.setLatitude(userPosJS.getLat());
            currentLoc.setLongitude(userPosJS.getLng());
        }
        else {
            currentLoc = locationDao.findOne(3);
        }

                // a location of a user
        ArrayList<BeerTag> prefTags = new ArrayList<>();
        prefTags.add(beerTagDao.findOne(1));
        prefTags.add(beerTagDao.findOne(tagId));


        ArrayList<List<Beer>> filteredBeersByOneTag = new ArrayList<>();
        for (BeerTag prefTag : prefTags){
            filteredBeersByOneTag.add(beerDao.getBeersByTag(prefTag.getName()));
        }

        ArrayList<BeerAndOneLocation> filteredBeers = getBeersByTagsAndDistance(filteredBeersByOneTag, currentLoc, maxDistance);
        Location loc = locationDao.findOne(3);
        Collections.sort(filteredBeers, BeerAndOneLocation.BeerDistanceComparator);
        ArrayList<Location> myLocList= BeerAndOneLocation.locationsExtract(filteredBeers);
        String json = gson.toJson(myLocList.toArray());
        System.out.println("json : " + json);
        System.out.println("tags : " + tags);
        model.addAttribute("locations", json);
        model.addAttribute("userLocation",gson.toJson(currentLoc));
        model.addAttribute("searchResults", filteredBeers);
        model.addAttribute("title", "search results for " + userDao.findOne(userId).getLastName());
        model.addAttribute("prefTags", prefTags);
        model.addAttribute("states2", beerTagDao.findAll());
        model.addAttribute("allTags", beerTagDao.findAll());
        return "test/map3";
    }

    private  ArrayList<BeerAndOneLocation> getBeersByTagsAndDistance( ArrayList<List<Beer>> filteredBeersByOneTag, Location currentLoc, float maxDistance){
        ArrayList<BeerAndOneLocation> filteredBeers = new ArrayList<>();
        for (Beer beer : filteredBeersByOneTag.get(0)){
            boolean rez = true;
            for (int i = 1; i < filteredBeersByOneTag.size(); i++){
                if (!filteredBeersByOneTag.get(i).contains(beer)){
                    rez = false;
                    break;
                }
            }
            if (rez) {
                //    ArrayList<Location> allLocations = new ArrayList<>();
                for (Location location : beer.getLocations()){

                    if (location.getDistanceMi(currentLoc) <= maxDistance){
                        //allLocations.add(currentLoc);
                        BeerAndOneLocation aBeerAndOneLocation = new BeerAndOneLocation(beer, currentLoc, location);
                        // aBeerAndOneLocation.getLocation().getDistanceMi(aBeerAndOneLocation.getComparatorPoint());
                        filteredBeers.add(aBeerAndOneLocation);

                    }
                }

            }
        }
        return filteredBeers;
    }

}