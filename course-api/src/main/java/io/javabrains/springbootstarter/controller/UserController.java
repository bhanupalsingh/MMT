package io.javabrains.springbootstarter.controller;

import io.javabrains.springbootstarter.bean.User;
import io.javabrains.springbootstarter.helper.ResponseData;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class UserController {

    private List<User> listOfUsers = new ArrayList();

    public UserController(){

    }


    @RequestMapping(value="/user",method= RequestMethod.GET)
    public List<User> getAllUsers(){

        return listOfUsers;
    }

    @RequestMapping(value="/user/{id}",method=RequestMethod.GET)
    public @ResponseBody ResponseEntity<?> getUserInfo(@PathVariable int id){
        try{
            for(User c: listOfUsers) {
                if (c.getId() == id) {
                    return new ResponseEntity<User>(c, HttpStatus.OK);
                }
            }
        }catch(Exception e){
            System.out.println("error:-"+e.getMessage());
        }
        return new ResponseEntity<ResponseData<String>>(new ResponseData("failed","user not found."),HttpStatus.NOT_FOUND);

    }

    @RequestMapping(value="/user/{id}",method=RequestMethod.PUT)
    public @ResponseBody  ResponseEntity<?> setUserInfo(@PathVariable int id, @RequestBody User user){
        for(User c: listOfUsers){
            if(c.getId() == id){
                c.setId(user.getId());
                c.setUserame(user.getUsername());
                c.setPassword(user.getPassword());
                return new ResponseEntity<ResponseData<String>>(new ResponseData("success","user updated successfully"),HttpStatus.OK);
            }
        }
        return new ResponseEntity<ResponseData<String>>(new ResponseData("failed","user not found."),HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value="/user",method=RequestMethod.POST)
    public @ResponseBody  ResponseEntity<?>  addUserInfo(@RequestBody User user){
        int uniqueId = listOfUsers.size()+1;
        user.setId(uniqueId);
        listOfUsers.add(user);
        return new ResponseEntity<ResponseData<String>>(new ResponseData("success","user added successfully"),HttpStatus.OK);
    }
}
