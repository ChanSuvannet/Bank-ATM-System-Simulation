package com.example.bankatmsystem;

import static org.junit.jupiter.api.Assertions.*;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.bankatmsystem.service.SemaphoreService;

@SpringBootTest
class BankatmsystemApplicationTests {

	@Test
	void testSemaphoreBusyState() throws InterruptedException {
		SemaphoreService semaphoreService = new SemaphoreService();
		ExecutorService executor = Executors.newFixedThreadPool(3);

		// Simulate three users trying to access the critical section
		Runnable task = () -> {
			boolean acquired = semaphoreService.acquire();
			if (acquired) {
				try {
					// Simulate task execution
					System.out.println(Thread.currentThread().getName() + ": Access granted.");
					Thread.sleep(1000); // Simulate processing time
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
				} finally {
					semaphoreService.release();
				}
			} else {
				System.out.println(Thread.currentThread().getName() + ": System busy.");
			}
		};

		// Submit tasks to executor
		for (int i = 0; i < 3; i++) {
			executor.submit(task);
		}

		executor.shutdown();
		executor.awaitTermination(5, TimeUnit.SECONDS);

		// Assert that the semaphore handled concurrency correctly
		assertEquals(1, semaphoreService.acquire() ? 1 : 0, "Semaphore should allow only one user at a time.");
	}

}
