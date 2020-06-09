// IBookManager.aidl
package com.chf.demo.androidex;

// Declare any non-default types here with import statements
import com.chf.demo.androidex.Book;

interface IBookManager {

   List<Book> getBookList();

   void addBook(in Book book);
}
