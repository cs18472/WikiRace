import javafx.concurrent.Service;
import javafx.concurrent.Task;

import java.util.Random;

public class Racer extends Service<Void> {

    private String name;
    private Manager manager;
    private Random r = new Random();

    public Racer(String name, Manager manager) {
        this.name = name;
        this.manager = manager;
    }

    private void think() throws InterruptedException {
        Thread.sleep(r.nextInt(100) + 1);
    }

    @Override
    protected Task<Void> createTask() {
        return new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                try {
                    manager.initRacer(Racer.this);
                    while (!manager.isSolved()) {
                        if (Thread.interrupted()) {
                            throw new InterruptedException();
                        }
                        manager.request(Racer.this);
                        think();
                    }
                } catch (InterruptedException e) {
                    // Handle interruption
                } finally {
                    // Perform any necessary cleanup
                    System.out.println("Racer " + name + " has been canceled");
                }
                return null;
            }
        };
    }

    public String getName() {
        return name;
    }
}
