package model;

public class data {
    private int id;
    private String name;
    private String path;
    private String email;
    public data(int id, String name, String path, String email){
        this.id=id;
        this.name=name;
        this.path=path;
        this.email=email;
    }
    public data(int id, String name, String path){
        this.id=id;
        this.name=name;
        this.path=path;
      
    }
    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id=id;
    }
    public String getPath(){
        return path;
    }
    public void setpath(String path){
        this.path=path;
    }
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name=name;
    }
    public String getEmail(){
        return email;
    }
    public void setEmail(String email){
        this.email=email;
    }
    
}
