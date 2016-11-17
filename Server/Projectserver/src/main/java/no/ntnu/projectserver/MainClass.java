
package no.ntnu.projectserver;


import java.io.Serializable;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;
import javax.validation.constraints.NotNull;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

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
    
    // Return a list of all users
    @GET
    @Path("users")
    public List<User> getAllUsers() {
        return em.createQuery("SELECT u from User u",User.class).getResultList();
    }
    
    
    // Search for a user by first or last name
    @GET
    @Path("finduser")
    public List<User> findUser(@QueryParam("name") String userParam) {
        userParam = userParam.toLowerCase();
        return em.createQuery("SELECT u FROM User u WHERE LOWER(u.firstName) LIKE LOWER(:userName) OR LOWER(u.lastName) LIKE LOWER(:userName)").setParameter("userName","%"+userParam+"%").getResultList();
    }
    
    // Return a user based on ID
    @GET
    @Path("getuser")
    public User getUser(@QueryParam("id") Long userID) {
        return (User) em.createQuery("SELECT u FROM User u WHERE u.id = :paramID").setParameter("paramID", userID).getSingleResult();
    }
    
    // Create user in db, not as admin, but active
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
    
    // Create article in db      
    @GET
    @Path("addarticle") 
    public Article addArticle(@NotNull @QueryParam("title") String title,@NotNull @QueryParam("ingress") String ingress , @NotNull @QueryParam("content") String content, @NotNull @QueryParam("photoUrl") String photoUrl, @QueryParam("youtubeUrl") String youtubeUrl)  {

        Article a = new Article(title,ingress, content,photoUrl, youtubeUrl);
        em.persist(a);
        return a;
    }
    
    // Return a article based on ID
    @GET
    @Path("secure/getarticle")
    public List<Article>getArticle(@QueryParam("id") Long articleID) {
        return em.createQuery("SELECT u FROM Article u WHERE u.articleId = :paramID").setParameter("paramID", articleID).getResultList();
    }
    
    // Return a article based on ID  -- fjernes når authentication fungerer
    @GET
    @Path("getarticle")
    public List<Article>getArticle2(@QueryParam("id") Long articleID) {
        return em.createQuery("SELECT u FROM Article u WHERE u.articleId = :paramID").setParameter("paramID", articleID).getResultList();
    }
    
    // Return all articles
    @GET
    @Path("articles")
    public List<Article> getArticles() {
        return em.createQuery("SELECT a FROM Article a order by a.articleId desc", Article.class).getResultList();
    }
    
    // Return articles based on search (title only)
    @GET
    @Path("findarticle")
    public List<Article> findArticle(@QueryParam("search") String articleParam) {
        articleParam = articleParam.toLowerCase();
        return em.createQuery("SELECT u FROM Article u WHERE LOWER(u.title) LIKE LOWER(:articleName) OR LOWER(u.ingress) LIKE LOWER(:articleName) OR LOWER(u.content) LIKE LOWER(:articleName)").setParameter("articleName","%"+articleParam+"%").getResultList();
    }
    
    // Change active state for user.
    @GET
    @Path("alteractive")
    public User userState(@QueryParam("change") Long alterParam) {

        User u = em.find(User.class, alterParam);
        if (u.isActive()) {
            u.setActive(false);
        } else if (!u.isActive()) {
            u.setActive(true);
        }
        em.merge(u);
        return u;
    }
    
    
    
    @GET
    @Path("checkCredentials")
    public User login(@QueryParam("email") String emailParam, @QueryParam("password") String passwordParam)
    {
        User loggedIn = null;
        List<User> users = em.createQuery("SELECT u FROM User u WHERE LOWER(u.email) LIKE LOWER(:emailParam) AND LOWER(u.password) LIKE LOWER(:passwordParam)").setParameter("emailParam","%"+emailParam+"%").setParameter("passwordParam","%"+passwordParam+"%").getResultList();
        if(users.size()>0)
        {
            loggedIn = users.get(0);
            if(loggedIn.getSessionId().equals("0"))
            {
                createSessionId(loggedIn);
            }
            else
            {
                loggedIn = null;
            }
        }
        return loggedIn;
    }
    
    @GET
    @Path("login")
    public PreppedUser sendUser(@QueryParam("email") String emailParam,@QueryParam("password") String passwordParam)
    {
        PreppedUser result = null;
        User u = login(emailParam,passwordParam);
        if(u != null)
        {
            result = new PreppedUser(u.getId(),u.getSessionId(),u.getEmail(),u.getFirstName(),u.getLastName(),u.isIsAdmin(),u.isActive(),u.getCreated());
        }
        return result;
    }
    
    @GET
    @Path("session")
    public String getSessionId(@QueryParam("email") String emailParam)
    {
        List<String> sessions = em.createQuery("SELECT u.sessionId FROM User u WHERE LOWER(u.email) LIKE LOWER(:emailParam)").setParameter("emailParam","%"+emailParam+"%").getResultList();
        String sessionId = sessions.get(0);
        return sessionId;
    }
    
    public String createSessionId(User u)
    {
        String result = null;
        String sessionId;
        Random random = new Random();
        int idAddition = random.nextInt(1000);
        sessionId = ""+u.getId().toString()+idAddition;
        if(getSessionId(u.email).equals("0"))
        {
            u.setSessionId(sessionId);
            result = sessionId;
        }
        return result;
    }
    
    @XmlRootElement
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class PreppedUser {
        Long id;
        String sessionId;
        String email;
        String firstName;
        String lastName;
        boolean isAdmin;
        boolean active;
        Date created = new Date();
        
        PreppedUser(Long id, String sessionId, String email, String firstName, String lastName, boolean isAdmin, boolean active, Date created)
        {
            this.id = id;
            this.sessionId = sessionId;
            this.email = email;
            this.firstName = firstName;
            this.lastName = lastName;
            this.isAdmin = isAdmin;
            this.active = active;
            this.created = created;
        }
        
        PreppedUser() {
        }
    }
}
