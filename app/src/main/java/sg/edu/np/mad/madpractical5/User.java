package sg.edu.np.mad.madpractical5;

public class User {
    public String name;
    public String getName() {
        return name;
    }
    public void setName(String newName) {
        this.name = newName;
    }

    public String description;
    public String getDescription() {
        return description;
    }
    public void setDescription(String newDesc) {
        this.description = newDesc;
    }

    public int id;

    public int getId(){
        return id;
    }

    public void setId(int newId) {
        this.id = newId;
    }

    public boolean followed;
    public boolean getFollowed() {
        return followed;
    }
    public void setFollowed(boolean newFollowed) {
        this.followed = newFollowed;
    }

    public User(String name, String description, int id, boolean followed)
    {
        this.name = name;
        this.description = description;
        this.id = id;
        this.followed = followed;
    }
}