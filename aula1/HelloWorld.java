public class HelloWorld implements Runnable {

    public void run() {
        System.out.println("Hello, World from thread " + Thread.currentThread().getId() + "from process " + ProcessHandle.current().pid() + "which is runiing within a runnable object");
    }
}

class Main{
    public static void main(String[] args) {
        System.out.println("Hello, World!");

        Thread thread1 = new Thread("Hello, World from thread " + Thread.currentThread().getId() + "from process " + ProcessHandle.current().pid());
        thread1.start();

        try{
            thread1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}