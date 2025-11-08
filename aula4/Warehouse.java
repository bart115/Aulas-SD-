import java.util.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

class Warehouse {
    private Map<String, Product> map =  new HashMap<String, Product>();

    private ReentrantLock lock = new ReentrantLock();


    private class Product {
        int quantity = 0;

        private Condition waitforstock = lock.newCondition();
    }


    private Product get(String item) {
        this.lock.lock();

            Product p = map.get(item);
            if (p != null) return p; //se existir produto retorna produto
            p = new Product();
            map.put(item, p); //se não existir nenhum produto com esse nome de item cria um produto
            return p;

    }

    public void supply(String item, int quantity) {
        this.lock.lock();
        Product p = get(item); // coloca quantidade
        p.quantity += quantity;
        //notificar threads a aguardar stock do produto
        p.waitforstock.signalAll();
        this.lock.unlock();
    }
    //versão egoista
    public void consume(Set<String> items) throws InterruptedException { //Quero os items que estão na lista de items
    this.lock.lock();
    try {
        for (String s : items) {
            Product p = get(s);
            while (p.quantity == 0) {
                p.waitforstock.await();
            }
            p.quantity--;
        }
    } finally{
        this.lock.unlock();
    }


    }

    //versão cooperativa
    public void consumeCooperative(Set<String> items) throws InterruptedException { //Quero os items que estão na lista de items
        this.lock.lock();
        try {
            // verifica o stock de toodos e espera se faltar um
            String[] product_ids =  (String[]) items.toArray();

            for (int i = 0 ; i > items.toArray().length;i++) {

                Product p = get(product_ids[i]);
                while (p.quantity == 0) {
                    p.waitforstock.await();
                    i = 0;
                }
                p.quantity--;
            }

            for (String s : items) {
                Product p = get(s);
                p.quantity--;
            }
        } finally{
            this.lock.unlock();
        }
    }

}
