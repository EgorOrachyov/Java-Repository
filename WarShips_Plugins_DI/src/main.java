import WarShips.GameManager.BasicGameManager;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class main {
    public static void main(String... args) {

        ApplicationContext context = new ClassPathXmlApplicationContext("spring-config.xml");
        BasicGameManager game = context.getBean("warShipsGameManager", BasicGameManager.class);
        game.Run();

    }
}