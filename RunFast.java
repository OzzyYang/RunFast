import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;


import game.*;
import player.Player;

public class RunFast {
    public static void main(String[] args) throws Exception {
        AtomicInteger atomicLong = new AtomicInteger();
        ExecutorService executor = Executors.newSingleThreadExecutor();
        int k = 0;
        List<Callable<Integer>> tasks = new ArrayList<>();
        tasks.add(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                System.out.printf("任务开始时间%d\n", System.currentTimeMillis());
                int result = 0;
                for (int i = 0; i < 1000; i++) {
                    result++;
                }
                return result;
            }
        });
        tasks.add(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                System.out.printf("任务开始时间%d\n", System.currentTimeMillis());
                int result = 0;
                for (int i = 0; i < 10000; i++) {
                    result++;
                }
                return result;
            }
        });
        tasks.add(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                System.out.printf("任务开始时间%d\n", System.currentTimeMillis());
                Thread.sleep(300);
                return 3;
            }
        });
        tasks.add(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                System.out.printf("任务开始时间%d\n", System.currentTimeMillis());
                Thread.sleep(400);
                return 4;
            }
        });
        CompletableFuture<Integer> f = new CompletableFuture<>();
        executor.execute(() ->
        {
            int n = 3 + 2 + 6 + 4 + 8 + 4;
            f.complete(n);
        });
        executor.execute(() ->
        {
            int n = 1 + 2 + 3 + 4 + 5 + 6;
            f.complete(n);
        });

        System.out.println(f.get());
        //Game game = Game.getGame();
        //game.startGame();
    }
}