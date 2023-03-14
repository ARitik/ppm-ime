package view;

import java.io.IOException;

public interface AppView {
  public void log(String operation, boolean isPass) throws IOException;
}
