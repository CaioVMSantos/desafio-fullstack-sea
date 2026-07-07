import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class TesteHash {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        System.out.println("Admin: " + encoder.encode("123qwe!@#"));
        System.out.println("Padrao: " + encoder.encode("123qwe123"));
    }
}