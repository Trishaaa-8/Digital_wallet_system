@Entity
public class Transaction {
	@Id @GeneratedValue
    private Long id;
    private BigDecimal amount;
    private String type;
    private LocalDateTime timestamp;
    @ManyToOne
    private User fromUser;
    @ManyToOne
    private User toUser;
    private boolean isFlagged;
    private boolean isDeleted;

    // Getters, Setters
}
