package view;

public class ImageLogView {
    public void log(String operation, boolean isPass) {
        if(isPass) {
            System.out.println("Log:\n" + operation + " completed successfully!");
        } else {
            System.out.println("Log:\n" + "Invalid command entered");
        }

    }
}
