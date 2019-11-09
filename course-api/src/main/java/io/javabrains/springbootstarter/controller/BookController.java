package io.javabrains.springbootstarter.controller;


import io.javabrains.springbootstarter.bean.Book;
import io.javabrains.springbootstarter.bean.User;
import io.javabrains.springbootstarter.helper.ResponseData;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class BookController {


    private List<Book> listOfBooks = new ArrayList();
    public void BookController(){

    }


    @RequestMapping(value="/book",method= RequestMethod.GET)
    public List<Book> getAllBooks(){
        return listOfBooks;
    }


    @RequestMapping(value="/book/{id}",method= RequestMethod.GET)
    public @ResponseBody
    ResponseEntity<?> getBookById(@PathVariable int id){
        try{
            for(Book c: listOfBooks) {
                if (c.getId() == id) {
                    return new ResponseEntity<Book>(c, HttpStatus.OK);
                }
            }
        }catch(Exception e){
            System.out.println("error:-"+e.getMessage());
        }
        return new ResponseEntity<ResponseData<String>>(new ResponseData("failed","book not found."),HttpStatus.NOT_FOUND);

    }






    @RequestMapping(value="/book/{id}",method=RequestMethod.PUT)
    public @ResponseBody  ResponseEntity<?> setUserInfo(@PathVariable int id, @RequestBody Book book){
        for(Book c: listOfBooks){
            if(c.getId() == id){
                c.setId(book.getId());
                c.setName(book.getName());
                c.setCategory(book.getCategory());
                c.setStockCount(book.getStockCount());
                return new ResponseEntity<ResponseData<String>>(new ResponseData("success","book data updated successfully"),HttpStatus.OK);
            }
        }
        return new ResponseEntity<ResponseData<String>>(new ResponseData("failed","book not found."),HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value="/book",method=RequestMethod.POST)
    public @ResponseBody  ResponseEntity<?>  addUserInfo(@RequestBody Book book){
        int uniqueId = listOfBooks.size()+1;
        book.setId(uniqueId);
        listOfBooks.add(book);
        return new ResponseEntity<ResponseData<String>>(new ResponseData("success","book added successfully"),HttpStatus.OK);
    }






}
