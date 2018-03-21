
import javafx.event.Event;
import javafx.event.EventType;

/**
 *
 * @author Ana Gorohovschi
 * This class is used to notify GUICore class about the end of the animation
 * from the Animation class
 */
public class GameOverEvent extends Event {
    public static final EventType<GameOverEvent> gameOver = new EventType(ANY);

    public GameOverEvent()
    {
        this(gameOver);
    }
    
    private GameOverEvent(EventType<? extends Event> eventType)
    {
        super(eventType);
    }
}
