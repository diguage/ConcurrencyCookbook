package com.diguage.books.concurrencycookbook.chapter2.recipe2;

/**
 * 同步对象示例：主类
 * <p/>
 * Coder: D瓜哥，http://www.diguage.com/
 * Date: 2013-10-05
 * Time: 00:22
 */
public class Main {
    public static void main(String[] args) {
        Cinema cinema = new Cinema();
        TicketOffice1 ticketOffice1 = new TicketOffice1(cinema);
        Thread thread1 = new Thread(ticketOffice1);

        TicketOffice2 ticketOffice2 = new TicketOffice2(cinema);
        Thread thread2 = new Thread(ticketOffice2);

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.printf("Room 1 Vacancies: %d\n",
                cinema.getVacanciesCinema1());
        System.out.printf("Room 2 Vacancies: %d\n",
                cinema.getVacanciesCinema2());
    }
}
