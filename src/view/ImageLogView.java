package view;


import java.io.IOException;

public class ImageLogView implements AppView {
    final Appendable out;
    public ImageLogView(Appendable out) {
        this.out = out;
    }
    public void log(String operation, boolean isPass) throws IOException {
        if(isPass) {
            this.out.append(String.format("Log: " + operation + " completed successfully!"));
        } else {
            this.out.append(String.format("Log: " + "Invalid command entered in " + operation));
        }

    }
}
