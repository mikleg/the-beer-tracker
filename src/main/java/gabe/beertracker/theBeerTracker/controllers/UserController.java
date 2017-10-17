package gabe.beertracker.theBeerTracker.Controllers;

import gabe.beertracker.theBeerTracker.models.User;
import gabe.beertracker.theBeerTracker.models.data.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;


@Controller
@SessionAttributes("userName")
public class UserController {

    @Autowired
    private UserDao userDao;

    @RequestMapping(value = "")
    public String index() {

        return "index";
    }

    @RequestMapping(value = "register", method = RequestMethod.GET)
    public String dsiplayRegister(Model model) {
        model.addAttribute("title", "Register");
        model.addAttribute(new User());

        //model.addAttribute("categories", categoryDao.findAll());
        return "register";
    }

    @RequestMapping(value = "register", method = RequestMethod.POST)
    public String processRegister(@ModelAttribute @Valid User newUser, @RequestParam String userName,
                                  Errors errors, Model model) {

        Iterable<User> allUsers = userDao.findAll();
        boolean userNameExists = false;
        boolean passwordExists = false;
        for (User user : allUsers) {
            if (newUser.getUserName().equals(user.getUserName())) {
                if (newUser.getHash().equals(user.getHash())) {
                    userNameExists = true;
                    passwordExists = true;
                }

                else {
                    userNameExists = true;
                    passwordExists = false;
                }
            }
        }
        if (errors.hasErrors() ) {
            return "register";
        }

        else{
            if (!userNameExists) {
                if (!passwordExists) {
                    model.addAttribute("userName", userName);
                    //model.addAttribute("registerSuccess", "You have successfully registered, please login");
                    userDao.save(newUser);
                    return "redirect:/userhome/" +newUser.getId();
                }
                model.addAttribute("userName", userName);
                //model.addAttribute("registerSuccess", "You have successfully registered, please login");
                userDao.save(newUser);
                return "redirect:/userhome/" +newUser.getId();
            }

            model.addAttribute("existingUsername", "Great minds think alike, username already exists");
            return "register";
        }
    }


    @RequestMapping(value = "userhome/{userId}", method = RequestMethod.GET)
    public String displayHome(@PathVariable("userId") /*String use/rUrl,*/ int userId, Model model){
        User user = userDao.findOne(userId);
        //String userUrl = user.getUserName();
        //model.addAttribute("userUrl", userUrl);
        model.addAttribute("user", user);
        model.addAttribute("welcome", "Welcome, " + user.getUserName());
        //model.addAttribute("foundBeers", user.getBeerDrinks());

        return "userhome";
    }


}
