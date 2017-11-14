package makeTable;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Printer {
    PrintStream out;
    File file;
    public Printer(String filename) {
        file = new File(filename);
        try {
            out = new PrintStream(file);
            out.println(init(filename));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    private int nameCount = 0;
    private int elementCount = 0;

    private String init(String filename) {
        Matcher matcher = Pattern.compile("\\.java$").matcher(filename);
        if (matcher.find()) return "public class " +
                filename.substring(0, matcher.start())
                + " {\n\n\tpublic static int[] get(int shape) {\n" +
                "\t\treturn map.get(shape);\n" +
                "\t}\n\n\tprivate static final HashMap<Integer, int[]> map = new HashMap<>();\n" +
                "\n" +
                "\tstatic {\n" +
                "\t\tinitialize();\n" +
                "\t}";
        else throw new IllegalArgumentException("not an java file");
    }

    public String newMethod() {
        if (elementCount == 1) return "\n\tprivate static void init" + nameCount++ + "() {";
        return "\t}\n\n\tprivate static void init" + nameCount++ + "() {";
    }

    public Printer println(CharSequence string) {
        if (elementCount++ % 1000 == 0) out.println(newMethod());
        out.println("\t\t" + string + ';');
        return this;
    }
    public void end() {
        out.println("\t}\n\n\tprivate static void initialize(){");
        for (int i = 0; i < nameCount; i++)
            out.println("\t\tinit" + i + "();");
        out.println("\t}\n}");
        out.close();
//        try {
//            Scanner scanner = new Scanner(file);
//            Matcher matcher = Pattern.compile("//To Do init").matcher("");
//            while (scanner.hasNext()) {
//                String line = scanner.nextLine();
//
//            }
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//            System.exit(2);
//        }

    }

    public static void main(String[] args) {
        new Printer("Table.java")
                .println("map.put(1,2)")
                .println("map.put(2,3)")
                .println("map.put(3,4)")
                .println("map.put(3,4)")
                .println("map.put(3,4)")
                .println("map.put(3,4)")
                .println("map.put(3,4)")
                .println("map.put(3,4)")
                .println("map.put(3,4)")
                .println("map.put(3,4)")
                .println("map.put(3,4)")
                .println("map.put(3,4)")
                .println("map.put(3,4)")
                .println("map.put(3,4)")
                .println("map.put(3,4)")
                .println("map.put(3,4)")
                .println("map.put(3,4)")
                .println("map.put(3,4)")
                .println("map.put(3,4)")
                .end();
    }
}
