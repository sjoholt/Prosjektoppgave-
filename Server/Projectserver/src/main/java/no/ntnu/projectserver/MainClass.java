package no.ntnu.projectserver;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;
import javax.validation.constraints.NotNull;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

/**
 * This is a server solution for the Tungrocken news app for Android systems.
 * It is a REST based server implemented with JAX-RS.
 * 
 * @author Team Tungrocken
 */

@Stateless
@Path("app")
@Produces(MediaType.APPLICATION_JSON)

public class MainClass {
    
    @PersistenceContext
    EntityManager em;
    
    @Resource(mappedName="jdbc/project")
    DataSource dataSource;

    /**
     * Get a list of all registered users
     * @return a list of all users
     */
    @GET
    @Path("users")
    public List<User> getAllUsers() {
        return em.createQuery("SELECT u from User u",User.class).getResultList();
    }
    
    /**
     * Search for a user by first or last name
     * @param userParam
     * @return list of found users
     */
    @GET
    @Path("finduser")
    public List<User> findUser(@QueryParam("name") String userParam) {
        userParam = userParam.toLowerCase();
        return em.createQuery("SELECT u FROM User u WHERE LOWER(u.firstName) LIKE LOWER(:userName) OR LOWER(u.lastName) LIKE LOWER(:userName)").setParameter("userName","%"+userParam+"%").getResultList();
    }
    
    /**
     * Get a user based on email
     * @param email
     * @return a user based on email
     */
    @GET
    @Path("secure/getuser")
    public List<User> getUser(@QueryParam("email") String email) {
        return em.createQuery("SELECT u FROM User u WHERE LOWER(u.email) LIKE LOWER(:paramID)").setParameter("paramID", email).getResultList();
    }
    
    /**
     * Create a new user in the database
     * @param email
     * @param password
     * @param firstName
     * @param lastName
     * @return list containing new user
     */
    @GET
    @Path("adduser")
    public List<User> addUser(@QueryParam("email") String email, @QueryParam("password") String password , @QueryParam("firstName") String firstName, @QueryParam("lastName") String lastName)  {
        User u = null;
        try{
            byte[] hash = MessageDigest.getInstance("SHA-256").digest(password.getBytes("UTF-8"));
            u = new User(email,Base64.getEncoder().encodeToString(hash),firstName, lastName,false, true);
            em.persist(u);
            em.persist(new Group(Group.USER,email));
        }
        catch(Exception e)
        {
            Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, "Failed to add user");
        }
        return em.createQuery("SELECT u FROM User u WHERE u.email = :paramID").setParameter("paramID", email).getResultList();
    }
        
    /**
     * Create a new article in the database
     * @param title
     * @param ingress
     * @param content
     * @param photoUrl
     * @param youtubeUrl
     * @return the new article
     */
    @GET
    @Path("addarticle") 
    public Article addArticle(@NotNull @QueryParam("title") String title,@NotNull @QueryParam("ingress") String ingress , @NotNull @QueryParam("content") String content, @NotNull @QueryParam("photoUrl") String photoUrl, @QueryParam("youtubeUrl") String youtubeUrl)  {
        Article a = new Article(title,ingress, content,photoUrl, youtubeUrl);
        em.persist(a);
        return a;
    }
    
    /**
     * Get an article based on ID
     * @param articleID
     * @return list of articles found
     */
    @GET
    @Path("secure/getarticle")
    public List<Article>getArticle(@QueryParam("id") Long articleID) {
        return em.createQuery("SELECT u FROM Article u WHERE u.articleId = :paramID").setParameter("paramID", articleID).getResultList();
    }
    
    /**
     * Get a list of all articles in the database
     * @return list of all articles
     */
    @GET
    @Path("articles")
    public List<Article> getArticles() {
        return em.createQuery("SELECT a FROM Article a order by a.articleId desc", Article.class).getResultList();
    }
    
    /**
     * Search for an article
     * @param articleParam
     * @return list of articles found
     */
    @GET
    @Path("findarticle")
    public List<Article> findArticle(@QueryParam("search") String articleParam) {
        articleParam = articleParam.toLowerCase();
        return em.createQuery("SELECT u FROM Article u WHERE LOWER(u.title) LIKE LOWER(:articleName) OR LOWER(u.ingress) LIKE LOWER(:articleName) OR LOWER(u.content) LIKE LOWER(:articleName)").setParameter("articleName","%"+articleParam+"%").getResultList();
    }
    
    /**
     * Edit a user
     * @param email
     * @param fname
     * @param lname
     * @param password
     * @return a list containing the edited user
     * @throws UnsupportedEncodingException
     */
    @GET
    @Path("edituser")
    public List<User> editUser(@QueryParam("email") String email,@QueryParam("firstname") String fname,@QueryParam("lastname") String lname, @QueryParam("password") String password) throws UnsupportedEncodingException{
        User u = em.find(User.class, email);
        try {
            byte[] hash = MessageDigest.getInstance("SHA-256").digest(password.getBytes("UTF-8"));
            u.setFirstName(fname);
            u.setLastName(lname);
            u.setPassword(Base64.getEncoder().encodeToString(hash));
            em.merge(u);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, ex);
        }     
        return em.createQuery("SELECT u FROM User u WHERE u.email = :paramID").setParameter("paramID", email).getResultList();
    }
}
