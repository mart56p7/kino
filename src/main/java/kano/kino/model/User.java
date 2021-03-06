package kano.kino.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


/**
 * User POJO.
 * Contains: Name, Password, UserType and id.
 *
 * The UserType can be set through a id.
 * */
public class User implements ModelInterface{
    private int id = 0;
    @NotNull
    @Size(min=2, max=50)
    private String name = null;
    @NotNull
    @Size(min=8, max=250)
    private String password = null;

    private UserType usertype = UserType.UNDEFINED;
    private int usertypeid = -1;

    public User(){

    }

    public User(int id, String name, String password, int usertype_id){
        this(id, name, password, UserType.getUserType(usertype_id));
    }

    public User(int id, String name, String password, UserType usertype){
        this.id = id;
        this.name = name;
        this.password = password;
        this.usertype = usertype;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserType getUserType()
    {
        if(this.usertype == UserType.UNDEFINED){
            if(this.usertypeid != -1){
                this.usertype = UserType.getUserType(usertypeid);
            }
        }
        return this.usertype;
    }

    public int getUsertypeid(){
        if(this.usertypeid == -1){
            if(this.usertype != UserType.UNDEFINED){
                this.usertypeid = this.usertype.getId();
            }
        }
        return this.usertypeid;
    }

    public void setUsertypeid(int usertype_id) {
        this.usertypeid = usertype_id;
        this.usertype = UserType.getUserType(usertype_id);
    }
    public void setUsertype(UserType usertype){
        this.usertypeid = usertype.getId();
        this.usertype = usertype;
    }
}
