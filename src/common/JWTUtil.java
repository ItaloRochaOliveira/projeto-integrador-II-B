package common;

import java.util.Base64;
import java.util.Date;
import java.security.MessageDigest;
import java.nio.charset.StandardCharsets;

public class JWTUtil {
    private static final String SECRET_KEY = "EcoColeta_Secret_Key_2024_Projeto_Integrador";
    private static final long EXPIRATION_TIME = 24 * 60 * 60 * 1000; 

    
    public static String generateToken(int userId, String email, String role) {
        try {
            long now = System.currentTimeMillis();
            long exp = now + EXPIRATION_TIME;

            
            String header = "{\"alg\":\"HS256\",\"typ\":\"JWT\"}";
            String encodedHeader = Base64.getUrlEncoder().withoutPadding()
                .encodeToString(header.getBytes(StandardCharsets.UTF_8));

            
            String payload = String.format(
                "{\"userId\":%d,\"email\":\"%s\",\"role\":\"%s\",\"iat\":%d,\"exp\":%d}",
                userId, email, role, now / 1000, exp / 1000
            );
            String encodedPayload = Base64.getUrlEncoder().withoutPadding()
                .encodeToString(payload.getBytes(StandardCharsets.UTF_8));

            
            String data = encodedHeader + "." + encodedPayload;
            String signature = createSignature(data);

            return data + "." + signature;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao gerar token JWT", e);
        }
    }

    
    public static boolean validateToken(String token) {
        try {
            if (token == null || token.isEmpty()) {
                return false;
            }

            String[] parts = token.split("\\.");
            if (parts.length != 3) {
                return false;
            }

            
            String data = parts[0] + "." + parts[1];
            String expectedSignature = createSignature(data);
            if (!expectedSignature.equals(parts[2])) {
                return false;
            }

            
            String payload = new String(Base64.getUrlDecoder().decode(parts[1]), StandardCharsets.UTF_8);
            long exp = extractExpFromPayload(payload);
            long now = System.currentTimeMillis() / 1000;

            return now < exp;
        } catch (Exception e) {
            return false;
        }
    }

    
    public static UserInfo extractUserInfo(String token) {
        try {
            if (!validateToken(token)) {
                return null;
            }

            String[] parts = token.split("\\.");
            String payload = new String(Base64.getUrlDecoder().decode(parts[1]), StandardCharsets.UTF_8);

            
            int userId = extractIntFromPayload(payload, "userId");
            String email = extractStringFromPayload(payload, "email");
            String role = extractStringFromPayload(payload, "role");

            return new UserInfo(userId, email, role);
        } catch (Exception e) {
            return null;
        }
    }

    
    private static String createSignature(String data) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            String signData = data + SECRET_KEY;
            byte[] hash = digest.digest(signData.getBytes(StandardCharsets.UTF_8));
            return Base64.getUrlEncoder().withoutPadding().encodeToString(hash);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao criar assinatura", e);
        }
    }

    
    private static long extractExpFromPayload(String payload) {
        String[] parts = payload.split("\"exp\":");
        if (parts.length < 2) return 0;
        String expStr = parts[1].split("[,}]")[0].trim();
        return Long.parseLong(expStr);
    }

    
    private static int extractIntFromPayload(String payload, String key) {
        String[] parts = payload.split("\"" + key + "\":");
        if (parts.length < 2) return 0;
        String valueStr = parts[1].split("[,}]")[0].trim();
        return Integer.parseInt(valueStr);
    }

    
    private static String extractStringFromPayload(String payload, String key) {
        String[] parts = payload.split("\"" + key + "\":\"");
        if (parts.length < 2) return null;
        return parts[1].split("\"")[0];
    }

    
    public static class UserInfo {
        public final int userId;
        public final String email;
        public final String role;

        public UserInfo(int userId, String email, String role) {
            this.userId = userId;
            this.email = email;
            this.role = role;
        }
    }
}
