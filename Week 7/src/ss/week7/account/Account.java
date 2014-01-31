package ss.week7.account;


public class Account {
        protected double balance = 0.0;

        public void transaction(double amount) {
                synchronized (this) {
                        while (balance + amount < -1000) {
                                try {
                                        wait();
                                }
                                catch (InterruptedException e) {
                                	e.printStackTrace();
                                }
                        }
                        balance = balance + amount;
                        notifyAll();
                }
        }

        public double getBalance() {
                return balance;
        }
}
