package bandeau;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Classe utilitaire pour représenter la classe-association UML
 */
class ScenarioElement  {

    Effect effect;
    int repeats;

    ScenarioElement(Effect e, int r) {
        effect = e;
        repeats = r;
    }
}
/**
 * Un scenario mémorise une liste d'effets, et le nombre de repetitions pour chaque effet
 * Un scenario sait se jouer sur un bandeau.
 */
public class Scenario {

    private final List<ScenarioElement> myElements = new LinkedList<>();
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    /**
     * Ajouter un effect au scenario.
     *
     * @param e l'effet à ajouter
     * @param repeats le nombre de répétitions pour cet effet
     */
    public void addEffect(Effect e, int repeats) {
        myElements.add(new ScenarioElement(e, repeats));
    }

    /**
     * Jouer ce scenario sur un bandeau
     *
     * @param b le bandeau ou s'afficher.
     */
    public void playOn(Bandeau b) {
        Thread t = new Thread () { 
            @Override
            public void run () {
                lock.readLock().lock();
                for(ScenarioElement element : myElements) {
                    for (int repeats = 0; repeats < element.repeats; repeats++) {
                    element.effect.playOn(b);
                    }
                }  
            }
        }; 
        t.start();
    }
}
