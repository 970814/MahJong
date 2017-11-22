package data;

public class Test {
    public static void main(String[] args) throws InterruptedException {
        DataModel dataModel = new DataModel();
        Painter painter = new Painter(dataModel);
        Driver driver = new Driver(dataModel);
        Window frame = new Window(painter, driver);
        frame.setVisible(true);
    }
}
