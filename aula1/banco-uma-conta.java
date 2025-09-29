import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Bank {



    private static class Account {
        private Lock l = new ReentrantLock();
        private int balance;

        Account(int balance) {
            this.balance = balance;
        }

        int balance() {
            l.lock();
            try{
                return balance;
            }
            finally{
                l.unlock();
            }
        }

        boolean deposit(int value) {
            l.lock();
            try{
                balance += value;
                return true;
            }

            finally{
                l.unlock();
            }
        }
    }

    // Our single account, for now
    private Account savings = new Account(0);

    // Account balance

    public int balance_savings() {
            return savings.balance();
    }

    // Deposit
    boolean deposit_savings(int value) {
        return savings.deposit(value); // o return não pode estar dentro do lock
    }

}

class Depositar implements Runnable{
    private Bank bank = new Bank();
    private int iteracoes;
    private int valor_deposito;
    
    Depositar(Bank bank, int iter, int valor ){
        this.bank = bank;
        this.iteracoes = iter;
        this.valor_deposito = valor;
    }

    @Override
    public void run(){
        for( int i = 0; i < iteracoes; i++){
            bank.deposit_savings(valor_deposito);
        }
    }
}

class Exercicio2 {
    public static void main(String[] args) throws InterruptedException {
        final int I = 100; //numero de depositos de cada thread
        final int V = 10; // Valor depositado no banco
        final int N = 10; // Numero de threads que vão iterar

        Bank bank = new Bank(); // banco onde as threads vão depositar o valor
        Thread[] threads = new Thread[N];

        for (int i = 0; i < N; i++) {
            threads[i] = new Thread(new Depositar(bank,I,V));
        }
        for(int i = 0; i< N ; i++){
            threads[i].start();
        }
        for (int i = 0 ; i <N ; i++) {
            threads[i].join();
        }
        System.out.println("Balanço: " + bank.balance_savings());

    }
}
