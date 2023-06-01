package socialmedia;

import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class SocialMedia implements SocialMediaPlatform {

    ArrayList<Account> accounts = new ArrayList<>(); //creates a global list of accounts

    ArrayList<Post> posts = new ArrayList<>();//creates a global list of posts
    Post placeholder = new Post("The original content was removed from the system and is no longer available.",false,true); //creates a default post not tied to any account and not stored in any array of posts



    @Override
    public int createAccount(String handle) throws IllegalHandleException, InvalidHandleException {
        // TODO Auto-generated method stub
        //sequential number of the account

        for (Account a : accounts){
            if(a.get_handle().equals(handle)){ //iterates over the accounts array to check if the handle already exists
                throw new IllegalHandleException("Handle already exists in the platform.");
            }
        }

        if (handle == null || handle.trim().isEmpty() || handle.length() > 30) { //checks to see if the handle is valid
            throw new InvalidHandleException("Handle is empty, has more than 30 characters, or has white spaces.");
        }
        Account user = new Account(handle);

        accounts.add(user); //creates the new account and adds it to the accounts array

        return user.get_id();
    }

    @Override
    public int createAccount(String handle, String description) throws IllegalHandleException, InvalidHandleException {
        // TODO Auto-generated method stub
        for (Account a : accounts){
            if(a.get_handle().equals(handle)){ //checks to see if handle already exists
                throw new IllegalHandleException("Handle already exists in the platform.");
            }
        }

        if (handle == null || handle.trim().isEmpty() || handle.length() > 30) { //checks to see if the account handle is valid
            throw new InvalidHandleException("Handle is empty, has more than 30 characters, or has white spaces.");
        }

        Account user = new Account(handle, description);

        accounts.add(user); //creates the account and adds it to the accounts array

        return user.get_id();
    }

    @Override
    public void removeAccount(int id) throws AccountIDNotRecognisedException {
        // TODO Auto-generated method stub
        boolean checked = false;

        for (Account a : accounts){ //iterates over the accounts array and then removes the account once it has been found
            if(a.get_id() == id){
                checked = true;
                accounts.removeIf(obj -> obj.get_id() == id);
                break;
            }
        }
        if (!checked){ //throws an exception if the account doesn't exist
            throw new AccountIDNotRecognisedException();
        }
    }

    @Override
    public void removeAccount(String handle) throws HandleNotRecognisedException {
        // TODO Auto-generated method stub
        boolean checked = false;


        for (Account a : accounts){ //iterates over the accounts array and then removes the account once it has been found
            if(a.get_handle().equals(handle)){
                checked = true;
                accounts.removeIf(obj -> obj.get_handle().equals(handle));
                break;
            }

        }
        if (!checked){//throws an exception if the account doesn't exist
            throw new HandleNotRecognisedException();
        }

    }

    @Override
    public void changeAccountHandle(String oldHandle, String newHandle)
            throws HandleNotRecognisedException, IllegalHandleException, InvalidHandleException {
        // TODO Auto-generated method stub

        for (Account a : accounts){  //checks to see if the handle already exists
            if(a.get_handle().equals(newHandle)){
                throw new IllegalHandleException("Handle already exists in the platform.");
            }
        }

        if (newHandle == null || newHandle.trim().isEmpty() || newHandle.length() > 30) { //checks to see if the handle is valid
            throw new InvalidHandleException("Handle is empty, has more than 30 characters, or has white spaces.");
        }
       boolean ishandle = false;
        for (Account i : accounts) { //changes the handle
            if (i.get_handle().equals(oldHandle)) {
                i.set_handle(newHandle);
                ishandle = true;
            }
        }
        if(ishandle == false){ //throws an exception if the account doesn't exist
            throw new HandleNotRecognisedException();
        }

    }

    @Override
    public void updateAccountDescription(String handle, String description) throws HandleNotRecognisedException {
        // TODO Auto-generated method stub
        boolean checked = false;

        for (Account i : accounts) {
            if (i.get_handle().equals(handle)) { //finds the account and changes the description
                checked = true;
                i.set_description(description);
            }
        }

        if (!checked){ //throws exception if the account isn't found
            throw new HandleNotRecognisedException();
        }

    }

    @Override
    public String showAccount(String handle) throws HandleNotRecognisedException {
        // TODO Auto-generated method stub

        String a = "";
        boolean isHandle = false;
        for (Account i : accounts) {
            if (i.get_handle().equals(handle)) { //finds the account and prints the account's details
                isHandle = true;
                a = "ID: " + i.get_id() + "\n" +
                        "Handle: " + i.get_handle() + "\n" +
                        "Description: " + i.getDescription() + "\n" +
                        "Post count: " + i.get_number_of_posts() + "\n" +
                        "Endorse count: " + i.get_number_of_endorsements();
            }
        }
        if(!isHandle) { //raises an exception if the account doesn't exist
            throw new HandleNotRecognisedException();

        }
        return a;
    }

    @Override
    public int createPost(String handle, String message) throws HandleNotRecognisedException, InvalidPostException {
        // TODO Auto-generated method stub

        boolean matchHandle = false;
        int x = 0;

        for (Account account : accounts) {
            if (account.get_handle().equals(handle)) {//checks to see if handle exists
                matchHandle = true;
                if (message.length() == 0 || message.length() > 100) { //checks message conditions are met
                    throw new InvalidPostException();
                } else {
                    Post p = new Post(message); //creates the post
                    posts.add(p);
                    account.add_post(p);
                    break;
                }

            }
        }
        if (matchHandle == false) { //raises exception if the account doesn't exist
            throw new HandleNotRecognisedException();
        }
        return x;
    }

    @Override
    public int endorsePost(String handle, int id)
            throws HandleNotRecognisedException, PostIDNotRecognisedException, NotActionablePostException {
        boolean matchHandle = false;
        boolean matchPost = false;
        int identifier = 0;
        Account child= null;
        for (Account account : accounts) { //iterates over each post made by an account
            for (Post post : account.get_posts()){
                if (post.getId() == id) {
                    for (Account valid :accounts) {
                        if (valid.get_handle().equals(handle)) {
                            matchHandle = true;
                            child = valid; //identifies the child account that is endorsing the post
                        }
                    }
                    matchPost = true;
                    if (post.getEndorsementPost() == true) { //raises an exception if the referenced post is an endorsement
                        throw new NotActionablePostException();

                    }
                    else {
                        Post endorsedPost = new Post("EP@" + handle + ": " + post.getMessage(), true); //create the endoresement post
                        child.add_post(endorsedPost);//adds the post to the account
                        endorsedPost.setReference(post); //sets a reference to the parent post
                        posts.add(endorsedPost); //adds the post to the list of posts
                        post.addChild(endorsedPost); //adds the endorsement to the parent's children
                        post.increaseEndorsementcounter(); //increases endorsement counter
                        identifier = endorsedPost.getId(); //sets the id to be returned from the method
                        break;
                    }
                }

            }

        }
        if (matchHandle == false) { //throws an exception if the account doesn't exist
            throw new HandleNotRecognisedException();
        }
        if (matchPost == false) {//throws an exception if the post doesn't exist
            throw new PostIDNotRecognisedException();
        }
        return identifier;
    }


    @Override
    public int commentPost(String handle, int id, String message) throws HandleNotRecognisedException,
            PostIDNotRecognisedException, NotActionablePostException, InvalidPostException {
        if (message.length() == 0 || message.length() > 100) { //checks message conditions are met
            throw new InvalidPostException();
        }

        Account commenter = null;
        Post post = null;

        boolean check_handle = false;
        boolean check_id = false;

        for(Post p : posts){ //iterates over the array of posts and finds the post being commented on
            if(p.getId() == id){
                check_id = true;
                if(p.getEndorsementPost()){ //raises am exception if the parent post is an endorsement post
                    throw new NotActionablePostException();
                }
                post = p; //sets the parent post
            }
        }

        for (Account a : accounts){ //iterates over the accounts array to find the account making the comment
            if (a.get_handle().equals(handle)){
                check_handle = true;
                commenter = a;
            }
        }

        if(!check_handle){ //throws exception if the account doesnt exist
            throw new HandleNotRecognisedException();
        }

        if(!check_id){//throws exception if the post doesn't exist
            throw new PostIDNotRecognisedException();
        }

        Post comment = new Post(message); //cretes the comment post
        post.increaseCommentCount(); //increases the parent post's comment count
        comment.setComment(true); //sets the comment
        comment.setReference(post); //sets the comments reference to the parent
        posts.add(comment); //adds the comment to the post array
        commenter.add_post(comment); //adds the comment to the commenters account
        post.addChild(comment); //adds the comment to the parents children
        return comment.getId(); //returns the comments id
    }

    @Override
    public void deletePost(int id) throws PostIDNotRecognisedException {
        // TODO Auto-generated method stub
        boolean idmatch = false;

        Post p = null;
        for(Post post : posts) { //iterates over the post array to find the post
            if (post.getId() == id) {
                p = post;
                idmatch = true;
                break;
            }
        }
        if(idmatch == true){
            for (Iterator<Post> it = posts.iterator();it.hasNext();){ //iterates over the post array
                Post ep = it.next();
                if(ep.getReference() == p && ep.getEndorsementPost()){//if the post matches the id and is an endorsement post
                    for(Account a : accounts){ //iterate over the accounts to find and remove the post
                        for (Iterator<Post> aep = a.get_posts().iterator();aep.hasNext();){
                            Post z = aep.next();
                            if (z.getId() == ep.getId()){
                                aep.remove();
                            }
                        }
                    }
                    it.remove();
                }
            }
            for (Post child:p.getChildren()){ //if the post has children that are comments
                if(child.isComment()){
                    child.setReference(placeholder);//sets the reference of the comment to the default post
                }
            }
            for(Account a : accounts){ //iterates over the accounts and removes the post
                for (Iterator<Post> ap = a.get_posts().iterator();ap.hasNext();){
                    Post x = ap.next();
                    if (x.getId() == p.getId()){
                        ap.remove();
                    }
                }
            }
            posts.remove(p); //removes the post from the global array

        }
        else{ //raises an exception if the post doesn't exist
            throw new PostIDNotRecognisedException("Invalid post ID");
        }
    }

    @Override
    public String showIndividualPost(int id) throws PostIDNotRecognisedException {
        boolean idMatch = false;
        String x = "";
        for (Account account : accounts) {
            for (Post post : account.get_posts()) { //iterates over the array of posts
                if (post.getId() == id) {
                    idMatch = true;
                    x = post.toString();
                    //returns the formatted string of the post details
                }
            }
        }
        if (idMatch == false) { //raises an exception if the post doesn't exist
            throw new PostIDNotRecognisedException();
        }
        return x;
    }



    @Override
    public StringBuilder showPostChildrenDetails(int id) throws PostIDNotRecognisedException, NotActionablePostException {
        StringBuilder outputString = new StringBuilder();
        boolean foundPost = false;
        for (Post post : posts) { //iterates over the post array and finds the post
            if (post.getId() == id) {
                foundPost = true;
                if (post.getEndorsementPost()) { //raises an exception if the post is an endorsement
                    throw new NotActionablePostException();
                }
                    outputString.append(showIndividualPost(id)); //adds the post to the stringbuilder
                    for (Post child : post.getChildren()) { //iterates over the children of the post
                        if (!child.getEndorsementPost()){ //checks if the child is not an endorsement
                            if (child.getChildren().isEmpty()) {//adds a final line if there are no more children left
                                outputString.append("\n");
                                outputString.append("| >  ");
                                outputString.append(showPostChildrenDetails(child.getId()).toString().indent(5).strip());
                            } else { //recursively call this method on each child post adding them to the stringbuilder each time
                                outputString.append("\n");
                                outputString.append("|");
                                outputString.append("\n");
                                outputString.append("| >  ");
                                outputString.append(showPostChildrenDetails(child.getId()).toString().indent(5).strip());
                            }
                    }
               }
            }
        }
        if (!foundPost) { //raises an exception if the post doesn't exist
            throw new PostIDNotRecognisedException();
        }

        return outputString; //returns the formatted stringbuilder
    }

    @Override
    public int getNumberOfAccounts() { //returns the number of accounts
        //TODO Auto-generated method stub
        return accounts.size();
    }

    @Override
    public int getTotalOriginalPosts() { //returns the number of original posts
        // TODO Auto-generated method stub
        int counter = 0;
        for (Account user : accounts){ //iterates over accounts and checks if each post is an original post and increments the counter
            for (Post p : user.get_posts()){
                if (!p.getEndorsementPost()&&!p.isComment()){
                    ++counter;
                }
            }
        }
        return counter;
    }

    @Override
    public int getTotalEndorsmentPosts() { //returns the number of endorsement posts
        // TODO Auto-generated method stub
        int counter = 0;
        for (Account user : accounts){//iterates over the accounts and identifies all the endorsement posts
            for (Post p : user.get_posts()){
                if (p.getEndorsementPost()){
                    ++counter;
                }
            }
        }
        return counter;
    }

    @Override
    public int getTotalCommentPosts() { //returns the number of comment posts
        // TODO Auto-generated method stub
        int counter = 0;
        for (Account user : accounts){//iterates over the accounts and identifies all the comment posts
            for (Post p : user.get_posts()){
                if (p.isComment()){
                    ++counter;
                }
            }
        }
        return counter;
    }

    @Override
    public int getMostEndorsedPost() {
        // TODO Auto-generated method stub
        int max_endorsment = 0;
        int id = 0;
        for (Account user : accounts){ //iterates over the accounts and finds the most endorsed post
            for (Post p : user.get_posts()){
                if(p.getEndorsementcount() > max_endorsment){
                    max_endorsment = p.getEndorsementcount();
                    id = p.getId();
                }
            }
        }
        return id;
    }

    @Override
    public int getMostEndorsedAccount() {
        // TODO Auto-generated method stub
        int current_endorsment = 0;
        int max_endorsment = 0;
        int id = 0;
        for (Account user : accounts){//iterates over the accounts and finds the account with the most enorsements made to their posts
            for (Post p : user.get_posts()){
                current_endorsment += p.getEndorsementcount();
            }
            if (current_endorsment > max_endorsment){
                max_endorsment = current_endorsment;
                id = user.get_id();
            }
            current_endorsment = 0;
        }
        return id;
    }

    @Override
    public void erasePlatform() { //clears the platform by emptying the arrays and counters
        // TODO Auto-generated method stub
        Post.resetidcounter();
        Account.resetidcounter();
        accounts.clear();
        posts.clear();
    }

    @Override
    public void savePlatform(String filename) throws IOException {
        FileOutputStream fileOut = new FileOutputStream(filename);
        ObjectOutputStream out = new ObjectOutputStream(fileOut);
        out.writeObject(accounts);
        out.writeObject(posts); // Add this line to write the 'posts' ArrayList
        out.writeInt(accounts.get(0).get_counter()); // Add this line to write the 'counter' variable
        out.writeInt(posts.get(0).get_counter()); // Add this line to write the 'counter' variable
        out.close();
        fileOut.close();
        System.out.printf("Serialized data is saved in %s%n", filename);
    }

    @Override
    public void loadPlatform(String filename) throws IOException, ClassNotFoundException {
        FileInputStream fileIn = new FileInputStream(filename);
        ObjectInputStream in = new ObjectInputStream(fileIn);
        ArrayList<Account> accountList = readObject(in);
        ArrayList<Post> postList = readObject(in);
        int counterValue = in.readInt();
        int counterValue2 = in.readInt();
        in.close();
        fileIn.close();
        accounts = accountList;
        posts = postList;
        accounts.get(0).set_counter(counterValue);
        posts.get(0).set_counter(counterValue2);
        System.out.printf("Serialized data is read from %s%n", filename);
    }
    @SuppressWarnings("unchecked")
    private <T> ArrayList<T> readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        return (ArrayList<T>) in.readObject();
    }
}
