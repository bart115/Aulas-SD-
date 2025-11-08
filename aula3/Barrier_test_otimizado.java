import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.*;

public class Barrier_test_otimizado {
    private static class Threads_Entrar implements Runnable {
        private Barrier barrier;
        public Threads_Entrar(Barrier b){
            this.barrier = b; //numero de threads que podem entrar de cada vez
        }

        public void run() {
            try {
                barrier.await();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void main(String [] args) throws InterruptedException {
        System.out.println("executando código");
        int numero_threads = 10;
        Barrier barrier = new Barrier(5); //a barreira é criada aqui
        Thread[] threads = new Thread[numero_threads];
        for ( int i = 0; i<numero_threads; i++){
            threads[i] = new Thread ( new Threads_Entrar(barrier)); // A barreira é passada aqui
            threads[i].start();
        }
        for(int i = 0; i < numero_threads; i++){
            threads[i].join();
        }

    }

}
