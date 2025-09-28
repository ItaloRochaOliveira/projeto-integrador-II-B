package db.mock;

import java.sql.Date;

import db.enitites.User;

public class UserDataMock {
    private User user;

    public User getUser() {
        return user;
    }

    public UserDataMock(int id, String name, String email, String passwordPlain, String passwordHash, Date created_at, Date updated_at, Date deleted_at, String role) {
        user = new User(id, name, email, passwordHash, created_at, updated_at, deleted_at, role);
    }

    public static final UserDataMock[] USERS = new UserDataMock[] {
        new UserDataMock(1, "Italo Rocha", "italo.rocha.de.oliveira@gmail.com", "aB3!kL9qwe", "$2b$14$wIhTyh6LhxQbiY34f66hGej1qpbwcBGpboynFTHjc0ODWerXal5Z6", new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()), null, "admin"), 
        new UserDataMock(2, "Ana Paula", "anapaulams14@gmail.com", "P@ssW0rd12", "$2b$14$FYTiEZA7qm91.oPrF1wGPuZ6zvT7BJovHBkFq7g.dbT37E4aEbfRy", new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()), null, "admin"), 
        new UserDataMock(3, "user3", "user3@example.com", "Zx9*Lm2#Qr", "$2b$14$T6Tvsqtgv1Ps.8h.BOj0C.ZDsEwWxXb54kTHX6KUFqaYXPDr3DF6a", new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()), null, "user"), 
        new UserDataMock(4, "user4", "user4@example.com", "mN8&vB4!tY", "$2b$14$yTvZjm2APyYO/5rB0dEg8ezEN7yIQrAOVMjeGMwRH1ukCvdwZAnve", new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()), null, "user"), 
        new UserDataMock(5, "user5", "user5@example.com", "Qw3#Rt6$Yu", "$2b$14$HPDefbhuH/PRCuENqulS4uXGnD22Pt3/N.xaxlmcmktn5iCcRU22G", new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()), null, "user"), 
        new UserDataMock(6, "user6", "user6@example.com", "Lp0!Xc7&Zu", "$2b$14$GgL3JsIlFpedNVosZIaKKux6XtbSF4AoBa.N5K6rdI.q6JiCcCtZ6", new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()), null, "user"), 
        new UserDataMock(7, "user7", "user7@example.com", "Rt5$Yh2@Kp", "$2b$14$Qr2FEnDczBGSAmD7/Z7sgu1mTCKKOA2/R4kXizSyQfeghJLaIIPte", new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()), null, "user"), 
        new UserDataMock(8, "user8", "user8@example.com", "Vb2*Mn9#Ss", "$2b$14$nPvVMr80o39FBcrq49DCp.uWBFtuhIczCOFT85rcCgdy3.Dxgv4Be", new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()), null, "user"), 
        new UserDataMock(9, "user9", "user9@example.com", "Thy%b%sKlZ", "$2b$14$v2oVZy6FIguOLOTpd8HhNOxFmHQ5KtXomRYbkc1n4z0eqHZEovMsC", new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()), null, "user"), 
        new UserDataMock(10, "user10", "user10@example.com", "S3OL7SLzlP", "$2b$14$USJQI./y/l.gQUKW2B.WYeNo4q8CS/0g/76vVxMP5fF3Lr3OuRlRO", new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()), null, "user"), 
    };

}