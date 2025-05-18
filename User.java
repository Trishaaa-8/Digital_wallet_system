@entity
public class User {
	@Id @GeneratedValue
    private Long id;
    private String username;
    private String email;
    private String password;
    private String role = "USER";
    private boolean isDeleted = false;

}
