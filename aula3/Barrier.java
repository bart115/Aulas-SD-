import java.util.concurrent.locks.*;

public class Barrier {
    private int epoch;
    private final int capacidade;
    private int waiters; // quantas threads estão lá
    private ReentrantLock lock;
    private Condition cond;

    Barrier(int N){
        this.epoch = 0; // é a fase que está a ser utilizada
        this.capacidade = N;
        this.waiters = 0;
        this.lock = new ReentrantLock();
        this.cond = lock.newCondition();

    }
    public void await() throws InterruptedException{
        this.lock.lock();
        try{
            this.waiters++;
            int e = this.epoch; // inicialmente começa com a fase 0
            if(this.waiters < this.capacidade) { //ou a thread fica "presa"
                while (e == this.epoch) { //isto é feito para sabermos em que fase está ou seja se estivermos à espera dos primeiros 5 primeiros ou dos segundos, isso impede que outras threads que não são desta fase entrem no while sem
                    System.out.println("Thread-" +  Thread.currentThread().getId() + " Está à espera");
                    this.cond.await(); // este é o await do java (não é esta função)
                    System.out.println("Thread-" +  Thread.currentThread().getId() + " A sair do  while");
                }
            }
            else { // ou a thread acorda as threads presas
                System.out.println("Thread-" +  Thread.currentThread().getId() + " Acordou os outros");
                this.cond.signalAll();
                this.waiters = 0;
                this.epoch++;
            }
        } finally {
            this.lock.unlock();
        }
    }
}


