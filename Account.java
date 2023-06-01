package socialmedia;

import java.io.Serializable;
import java.util.ArrayList;

public class Account implements Serializable {
    //sets the attributes of the class:
    private String handle;
    private String description ="";
    private int id;
    private ArrayList<Post> posts = new ArrayList<Post>();
    private static int counter = 0;


    public Account (String u_handle){ //constructor without description parameter
        this.handle = u_handle;
        this.id = counter;
        ++counter;
    }
    public Account (String u_handle, String description){ //constructor with description
        this.handle = u_handle;
        this.id = counter;
        ++counter;
        this.description = description;
    }

    public int get_counter(){
        return counter;
    } //returns the account id counter

    public void set_counter(int u_counter){
        counter = u_counter;
    } //sets the id counter

    public int get_id(){
        return this.id;
    } //returns the account id

    public static void resetidcounter(){
        counter = 0;
    }//resets the idcounter

    public String get_handle(){
        return this.handle;
    }//returns the account handle

    public void set_handle(String new_handle){
        this.handle = new_handle;
    } //sets the handle of the account

    public void set_description(String new_description){
        this.description = new_description;
    } //sets the description of the account

    public String getDescription(){ //ADD THIS
        return this.description;
    }//returns the account description

    public int get_number_of_posts(){
        return this.posts.size();
    } //returns the total number of posts made by the account

    public int get_number_of_endorsements() { //returns the number of endorsement posts made by the account
        int counter = 0;
        for (Post i : this.posts){
                counter+=i.getEndorsementcount();
        }
        return counter;
    }


    public ArrayList<Post> get_posts() {
        return this.posts;
    } //returns the posts made by the account

    public void add_post(Post endorsedPost) { //adds a post to the accounts list of posts
        this.posts.add(endorsedPost);
        endorsedPost.setHandle(this.handle);
    }
}
