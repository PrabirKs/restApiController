package com.example.RestApiController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController             //annotation
public class MyController {

    //automatic wired the bean
    //we can see that there is no main method created for MyController class but , how the functions inside it is
    //being called . so at the background the spring is creating a single ton object through which all the methods\
    //are called using api end point


//    @GetMapping("/bean")
//    public int testBean(){
//        obj.setId(56);
//        return obj.getId();
//    }

    //use like database
    Map<Integer,User> users =  new HashMap<>() ;  //store as user id and user object

    @GetMapping("/get_user")    //end point
    public List<User> getUsers(){
        List<User> ListOfUsers = new ArrayList<>() ;
        for(User user : users.values()){
            ListOfUsers.add(user) ;
        }
        return ListOfUsers ; //return an arrayList which contain all the user in the Users data base
    }
    @PostMapping("/add_users")
    public void addUser(@RequestParam("id") int id,
                        @RequestParam("name") String name,
                        @RequestParam("age") int age,
                        @RequestParam("country") String country)
    {
        User user = new User(id,name,country,age) ;
        users.put(id,user) ;
    }
    @PostMapping("/add_user_body")
    //response entity is something which send the return type and with the http status code as per our requirement
    public ResponseEntity addUserBody(@RequestBody(required = true)User user){  //client will give the user object
        try {
            users.put(user.getId(), user);   //getting the id and storing it in hashMap
        }
        catch(Exception e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(HttpStatus.ACCEPTED);
    }
    //path variable
    @GetMapping("/get_user/{id}")
    public User getUser(@PathVariable("id") int id){
        return users.get(id) ;
    }
    @DeleteMapping("/del_user/{id}")
    public ResponseEntity deleteUser(@PathVariable("id") int idx){
        if(!users.containsKey(idx)){
            return new ResponseEntity(HttpStatus.BAD_REQUEST) ;
        }
        users.remove(idx) ;
            return new ResponseEntity((HttpStatus.ACCEPTED)) ;
    }
    @PutMapping("/update/{id}")
    public void updateUser(@PathVariable("id") int id , @RequestBody() User user){
        users.put(id,user) ;
    }
}
