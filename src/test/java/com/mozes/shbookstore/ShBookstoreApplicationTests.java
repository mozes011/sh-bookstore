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
	void testSingleBookInsertion(){
		Book testBook = new Book("The Old Man and The Sea" ,"SomeName");
		bookService.addBook(testBook);
		assertEquals(1 ,bookService.getAllBooks().size());
		bookService.purchaseBook(testBook);
	}

	@Test
	void testBookServiceAddConcurent(){
		Book testBook = new Book("The Old Man and The Sea" ,"SomeName");
		ThreadPoolExecutor executorService = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);
		for (int i = 0; i < NUMBER_OF_ACTIONS; i++) {
			final int tmp = i;
			executorService.submit(() -> {
				bookService.addBook(testBook);
				System.out.println("add action "+tmp);
			});

		}
		try {
			executorService.awaitTermination(10L, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("quantity of books in add test "+bookService.getAllBooks().get(0).quantity);
		assertEquals(NUMBER_OF_ACTIONS,bookService.getBookQuantity(testBook));


	}

	@Test
	void testBookServiceRemoveConcurent(){
		Book testBook = new Book("The bla" ,"SomeName");
		for (int i = 0; i < NUMBER_OF_ACTIONS; i++) {
			bookService.addBook(testBook);
		}

		bookService.purchaseBook(testBook); //remove one extra so counter of successful perchuse will off by 1
		AtomicInteger countr= new AtomicInteger(0);
		ThreadPoolExecutor executorService = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);
		for (int i = 0; i < NUMBER_OF_ACTIONS; i++) {
			final int tmp=i;
			executorService.submit(() -> {
				if(bookService.purchaseBook(testBook)!=null){
					countr.incrementAndGet();
				}
				System.out.println("perchuse action "+tmp);
			});

		}
		try {
			executorService.awaitTermination(15L, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		assertEquals(NUMBER_OF_ACTIONS - 1,countr.get());

	}



}
