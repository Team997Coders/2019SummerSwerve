package frc.robot.util;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Queue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SpartanReporter {

  private Thread t;
  private File f;
  private FileWriter fw;
  private Lock lock;
  private Queue<String> writeQueue;
  private static SpartanReporter instance;

  public static SpartanReporter getInstance() {
    if (instance == null) instance = new SpartanReporter();
    return instance;
  }

  public static boolean hasInstance() {
    return instance != null;
  }

  private SpartanReporter() {
    lock = new ReentrantLock();
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH:mm.ss");  
    LocalDateTime now = LocalDateTime.now();
    String filename = "log-" + dtf.format(now) + ".txt";
    f = new File("~/spartanlogs/" + filename);
    try {
      f.createNewFile();
      fw = new FileWriter(f);
      t = new Thread(this::Runner);
      t.start();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void Runner() {
    while (true) {
      lock.lock();
      try {
        while (writeQueue.size() > 0) {
          fw.write(writeQueue.poll() + "\n");
          fw.flush();
        }
      } catch (Exception e) {
        e.printStackTrace();
      } finally {
        lock.unlock();
      }

      try {
        Thread.sleep(60);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  public void AddToQueue(String txt) {
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm.ss");  
    LocalDateTime now = LocalDateTime.now();
    String timestamp = "[" + dtf.format(now) + "]:";
    lock.lock();
    writeQueue.add(timestamp + txt);
    lock.unlock();
  }

  public void Close() {
    try {
      fw.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
    t.interrupt();
    instance = null;
  }

}