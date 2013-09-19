package com.diguage.books.concurrencycookbook.chapter1.recipe7;

import java.util.Date;

/**
 * 保存事件信息。
 * <p/>
 * Coder: D瓜哥，http://www.diguage.com/
 * Date: 2013-09-19
 * Time: 22:56
 */
public class Event {
    private Date date;
    private String event;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }
}
