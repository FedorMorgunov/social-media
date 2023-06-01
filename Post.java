package socialmedia;
import java.io.Serializable;
import  java.util.ArrayList;

public class Post implements Serializable {
    //setting class attributes
    private static int idCounter = -1;
    private int id;
    private boolean endorsementPost = false;
    private ArrayList<Post> children = new ArrayList<Post>();
    private String message;
    private boolean isComment = false;
    private Post reference = null;
    private boolean isnonacessible = false;


    private int endorsementcount = 0;
    private int commentcount = 0;

    private String handle = "";


    public Post(String message) { //constructor for normal posts
        this.message = message;
        this.id = idCounter;
        idCounter = idCounter + 1;

    }

    public Post(String message, boolean endorsementPost) { //constructor for endorsement posts
        this.message = message;
        this.id = idCounter;
        this.endorsementPost = endorsementPost;
        idCounter = idCounter + 1;
    }
    public Post(String message, boolean endorsementPost,boolean isnonacessible) { //constructor for the default post
        this.message = message;
        this.id = idCounter;
        this.endorsementPost = endorsementPost;
        idCounter = idCounter + 1;
        this.isnonacessible = isnonacessible;
    }

    public String toString() { //returns the details of a post in a formatted string
        String y = String.format("ID: %d\n" +
                "" +
                "Account: %s\n" +
                "No. endorsements: %d |" +
                " No. comments: %d\n"+
                this.message,this.id,this.handle,this.endorsementcount,this.commentcount);
        return y;
    }

    public int get_counter(){
        return idCounter;
    } //returns the account id counter

    public void set_counter(int u_counter){
        idCounter = u_counter;
    } //sets the id counter

    public void setisnonacessible(boolean isnonacessible){ //sets a post to be unacessible/acessible
        this.isnonacessible = isnonacessible;
    }
    public boolean getisnonacessible(boolean isnonacessible){ //returns wether a post is accessible
        return this.isnonacessible;
    }
    public static void resetidcounter(){ //add this
        idCounter = 0;
    } //resets the post id counter
    public Post getReference() {
        return this.reference;
    } //gets the reference of a post if it has one
    public void setReference(Post reference) {
        this.reference = reference;
    } //sets the reference of a post
    public void setComment(boolean isComment){ //ADD THIS
        this.isComment = isComment;
    } //defines wether the post is a comment
    public boolean isComment() { //ADD THIS
        return isComment;
    }//returns if the post is a comment or not

    public void increaseEndorsementcounter() {
        ++this.endorsementcount;
    }//increases the endorsement counter of a post

    public int getEndorsementcount(){
        return this.endorsementcount;
    } //returns the endorsement counter of a post

    public void increaseCommentCount() {
        ++this.commentcount;
    }//increases the comment counter of a post

    public void setId(int id) {
        this.id = id;
    }//sets the id of a post

    public int getId() {
        return this.id;
    } //gets the id of a post

    public void setEndorsementPost(boolean endorsementPost) {
        this.endorsementPost = endorsementPost;
    } //sets a post as an endorsement

    public boolean getEndorsementPost() {
        return endorsementPost;
    }//returns if a post is an endorsement

    public String getMessage() {
        return message;
    }//returns the message of a post

    public void addChild(Post post) {
        this.children.add(post);
    } //adds a child to the post's children array

    public void setHandle(String handle){
        this.handle = handle;
    } //sets the handle that is related to the post
    public String getHandle(){
        return this.handle;
    } //gets the handle of the account related to the post


    public ArrayList<Post> getChildren() {
        return children;
    }//returs the children array
}
