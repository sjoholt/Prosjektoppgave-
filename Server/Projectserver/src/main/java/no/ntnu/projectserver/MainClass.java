/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package no.ntnu.projectserver;

import java.util.List;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Tungrocken
 */

@Stateless
@Path("projectdb")
@Produces(MediaType.APPLICATION_JSON)



public class MainClass {
    
    @PersistenceContext
    EntityManager em;
    
    @Resource(mappedName="jdbc/project")
    DataSource dataSource;
    
     @GET
    @Path("users")
    public List<User> getAllUsers() {
        
        return em.createQuery("select u from User u",User.class).getResultList();
    }
    
}
