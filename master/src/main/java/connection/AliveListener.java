package connection;

import java.util.ArrayList;

public class AliveListener extends Thread {
    private static final AliveListener aliveListener;
    private final ArrayList<QueryReceiver> queryReceivers;
    private final DatabaseController databaseController;

    static {
        aliveListener = new AliveListener();
    }
    public AliveListener() {
        this.queryReceivers = new ArrayList<>();
        this.databaseController = DatabaseController.getInstance();
    }

    public static AliveListener getInstance() {
        return aliveListener;
    }

    public synchronized void addQueryReceiver(QueryReceiver queryReceiver) {
        this.queryReceivers.add(queryReceiver);
    }
    @Override
    public void run() {
        while (true) {
            for (QueryReceiver queryReceiver : this.queryReceivers)
                queryReceiver.setQueryAlive(false);

            try {
                sleep(40000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            for (QueryReceiver queryReceiver : this.queryReceivers) {
                if (!queryReceiver.isQueryAlive()) {
                    queryReceiver.setIsDead(true);
                    databaseController.endSession(queryReceiver.getUser());
                }
            }
        }
    }
}
