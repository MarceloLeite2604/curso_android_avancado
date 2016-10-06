package targettrust.com.br.aula2.model;

import com.orm.SugarRecord;
import com.orm.dsl.Table;

/**
 * Created by sala01 on 05/10/2016.
 */

@Table
public class User extends SugarRecord {

    private String completeName;
    private String largePictureAddress;
    private String emailAddress;

    public User() {
    }

    public User(String completeName, String largePictureAddress, String emailAddress) {
        this.completeName = completeName;
        this.largePictureAddress = largePictureAddress;
        this.emailAddress = emailAddress;
    }

    public String getCompleteName() {
        return completeName;
    }

    public String getLargePictureAddress() {
        return largePictureAddress;
    }

    public String getEmailAddress() {
        return emailAddress;
    }
}
