import java.lang.Thread;

class Increment implements Runnable {

    public void run() {
        final long I=100;
        for (long i = 0; i < I; i++)
            System.out.println("A Thread numero " + Thread.currentThread().getId() + " deu print a: " + i);
    }

}


class Exercicio1 {
    public static void main(String[]  args){
        int num_threads = 10;
        Thread[] threads = new Thread[num_threads];
        for (int i = 0 ; i < num_threads; i++){
            threads[i] = new Thread(new Increment());
            threads[i].start();
        }

        for(int i = 0; i < num_threads ; i++){
            try{
                threads[i].join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        System.out.println("Fim");
    }
}