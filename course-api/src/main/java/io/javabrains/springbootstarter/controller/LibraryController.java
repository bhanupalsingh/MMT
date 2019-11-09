package io.javabrains.springbootstarter.controller;

        import io.javabrains.springbootstarter.bean.Book;
        import io.javabrains.springbootstarter.bean.User;
        import io.javabrains.springbootstarter.bean.UserBook;
        import io.javabrains.springbootstarter.helper.ResponseData;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.http.HttpStatus;
        import org.springframework.http.ResponseEntity;

        import java.util.ArrayList;
        import java.util.List;

        import org.springframework.web.bind.annotation.PathVariable;
        import org.springframework.web.bind.annotation.RequestMapping;
        import org.springframework.web.bind.annotation.RequestMethod;
        import org.springframework.web.bind.annotation.ResponseBody;
        import org.springframework.web.bind.annotation.RestController;


@RestController
public class LibraryController {

    private List<UserBook> bookingDetail = new ArrayList<UserBook>();


    @Autowired
    private BookController bookController;

    @Autowired
    private UserController userController;


    public LibraryController(){

    }


    @RequestMapping(value="/details",method= RequestMethod.GET)
    public List<UserBook> getAllReserve(){
        return bookingDetail;
    }



    @RequestMapping(value="/returnBook/userId/{userId}/bookId/{bookId}",method= RequestMethod.PUT)
    public @ResponseBody  ResponseEntity<?> returnBook(@PathVariable int userId ,@PathVariable int bookId){
        try {
            for (UserBook c : bookingDetail) {
                if (c.getUserId() == userId) {
                    c.getBookIssued().remove(bookId);
                    ResponseEntity<?> book = bookController.getBookById(bookId);
                    if (!book.getStatusCode().isError()) {
                        Book bookObject = (Book) book.getBody();
                        bookObject.setStockCount(bookObject.getStockCount() + 1);
                        return new ResponseEntity<ResponseData<String>>(new ResponseData("success", "book Return successfully"), HttpStatus.OK);
                    }

                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<ResponseData<String>>(new ResponseData("error","book Return  failed"),HttpStatus.BAD_REQUEST);
    }





    @RequestMapping(value="/unreserve/userId/{userId}/bookId/{bookId}",method= RequestMethod.PUT)
    public @ResponseBody  ResponseEntity<?> unreserveBook(@PathVariable int userId ,@PathVariable int bookId){
        try {
            for (UserBook c : bookingDetail) {
                if (c.getUserId() == userId) {
                    c.getBookIssued().remove(bookId);
                    ResponseEntity<?> book = bookController.getBookById(bookId);
                    if (!book.getStatusCode().isError()) {
                        Book bookObject = (Book) book.getBody();
                        //bookObject.setStockCount(bookObject.getStockCount() + 1);
                        return new ResponseEntity<ResponseData<String>>(new ResponseData("success", "book Return successfully"), HttpStatus.OK);
                    }

                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<ResponseData<String>>(new ResponseData("error","book Return  failed"),HttpStatus.BAD_REQUEST);
    }





    @RequestMapping (value="/issue/user/{userId}/book/{bookId}",method=RequestMethod.PUT)
    public @ResponseBody  ResponseEntity<?>  addIssueBook(@PathVariable int userId ,@PathVariable int bookId){
        ResponseEntity<?> book = bookController.getBookById(bookId);
        System.out.println(book.getBody());
        if(!book.getStatusCode().isError()){
            Book  bookObject = (Book) book.getBody();
            if(bookObject.getStockCount() > 0 ){



                //if user already reserve for that book

                int reservedCount = 0;
                for(UserBook c: bookingDetail) {
                    if (c.getUserId() == userId && c.getBookReserved().contains(bookObject.getId())){
                        break;
                    }else{
                        reservedCount += c.getBookReserved().size();
                    }
                }

                // if stock is less then reserved. then we not allow for new user
                if(reservedCount>=bookObject.getStockCount()){
                    return new ResponseEntity<ResponseData<String>>(new ResponseData("success","book Allready reserved for other user.please create reserve request . whenever available er will notify you"),HttpStatus.OK);
                }

                try{
                    boolean dataAlreayThere = false;
                    for(UserBook c: bookingDetail) {
                        if (c.getUserId() == userId) {
                            dataAlreayThere = true;
                            if(c.getBookIssued().contains(bookObject.getId())){
                                return new ResponseEntity<ResponseData<String>>(new ResponseData("success","book Allready issused.you are only allowed to 1 copy of each book"),HttpStatus.OK);
                            }
                            c.getBookIssued().add(bookObject.getId());
                            //remove from reserved , if there
                            c.getBookReserved().remove(bookObject.getId());
                            bookObject.setStockCount(bookObject.getStockCount()-1);
                        }
                    }

                    if(!dataAlreayThere){
                        UserBook userBook = new UserBook();
                        userBook.setUserId(userId);
                        ArrayList<Integer> issueBook = new ArrayList<>();
                        issueBook.add(bookId);
                        userBook.setBookIssued(issueBook);
                        userBook.setBookReserved(new ArrayList<Integer>());
                        bookObject.setStockCount(bookObject.getStockCount()-1);
                        bookingDetail.add(userBook);
                    }

                    return new ResponseEntity<ResponseData<String>>(new ResponseData("success","book issused successfully"),HttpStatus.OK);


                }catch(Exception e){
                    System.out.println("error:-"+e.getMessage());
                }
            }else{
                return new ResponseEntity<ResponseData<String>>(new ResponseData("error","book is not available now"),HttpStatus.NOT_FOUND);
            }
        }
        return new ResponseEntity<ResponseData<String>>(new ResponseData("error","book issued failed"),HttpStatus.BAD_REQUEST);
    }





    //I am not fully sure reserve facility. here I am assumming that when books are not available user can reserve book
    //whenever book avail they can issue those book.
    @RequestMapping (value="/reserve/user/{userId}/book/{bookId}",method=RequestMethod.PUT)
    public @ResponseBody  ResponseEntity<?>  addReservation(@PathVariable int userId ,@PathVariable int bookId){
        ResponseEntity<?> book = bookController.getBookById(bookId);
        System.out.println(book.getBody());
        if(!book.getStatusCode().isError()){
            Book  bookObject = (Book) book.getBody();
                try{
                    boolean dataAlreayThere = false;
                    for(UserBook c: bookingDetail) {
                        if (c.getUserId() == userId) {
                            dataAlreayThere = true;
                            if(c.getBookReserved().contains(bookObject.getId())){
                                return new ResponseEntity<ResponseData<String>>(new ResponseData("success","book Allready in reserve state.you are only allowed to 1 copy of each book"),HttpStatus.OK);
                            }
                            c.getBookReserved().add(bookObject.getId());
                            //bookObject.setStockCount(bookObject.getStockCount()-1);
                        }
                    }

                    if(!dataAlreayThere){
                        UserBook userBook = new UserBook();
                        userBook.setUserId(userId);
                        ArrayList<Integer> reserveBook = new ArrayList<>();
                        reserveBook.add(bookId);
                        userBook.setBookIssued(new ArrayList<Integer>());
                        userBook.setBookReserved(reserveBook);
                        //bookObject.setStockCount(bookObject.getStockCount()-1);
                        bookingDetail.add(userBook);
                    }

                    return new ResponseEntity<ResponseData<String>>(new ResponseData("success","book reserved successfully"),HttpStatus.OK);


                }catch(Exception e){
                    System.out.println("error:-"+e.getMessage());
                }

        }
        return new ResponseEntity<ResponseData<String>>(new ResponseData("error","book reservation failed"),HttpStatus.BAD_REQUEST);
    }



}
