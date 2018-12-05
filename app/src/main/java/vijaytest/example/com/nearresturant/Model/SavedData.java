package vijaytest.example.com.nearresturant.Model;

import java.io.Serializable;

/**
 * Created by saiprasanthk on 04-12-2018.
 */

public class SavedData implements Serializable {
    private String sno;
    private String img;
    private String name;
    private String add;
    private String id;

    public String getSno() {
        return sno;
    }

    public void setSno(String sno) {
        this.sno = sno;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAdd() {
        return add;
    }

    public void setAdd(String add) {
        this.add = add;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
