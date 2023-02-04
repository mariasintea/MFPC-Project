import controllers.LogInController;
import controllers.MainPageAdministratorController;
import controllers.MainPageClientController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import services.IService;
import views.LogInView;
import views.MainPageAdministratorView;
import views.MainPageClientView;

public class Client extends Application {

    public void start(Stage primaryStage) throws Exception {
        ApplicationContext factory = new ClassPathXmlApplicationContext("classpath:spring-client.xml");
        IService service = (IService) factory.getBean("service");

        FXMLLoader loaderMainPageAdministrator = new FXMLLoader();
        loaderMainPageAdministrator.setLocation(getClass().getResource("/fxml/MainPageAdministrator.fxml"));
        MainPageAdministratorController controllerAdministrator = new MainPageAdministratorController();
        controllerAdministrator.setUp(service);
        Parent rootAdministrator = loaderMainPageAdministrator.load();
        MainPageAdministratorView viewAdministrator = loaderMainPageAdministrator.getController();
        viewAdministrator.setUp(controllerAdministrator);

        FXMLLoader loaderMainPageClient = new FXMLLoader(getClass().getResource("/fxml/MainPageClient.fxml"));
        MainPageClientController controllerClient = new MainPageClientController();
        controllerClient.setUp(service);
        Parent rootSalesman = loaderMainPageClient.load();
        MainPageClientView viewClient = loaderMainPageClient.getController();
        viewClient.setUp(controllerClient);

        FXMLLoader loaderLogin = new FXMLLoader(getClass().getResource("/fxml/LogInPage.fxml"));
        Parent root = loaderLogin.load();
        LogInView viewLogIn = loaderLogin.getController();
        LogInController controllerLogIn = new LogInController();
        controllerLogIn.setUp(service);
        viewLogIn.setUp(controllerLogIn, rootAdministrator, rootSalesman);

        primaryStage.setTitle("Log In");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}
