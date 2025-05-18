@Entity
public class Wallet {
	    @Id @GeneratedValue
	    private Long id;
	    @OneToOne
	    private User user;
	    private BigDecimal balance;
	    private String currency;

	    // Getters, Setters
}

