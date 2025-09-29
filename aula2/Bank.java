import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Bank {


    private static class Account {
        private Lock lock = new ReentrantLock();
        private int balance;

        Account(int balance) {
            this.balance = balance;
        }

        int balance() {
            lock.lock();
            try {
                return balance;
            } finally {
                lock.unlock();
            }
        }

        boolean deposit(int value) {
            lock.lock();
            try {
                balance += value;
                return true;
            } finally {
                lock.unlock();
            }

        }

        boolean withdraw(int value) {
            if (value > balance)
                return false;
            lock.lock();
            try {
                balance -= value;
                return true;
            } finally {
                lock.unlock();
            }
        }
    }

    // Bank slots and vector of accounts
    final private int slots;
    private Account[] av;

    public Bank(int n) { // cria N contas com 0 euros cada
        slots = n;
        av = new Account[slots];
        for (int i = 0; i < slots; i++) av[i] = new Account(0); //
    }

    // Account balance
    public int balance(int id) {
        if (id < 0 || id >= slots)
            return 0;
        this.av[id].lock.lock();
        try {
            return av[id].balance();
        } finally {
            this.av[id].lock.unlock();
        }

    }

    // Deposit
    public boolean deposit(int id, int value) {
        if (id < 0 || id >= slots)
            return false;
        this.av[id].lock.lock();
        try {
            return av[id].deposit(value);
        } finally {
            this.av[id].lock.unlock();
        }
    }

    // Withdraw; fails if no such account or insufficient balance
    public boolean withdraw(int id, int value) {
        if (id < 0 || id >= slots)
            return false;
        this.av[id].lock.lock();
        try {
            return av[id].withdraw(value);
        } finally {
            this.av[id].lock.unlock();
        }

    }


    public boolean transfer(int from, int to, int value) {
        if (balance(from) < 0 || balance(from) < value || from < 0 || from >= slots || to >= slots)
            return false;

        if (from < to) {
            this.av[from].lock.lock();
            this.av[to].lock.lock();

            try {
                withdraw(from, value);
                deposit(to, value);
                return true;
            } finally {
                this.av[from].lock.unlock();
                this.av[to].lock.unlock();
            }
        } else {
            this.av[to].lock.lock();
            this.av[from].lock.lock();

            try {
                withdraw(from, value);
                deposit(to, value);
                return true;
            } finally {
                this.av[to].lock.unlock();
                this.av[from].lock.unlock();

            }
        }
    }


    public int totalBalance() {
        int total = 0;
        for (int i = 0 ; i < slots; i++){
            this.av[i].lock.lock();
        }
            for ( int i = 0; i < slots; i++){
                total += balance(i);
                this.av[i].lock.unlock();
            }
            return total;


    }

}

