package no.ntnu.projectserver;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Team Tungrocken
 */
@Entity
@Table(name = "USER_GROUP")
@Data @AllArgsConstructor @NoArgsConstructor
public class Group implements Serializable{
    public static final String USER = "USER";
    public static final String ADMIN = "ADMIN";
    
    @Id String name;
    @Id String email;
}
