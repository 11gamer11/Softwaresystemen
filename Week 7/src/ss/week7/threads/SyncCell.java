package ss.week7.threads;

public class SyncCell implements IntCell{
    private int value;
    private boolean setValue;

    public synchronized void setValue(int val) {

    		while (setValue){
                    try {
                            wait();
                    }
                    catch (InterruptedException e){
                    	e.printStackTrace();
                    }
            }
            this.value = val;
            setValue = true;
            notifyAll();
    }

    public synchronized int getValue() {
            while (!setValue){
                    try {
                            wait();
                    }
                    catch (InterruptedException e){
                    	e.printStackTrace();
                    }
            }
            setValue = false;
            notifyAll();
            return value;
    }
}
