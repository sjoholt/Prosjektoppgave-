/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package no.ntnu.projectserver;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Sindre
 */

@Stateless
@Path("app")
@Produces(MediaType.APPLICATION_JSON)

public class MainClass {
    
    @PersistenceContext
    EntityManager em;
    
    @Resource(mappedName="jdbc/projectdb")
    DataSource dataSource;
    
}
