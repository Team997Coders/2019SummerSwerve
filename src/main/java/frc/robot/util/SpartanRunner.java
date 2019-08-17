package frc.robot.util;

import java.util.ArrayList;

public class SpartanRunner {

  private boolean halt = false;
  private int loopFrequency;
  private double lastRun = 0;
  private Thread t;
  private ArrayList<SpartanAction> actions;

  public SpartanRunner(int loopFrequency) {
    this.loopFrequency = loopFrequency;
    actions = new ArrayList<SpartanAction>();

    t = new Thread(this::Run);
    t.start();
  }

  private void Run() {
    lastRun = System.currentTimeMillis();
    while (!halt) {
      if (lastRun + loopFrequency < System.currentTimeMillis()) {
        lastRun = System.currentTimeMillis();
        if (actions.size() > 0) {
          actions.forEach(x -> {
            if (!x.hasInit) {
              x.init();
              x.hasInit = true;
            }
          });
          actions.forEach(x -> x.exec());
        }
      }

      try { Thread.sleep((long)(loopFrequency / 3)); } catch (Exception e) { e.printStackTrace(); }
    }

    actions.forEach(x -> x.interrupt());
    actions.clear();
  }

  public void Stop() {
    halt = true;
  }

}