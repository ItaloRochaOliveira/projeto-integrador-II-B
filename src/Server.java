import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import controller.AuthController;
import controller.PontoColetaController;
import server.ServerCRUD;
import service.PontoColetaService;
import service.AuthService;
import service.repository.UserRepositoryMock;
import service.repository.PontoColetaRepositoryMock;

public class Server {
    
    public static void main(String[] args) throws Exception {
        
        
        
        Properties props = new Properties();

        try (FileInputStream input = new FileInputStream("src/properties/config.properties")) {
            props.load(input);

            
            
            
        } catch (IOException e) {
            throw new Exception(e);
        };

        AuthController authController = new AuthController(
            new AuthService(
                new UserRepositoryMock()
            )
        );

        PontoColetaController pontoColetaController = new PontoColetaController(
            new PontoColetaService(
                new PontoColetaRepositoryMock()
            )
        );

        ServerCRUD serverCRUD = new ServerCRUD();

        
        serverCRUD.get("/pontos-coleta", pontoColetaController.get());
        serverCRUD.get("/pontos-coleta/buscar", pontoColetaController.getByTipoResiduo());
        serverCRUD.get("/pontos-coleta/tipos-residuos", pontoColetaController.getTiposResiduos());
        serverCRUD.post("/pontos-coleta", pontoColetaController.post());
        serverCRUD.put("/pontos-coleta", pontoColetaController.put());
        serverCRUD.delete("/pontos-coleta", pontoColetaController.delete());

        
        serverCRUD.post("/auth/login", authController.login());
        serverCRUD.post("/auth/signup", authController.signup());
        serverCRUD.put("/auth", authController.put());
        serverCRUD.delete("/auth", authController.delete());

        serverCRUD.start();
    }
};
