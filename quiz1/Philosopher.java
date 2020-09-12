import java.util.concurrent.Semaphore;

public class Philosopher implements Runnable {

    //each philosopher has access to two forks
    private Object leftFork;
    private Object rightFork;

    //declare waiter as a semaphore that handles 1
    private Semaphore waiter;

    //initialize 
    public Philosopher(Object leftFork, Object rightFork, Semaphore waiter) {
        this.leftFork = leftFork;
        this.rightFork = rightFork;
        this.waiter = waiter;
    }

    //each philosopher thinks, picks up the left and right fork respectively, eats, then puts down the right and left fork respectively.
    private void doAction(String action) throws InterruptedException {
        System.out.println(Thread.currentThread().getName() + " " + action);
        Thread.sleep((int) (Math.random() * 100));
    }

    @Override
    public void run(){
        try {
            //first the philosopher must think
            doAction(": Thinking!");
            waiter.acquire();
            doAction(": Excuse me sir, may I please touch the forks so I may eat?");
            //--CRITICAL SECTION--
            //only allow one philosopher to access forks at a time to avoid deadlock situation
            //this is solution is called the "arbitrator". synchronized block acts as mutex on forks as a resource.
            //we reduce parallelism for the trade off of guaranteed no deadlocks. 
            synchronized(leftFork) {
                doAction(": Picked up the left fork!");
                synchronized(rightFork) {
                    doAction(": Picked up the right fork! Now he can eat.");
                    doAction(": Put down the right fork!");
                }
            }
            doAction(": Put down the left fork! Now he can think.");
        } catch (Exception e) {
            //TODO: handle exception
            Thread.currentThread().interrupt();
            return;
        } finally {
            waiter.release();
        }
    }
}