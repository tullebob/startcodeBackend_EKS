package entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.mindrot.jbcrypt.BCrypt;

@Entity
@NamedQuery(name = "User.deleteAllRows", query = "DELETE from User")
@Table(name = "users")
public class User implements Serializable {

  private static final long serialVersionUID = 1L;
  @Id
  @Basic(optional = false)
  @NotNull
  @Column(name = "user_name", length = 25)
  private String userName;
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 255)
  @Column(name = "user_pass")
  private String userPass;
  @JoinTable(name = "user_roles", joinColumns = {
    @JoinColumn(name = "user_name", referencedColumnName = "user_name")}, inverseJoinColumns = {
    @JoinColumn(name = "role_name", referencedColumnName = "role_name")})
  @ManyToMany
  private List<Role> roleList = new ArrayList<>();
  
  @OneToOne(cascade = CascadeType.PERSIST)
  private Computer computer;

  public List<String> getRolesAsStrings() {
    if (roleList.isEmpty()) {
      return null;
    }
    List<String> rolesAsStrings = new ArrayList<>();
    roleList.forEach((role) -> {
        rolesAsStrings.add(role.getRoleName());
      });
    return rolesAsStrings;
  }

  public User() {}

  //TODO Change when password is hashed
   public boolean verifyPassword(String pw){
        return(BCrypt.checkpw(pw, this.userPass));
    }

  public User(String userName, String userPass) {
    this.userName = userName;

    this.userPass = BCrypt.hashpw(userPass, BCrypt.gensalt(12));
  }


  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getUserPass() {
    return this.userPass;
  }

  public void setUserPass(String userPass) {
    this.userPass = BCrypt.hashpw(userPass, BCrypt.gensalt(12));
  }

  public List<Role> getRoleList() {
    return roleList;
  }

  public void setRoleList(List<Role> roleList) {
    this.roleList = roleList;
  }

  public void addRole(Role userRole) {
    roleList.add(userRole);
  }

    public Computer getComputer() {
        return computer;
    }

    public void setComputer(Computer computer) {
        this.computer = computer;
    }
  
  

}
