package frc.lib.logging;

import edu.wpi.first.networktables.BooleanPublisher;
import edu.wpi.first.networktables.BooleanSubscriber;
import edu.wpi.first.networktables.BooleanTopic;
import edu.wpi.first.util.datalog.BooleanLogEntry;
import edu.wpi.first.util.datalog.DataLog;

/** Sets up a boolean value in NetworkTables with the option to be logged */
public class BooleanEntry {

  private BooleanTopic topic;
  private BooleanPublisher pub;
  private BooleanSubscriber sub;
  private BooleanLogEntry log;
  boolean defaultValue = false;
  boolean logged = false;
  DataLog logInstance = LogManager.getCurrentLog();

  public BooleanEntry(String name) {
    this(name, false);
  }

  public BooleanEntry(String name, boolean value) {
    this(name, value, false);
  }

  public BooleanEntry(String name, boolean value, boolean logged) {
    this.defaultValue = value;
    this.logged = logged;
    topic = LogManager.getNTInstance().getBooleanTopic(name);
    log = new BooleanLogEntry(logInstance, name);
  }

  public void set(boolean value) {
    if (pub == null) pub = topic.publish();
    pub.set(value);
    if (LogManager.isCompetition() && logged) log.append(value);
  }

  public boolean get() {
    if (sub == null) sub = topic.subscribe(defaultValue);
    var currValue = sub.get();
    return currValue;
  }
}
