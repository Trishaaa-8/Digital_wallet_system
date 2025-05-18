@RestController
@RequestMapping("/api/admin")
public class AdminController {
    @Autowired TransactionRepository txRepo;
    @Autowired WalletRepository walletRepo;

    @GetMapping("/flags")
    public List<Transaction> getFlagged() {
        return txRepo.findAll().stream().filter(Transaction::isFlagged).toList();
    }

    @GetMapping("/top-users")
    public List<Wallet> getTopUsers() {
        return walletRepo.findAll().stream()
            .sorted((a, b) -> b.getBalance().compareTo(a.getBalance()))
            .limit(10).toList();
    }
}

// --- Application Main ---
@SpringBootApplication
@EnableScheduling
public class WalletApplication {
    public static void main(String[] args) {
        SpringApplication.run(WalletApplication.class, args);
    }
}

// --- Swagger Config ---
@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI().info(new Info().title("Digital Wallet API").version("1.0.0"));
    }
}
