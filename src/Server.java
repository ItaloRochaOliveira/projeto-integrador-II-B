import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import controller.UserController;
import server.ServerCRUD;
import service.UserService;
import service.repository.UserRepositoryMock;

public class Server {
    //to not up properties
    public static void main(String[] args) throws Exception {
        // String url;
        // String userDb;
        // String password;
        Properties props = new Properties();

        try (FileInputStream input = new FileInputStream("src/properties/config.properties")) {
            props.load(input);

            // url = props.getProperty("db.url");
            // userDb = props.getProperty("db.user");
            // password = props.getProperty("db.password");
        } catch (IOException e) {
            throw new Exception(e);
        };

        UserController userController = new UserController(
            new UserService(
                new UserRepositoryMock(
                )
            )
        );

        ServerCRUD serverCRUD = new ServerCRUD();

        serverCRUD.post("/usuarios/cadastro", userController.post());

        // serverCRUD.post("/usuarios/login", userController.post());

        serverCRUD.put("/usuarios", userController.put());

        serverCRUD.delete("/usuarios", userController.delete());

        serverCRUD.start();
    }
};
