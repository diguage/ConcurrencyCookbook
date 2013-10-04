package com.diguage.books.concurrencycookbook.chapter2.recipe2;

/**
 * 同步对象示例：售票处二
 * <p/>
 * Coder: D瓜哥，http://www.diguage.com/
 * Date: 2013-10-05
 * Time: 00:21
 */
public class TicketOffice2 implements Runnable {
    private Cinema cinema;

    public TicketOffice2(Cinema cinema) {
        this.cinema = cinema;
    }

    @Override
    public void run() {
        cinema.sellTickets2(2);
        cinema.sellTickets2(4);
        cinema.sellTickets1(2);
        cinema.sellTickets1(1);
        cinema.returnTickets2(2);
        cinema.sellTickets1(3);
        cinema.sellTickets2(2);
        cinema.sellTickets1(2);
    }
}
