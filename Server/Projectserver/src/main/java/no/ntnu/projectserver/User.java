
package no.ntnu.projectserver;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * Data class holding information about user objects
 * @author Team Tungrocken
 */

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)

//husk å legge til dependency for entity! (drop and create)
@Entity
@Table(name = "APPUSER")

public class User implements Serializable {
   // @Id @GeneratedValue
   // Long id;
    @Id
    @NotNull String email;
    String sessionId;
    String password;
    String firstName;
    String lastName;
    boolean isAdmin;
    boolean active;
    
    @Temporal(javax.persistence.TemporalType.DATE)
    Date created = new Date();
    
    public User() {
    }

    public User(String email, String password, String firstName, String lastName, boolean isAdmin, boolean active) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.isAdmin = isAdmin;
        this.active = active;
        sessionId = ""+0;
    }
    
    
    // Following is a basic set of setters and getters methods
    public String getSessionId()
    {
        return sessionId;
    }
    
    public void setSessionId(String sessionId)
    {
        this.sessionId = sessionId;
    }
    
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public boolean isIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Date getCreated() {
        return created;
    }


}