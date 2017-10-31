package envio;

import java.io.Serializable;

/**
 * Created by Jhon on 23/05/16.
 */
public class UserPerdedor implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String name;

    public UserPerdedor(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
