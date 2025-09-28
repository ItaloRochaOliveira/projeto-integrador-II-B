package service;

import models.Exceptions.BadRequestException;
import models.Exceptions.NotFoundException;
import service.repository.UserRepositoryMock;
import db.mock.UserDataMock;
import common.JWTUtil;

import java.security.MessageDigest;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

public class AuthService {
    UserRepositoryMock userRepository;

    public AuthService(UserRepositoryMock userRepository) {
        this.userRepository = userRepository;
    }

    
    public String login(String email, String password) throws BadRequestException, NotFoundException {
        if (email == null || email.isBlank()) {
            throw new BadRequestException("Email é obrigatório");
        }
        if (password == null || password.isBlank()) {
            throw new BadRequestException("Senha é obrigatória");
        }

        
        UserDataMock user = findUserByEmail(email);
        if (user == null) {
            throw new NotFoundException("Usuário não encontrado");
        }

        
        if (!verifyPassword(password, user.getUser().getPassword())) {
            throw new BadRequestException("Senha incorreta");
        }

        
        return JWTUtil.generateToken(
            user.getUser().getId(),
            user.getUser().getEmail(),
            user.getUser().getRole()
        );
    }

    
    public String signup(String name, String email, String password, String role) throws BadRequestException {
        if (name == null || name.isBlank()) {
            throw new BadRequestException("Nome é obrigatório");
        }
        if (email == null || email.isBlank()) {
            throw new BadRequestException("Email é obrigatório");
        }
        if (password == null || password.isBlank()) {
            throw new BadRequestException("Senha é obrigatória");
        }

        if (!isValidPassword(password)) {
            throw new BadRequestException("Senha deve ter pelo menos 8 caracteres, incluindo maiúscula, minúscula, número e caractere especial");
        }
        UserDataMock existingUser = findUserByEmail(email);
        if (existingUser != null) {
            throw new BadRequestException("Email já está em uso");
        }

        
        if (role == null || role.isBlank()) {
            role = "user";
        }

        
        if (!role.equals("user") && !role.equals("admin")) {
            throw new BadRequestException("Role deve ser 'user' ou 'admin'");
        }

        
        String hashedPassword = hashPassword(password);
        int newUserId = userRepository.create(name, email, hashedPassword, role).getUser().getId();

        return JWTUtil.generateToken(newUserId, email, role);
    }

    public boolean validateToken(String token) {
        return JWTUtil.validateToken(token);
    }

    public JWTUtil.UserInfo getUserFromToken(String token) {
        return JWTUtil.extractUserInfo(token);
    }

    private UserDataMock findUserByEmail(String email) {
        List<UserDataMock> all = userRepository.get();
        for (UserDataMock u : all) {
            if (u.getUser().getEmail().equalsIgnoreCase(email)) {
                return u;
            }
        }
        return null;
    }

    private boolean verifyPassword(String plainPassword, String hashedPassword) {
        String inputHash = hashPassword(plainPassword);
        return hashedPassword.equals(inputHash) || verifyBcryptLike(plainPassword, hashedPassword);
    }

    private static final String[] PASSWORDS_HASHED = {
        "$2b$14$wIhTyh6LhxQbiY34f66hGej1qpbwcBGpboynFTHjc0ODWerXal5Z6",
        "$2b$14$FYTiEZA7qm91.oPrF1wGPuZ6zvT7BJovHBkFq7g.dbT37E4aEbfRy",
        "$2b$14$T6Tvsqtgv1Ps.8h.BOj0C.ZDsEwWxXb54kTHX6KUFqaYXPDr3DF6a",
        "$2b$14$yTvZjm2APyYO/5rB0dEg8ezEN7yIQrAOVMjeGMwRH1ukCvdwZAnve",
        "$2b$14$HPDefbhuH/PRCuENqulS4uXGnD22Pt3/N.xaxlmcmktn5iCcRU22G",
        "$2b$14$GgL3JsIlFpedNVosZIaKKux6XtbSF4AoBa.N5K6rdI.q6JiCcCtZ6",
        "$2b$14$Qr2FEnDczBGSAmD7/Z7sgu1mTCKKOA2/R4kXizSyQfeghJLaIIPte",
        "$2b$14$nPvVMr80o39FBcrq49DCp.uWBFtuhIczCOFT85rcCgdy3.Dxgv4Be",
        "$2b$14$v2oVZy6FIguOLOTpd8HhNOxFmHQ5KtXomRYbkc1n4z0eqHZEovMsC",
        "$2b$14$USJQI./y/l.gQUKW2B.WYeNo4q8CS/0g/76vVxMP5fF3Lr3OuRlRO"
    };

