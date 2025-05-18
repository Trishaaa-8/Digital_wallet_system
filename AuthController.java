@RestController;
@RequestMapping("/api/wallet")
public class WalletController {
    @Autowired WalletRepository walletRepo;
    @Autowired TransactionRepository txRepo;
    @Autowired UserRepository userRepo;

    @PostMapping("/deposit")
    public ResponseEntity<?> deposit(@RequestParam BigDecimal amount, Principal principal) {
        User user = userRepo.findByUsername(principal.getName()).orElseThrow();
        Wallet wallet = walletRepo.findByUser(user).orElseThrow();
        wallet.setBalance(wallet.getBalance().add(amount));
        walletRepo.save(wallet);

        Transaction tx = new Transaction();
        tx.setAmount(amount);
        tx.setType("DEPOSIT");
        tx.setTimestamp(LocalDateTime.now());
        tx.setToUser(user);
        txRepo.save(tx);

        return ResponseEntity.ok("Deposited successfully");
    }

    // Add withdraw, transfer, history...
}

// --- FraudDetectionService.java ---
@Service
public class FraudDetectionService {
    @Autowired TransactionRepository txRepo;

    public void scanTransactions() {
        List<Transaction> transactions = txRepo.findAll();
        for (Transaction tx : transactions) {
            if (tx.getAmount().compareTo(new BigDecimal("10000")) > 0) {
                tx.setFlagged(true);
                txRepo.save(tx);
            }
        }
    }

    @Scheduled(cron = "0 0 0 * * *") // Daily
    public void runDailyScan() {
        scanTransactions();
        System.out.println("Daily fraud scan completed");
    }
}

