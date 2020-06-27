package com.chf.demo.androidex;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.PersistableBundle;
import android.os.RemoteException;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class BookManagerActivity extends AppCompatActivity {
    private final String TAG = "BookManagerActivity";
    private ServiceConnection connection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            IBookManager bookManager = IBookManager.Stub.asInterface(iBinder);
            try {
                List<Book> bookList = bookManager.getBookList();
                Log.i(TAG, "query bookList " + bookList.getClass().getCanonicalName());
                Book newBook = new Book(3, "Hello world");
                bookManager.addBook(newBook);
                for (int i =0 ; i < bookList.size() ; i++) {
                    Log.i(TAG, "print book " + bookList.get(i).toString());
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_manager);
        Intent intent = new Intent(this, BookManagerService.class);
        bindService(intent, connection, Context.BIND_AUTO_CREATE);
        Log.i(TAG, "onCreate bindService");
    }


    @Override
    protected void onDestroy() {
        unbindService(connection);
        super.onDestroy();
    }
}
