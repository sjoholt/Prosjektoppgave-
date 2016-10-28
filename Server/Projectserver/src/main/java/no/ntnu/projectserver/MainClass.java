
package no.ntnu.projectserver;

import java.util.List;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
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
    public User addUser(@QueryParam("email") String email, @QueryParam("password") String password , @QueryParam("firstName") String firstName, @QueryParam("lastName") String lastName)  {
        User u = new User(email,password,firstName, lastName,false, true);
        em.persist(u);
        return u;
    }
    
    // Create article in db      
    @GET
    @Path("addarticle") 
    public Article addArticle(@QueryParam("title") String title, @QueryParam("ingress") String ingress , @QueryParam("content") String content, @QueryParam("photoUrl") String photoUrl, @QueryParam("youtubeUrl") String youtubeUrl)  {
        Article a = new Article(title,ingress, content,photoUrl, youtubeUrl);
        em.persist(a);
        return a;
    }
    
    // Return a article based on ID
    @GET
    @Path("getarticle")
    public Article getArticle(@QueryParam("id") Long articleID) {
        return (Article)em.createQuery("SELECT u FROM Article u WHERE u.articleId = :paramID").setParameter("paramID", articleID).getSingleResult();
    }
    // Return all articles
    @GET
    @Path("articles")
    public List<Article> getArticles() {
        return em.createQuery("SELECT a FROM Article a", Article.class).getResultList();
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
}
