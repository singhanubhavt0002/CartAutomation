package ZetaDataProviders;

import org.testng.annotations.Test;

import java.util.concurrent.CountDownLatch;

public class dummy {

    //Hi Bharat sir i have checked and got where the issue was and fixed that issue later on different class, here i am sharing the same code
    // as this code i have written that day. Thanks for helping me in debugging and finding the issue.


    private static String username="ashutosh";
    private static CountDownLatch latch= new CountDownLatch(3);
    private static CountDownLatch finishlatch = new CountDownLatch(1);

    @Test(threadPoolSize = 3, invocationCount = 1)
    public void test1() throws InterruptedException {
        Thread.sleep(600);
        System.out.println("Test 1 Finished "+username);
        latch.countDown();
        finishlatch.await();
        System.out.println("Test 1 Resumed"+username);
    }

    @Test(threadPoolSize = 3,invocationCount = 1)
    public void test2() throws InterruptedException {
        Thread.sleep(1200);
        System.out.println("Test 2 Finished"+username);
        latch.countDown();
        finishlatch.await();
        System.out.println("Test 2 Resumed"+username);

    }
    @Test(threadPoolSize = 3,invocationCount = 1)
    public void test3() throws InterruptedException {
        Thread.sleep(1800);
        System.out.println("Test 3 Finished"+username);
        latch.countDown();
        finishlatch.await();
        System.out.println("Test 3 Resumed"+username);
    }

    @Test()
    public void UpdateUserNameTest() throws InterruptedException {
        System.out.println("Common method waiting");
        latch.await();
        username = "anubhav";
        System.out.println("Updated username: "+username);
        finishlatch.countDown();
    }


    //    private static String username = "ashutosh";
//    private static CountDownLatch latch = new CountDownLatch(1); // Wait for UpdateUserNameTest
//    private static CountDownLatch finishLatch = new CountDownLatch(3); // Wait for all tests to finish
//
//    @Test
//    public void UpdateUserNameTest() throws InterruptedException {
//        System.out.println("Updating username...");
//        Thread.sleep(500); // Optional delay to simulate processing
//        username = "anubhav";  // Update username first
//        latch.countDown();      // Release other tests
//        System.out.println("Updated username: " + username);
//    }
//
//    @Test(threadPoolSize = 3, invocationCount = 1)
//    public void test1() throws InterruptedException {
//        latch.await(); // Wait until username is updated
//        Thread.sleep(600); // Original delay
//        System.out.println("Test 1 Finished " + username);
//        finishLatch.countDown(); // Signal finished
//        finishLatch.await();     // Wait for all tests to finish
//        System.out.println("Test 1 Resumed " + username);
//    }
//
//    @Test(threadPoolSize = 3, invocationCount = 1)
//    public void test2() throws InterruptedException {
//        latch.await();
//        Thread.sleep(1200); // Original delay
//        System.out.println("Test 2 Finished " + username);
//        finishLatch.countDown();
//        finishLatch.await();
//        System.out.println("Test 2 Resumed " + username);
//    }
//
//    @Test(threadPoolSize = 3, invocationCount = 1)
//    public void test3() throws InterruptedException {
//        latch.await();
//        Thread.sleep(1800); // Original delay
//        System.out.println("Test 3 Finished " + username);
//        finishLatch.countDown();
//        finishLatch.await();
//        System.out.println("Test 3 Resumed " + username);
//    }
}
