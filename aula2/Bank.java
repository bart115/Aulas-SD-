import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Bank {
    private Lock lock = new ReentrantLock();


    private static class Account {
        private int balance;
        Account(int balance) { this.balance = balance; }
        int balance() { return balance; }
        boolean deposit(int value) {
            balance += value;
            return true;
        }
        boolean withdraw(int value) {
            if (value > balance)
                return false;
            balance -= value;
            return true;
        }
    }

    // Bank slots and vector of accounts
    final private int slots;
    private Account[] av; 

    public Bank(int n) { // cria N contas com 0 euros cada
        slots=n;
        av=new Account[slots];
        for (int i=0; i<slots; i++) av[i]=new Account(0); //
    }

    // Account balance
    public int balance(int id) {
        lock.lock();
        try{
            if (id < 0 || id >= slots)
                return 0;
            return av[id].balance();
        } finally{
            lock.unlock();
        }

    }

    // Deposit
    public boolean deposit(int id, int value) {
        lock.lock();
        try{
            if (id < 0 || id >= slots)
                return false;
            return av[id].deposit(value);
        } finally{
            lock.unlock();
        }

    }

    // Withdraw; fails if no such account or insufficient balance
    public boolean withdraw(int id, int value) {
        lock.lock();
        try{
            if (id < 0 || id >= slots)
                return false;
            return av[id].withdraw(value);
        } finally{
            lock.unlock();
        }

    }


    public boolean transfer(int from, int to, int value){
        if(balance(from) < 0 || balance(from) < value || from < 0 || from >= slots || to >= slots )
            return false;
        lock.lock();
        try{
            withdraw(from,value);
            deposit(to,value);
            return true;
        } finally{
            lock.unlock();
        }
    }

    public int totalBalance() {
        lock.lock();
        try{
            int total = 0;
            for ( int i = 0; i < slots; i++){
                total += balance(i);
            }
            return total;
        } finally{
            lock.unlock();
        }

    }

}

