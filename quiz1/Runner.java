import java.util.concurrent.Semaphore;

public class Runner {
    public static void main(String [] args) throws Exception {

        //initialize arrays of philosophers and forks to number of choice
        //initizalize mutex
        Philosopher[] philosophers = new Philosopher[5];
        Object[] forks = new Object[philosophers.length];
        Semaphore waiter = new Semaphore(1);

        //instantiate forks as objects
        for(int i = 0; i < forks.length; i++) {
            forks[i] = new Object();
        }

        //assign forks to philosophers and run threads
        for(int i = 0; i < philosophers.length; i++) {
            Object leftFork = forks[i];
            Object rightFork = forks[(i + 1) % forks.length];

            philosophers[i] = new Philosopher(leftFork, rightFork, waiter);

            //assign name to Philosopher and start thread
            Thread t = new Thread(philosophers[i], "Philosopher " + (i + 1));
            t.start();
        }
    }
}