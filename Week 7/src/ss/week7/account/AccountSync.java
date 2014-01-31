package ss.week7.account;

public class AccountSync {
    public static void main(String[] args) throws Throwable{
            Account account = new Account();
            MyThread mythread1 = new MyThread(-100,15,account);
            MyThread mythread2 = new MyThread(100,15,account);

            mythread1.start();
            mythread2.start();
            mythread1.join();
            mythread2.join();
            System.out.println(account.getBalance());

    }
}