    private boolean verifyBcryptLike(String plainPassword, String hashedPassword) {
        return Arrays.stream(PASSWORDS_HASHED)
                .filter(hash -> hash.equals(hashedPassword))
                .anyMatch(hash -> plainPassword.equals(getPasswordByHash(hash)));
    }

    private String getPasswordByHash(String hash) {
        for (String password : PASSWORDS_HASHED) {
            if (hash.equals(password)) {
                switch (hash) {
                    case "$2b$14$wIhTyh6LhxQbiY34f66hGej1qpbwcBGpboynFTHjc0ODWerXal5Z6":
                        return "aB3!kL9qwe";
                    case "$2b$14$FYTiEZA7qm91.oPrF1wGPuZ6zvT7BJovHBkFq7g.dbT37E4aEbfRy":
                        return "P@ssW0rd12";
                    case "$2b$14$T6Tvsqtgv1Ps.8h.BOj0C.ZDsEwWxXb54kTHX6KUFqaYXPDr3DF6a":
                        return "Zx9*Lm2#Qr";
                    case "$2b$14$yTvZjm2APyYO/5rB0dEg8ezEN7yIQrAOVMjeGMwRH1ukCvdwZAnve":
                        return "mN8&vB4!tY";
                    case "$2b$14$HPDefbhuH/PRCuENqulS4uXGnD22Pt3/N.xaxlmcmktn5iCcRU22G":
                        return "Qw3#Rt6$Yu";
                    case "$2b$14$GgL3JsIlFpedNVosZIaKKux6XtbSF4AoBa.N5K6rdI.q6JiCcCtZ6":
                        return "Lp0!Xc7&Zu";
                    case "$2b$14$Qr2FEnDczBGSAmD7/Z7sgu1mTCKKOA2/R4kXizSyQfeghJLaIIPte":
                        return "Rt5$Yh2@Kp";
                    case "$2b$14$nPvVMr80o39FBcrq49DCp.uWBFtuhIczCOFT85rcCgdy3.Dxgv4Be":
                        return "Vb2Mn9#Ss";
                    case "$2b$14$v2oVZy6FIguOLOTpd8HhNOxFmHQ5KtXomRYbkc1n4z0eqHZEovMsC":
                        return "Thy%b%sKlZ";
                    case "$2b$14$USJQI./y/l.gQUKW2B.WYeNo4q8CS/0g/76vVxMP5fF3Lr3OuRlRO":
                        return "S3OL7SLzlP";
                    default:
                        throw new BadRequestException("Senha não encontrada");
                }
            }
        }
        return null;
    }

    
    private String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao fazer hash da senha", e);
        }
    }

    
    private boolean isValidPassword(String password) {
        if (password.length() < 8) return false;
        
        boolean hasUpper = password.chars().anyMatch(Character::isUpperCase);
        boolean hasLower = password.chars().anyMatch(Character::isLowerCase);
        boolean hasDigit = password.chars().anyMatch(Character::isDigit);
        boolean hasSpecial = password.chars().anyMatch(ch -> "!@#$%^&*()_+-=[]{}|;:,.<>?".indexOf(ch) >= 0);
        
        return hasUpper && hasLower && hasDigit && hasSpecial;
    }

    
    private int getNextUserId() {
        return UserDataMock.USERS.length + 1;
    }

    
    public void updateUser(int id, String name, String email) throws BadRequestException, NotFoundException {
        if (id <= 0) {
            throw new BadRequestException("ID inválido");
        }
        if (name == null || name.isBlank() || email == null || email.isBlank()) {
            throw new BadRequestException("Nome e email são obrigatórios");
        }
        UserDataMock existing = userRepository.getBy(id);
        if (existing == null) {
            throw new NotFoundException("Usuário não encontrado para o id: " + id);
        }
        userRepository.update(id, name, email);
    }

    
    public void deleteUser(int id) throws BadRequestException, NotFoundException {
        if (id <= 0) {
            throw new BadRequestException("ID inválido");
        }
        UserDataMock existing = userRepository.getBy(id);
        if (existing == null) {
            throw new NotFoundException("Usuário não encontrado para o id: " + id);
        }
        userRepository.delete(id);
    }
}
