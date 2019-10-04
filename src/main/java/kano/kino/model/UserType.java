package kano.kino.model;

/**
 * POJO object for UserType's
 * The UserType is made very simple, only 4 types are defined in the system. and they cannot be changed
 * */

public enum UserType {
    ADMINISTRATOR (1, "Administrator"),
    GUEST (2, "Guest"), //Same as Anonymous, but cookies are available
    REGISTERED (3, "Registered"),
    ANONYMOUS (4, "Anonymous"); //Cookies are not available for anonymous users

    private int id;
    private String name;

    UserType(int id, String name){
        this.id = id;
        this.name = name;
    }

    public int getId(){
        return this.id;
    }

    public String getName(){
        return this.name;
    }

    //Find the usertype from a id
    public static UserType getUserType(int id){
        for (UserType usertype : UserType.values()) {
            if(id == usertype.getId()){
                return usertype;
            }
        }
        return UserType.ANONYMOUS;
    }
}
