package bgv.fit.bstu.eday.Models;

public class User {
    private Integer id;
    private String name;
    private String surname;
    private byte [] photo;
    private String login;
    private String password;

    public Integer getId() {
        return id;
    }
    public String getName() {return name; }
    public String getSurname() {
        return surname;
    }
    public byte[] getPhoto() {
        return photo;
    }
    public String getLogin() {
        return login;
    }
    public String getPassword() {
        return password;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setSurname(String surname) {this.surname=surname;}
    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }
    public void setLogin(String login) {
        this.login = login;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}
