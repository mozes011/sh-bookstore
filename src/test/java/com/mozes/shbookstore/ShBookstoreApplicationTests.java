package com.mozes.shbookstore;

import com.mozes.shbookstore.models.Book;
import com.mozes.shbookstore.services.BookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
class ShBookstoreApplicationTests {

	@Autowired
	private BookService bookService;

	final int NUMBER_OF_ACTIONS=200;
	@Test
	void contextLoads() {
	}


	@Test
	void testBookServiceAddConcurent(){
		Book testBook = new Book("The Old Man and The Sea" ,"SomeName");
		ThreadPoolExecutor executorService = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);
		for (int i = 0; i < NUMBER_OF_ACTIONS; i++) {
			final int tmp = i;
			executorService.submit(() -> {

				bookService.addBook(testBook);
				System.out.println("Task Submitted"+tmp);
			});

		}
		try {
			executorService.awaitTermination(5L, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		assertEquals(NUMBER_OF_ACTIONS,bookService.getBookQuantity(testBook));


	}

	@Test
	void testBookServiceRemoveConcurent(){
		Book testBook = new Book("The Old Man and The Sea" ,"SomeName");
		for (int i = 0; i < NUMBER_OF_ACTIONS; i++) {
			bookService.addBook(testBook);
		}

		bookService.removeBook(testBook);

		AtomicInteger countr= new AtomicInteger(0);
		ThreadPoolExecutor executorService = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);
		for (int i = 0; i < NUMBER_OF_ACTIONS; i++) {
			final int tmp=i;
			executorService.submit(() -> {
				if(bookService.removeBook(testBook)){
					countr.incrementAndGet();
				}
				System.out.println("Task Submitted"+tmp);
			});

		}
		try {
			executorService.awaitTermination(5L, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		assertEquals(NUMBER_OF_ACTIONS-1,countr.get());

	}



}
