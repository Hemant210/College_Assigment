package Java.Assignment_1;
// DataTableApp.java
import java.util.*;
import java.io.*;
import java.awt.Point;   // <<-- Fix: import Point



interface Featurable {
    void setForeground(String color);
    void setBackground(String color);
    void setFont(String fontName, int size, boolean bold, boolean italic);
}

class TableException extends Exception {
    public TableException(String msg) { super(msg); }
}

class DataTable implements Featurable {
    private int rows, cols;
    private String[][] data;
    private String foreground = "black", background = "white", font = "Arial-12";

    public DataTable(int rows, int cols) throws TableException {
        checkLimit(rows, cols);
        this.rows = rows; this.cols = cols;
        data = new String[rows][cols];
    }

    private void checkLimit(int r, int c) throws TableException {
        if (r > 200 || c > 200) throw new TableException("Rows or columns cannot exceed 200");
        if (r < 0 || c < 0) throw new TableException("Rows and columns must be non-negative");
    }

    private void checkBounds(int r, int c) {
        if (r < 0 || r >= rows || c < 0 || c >= cols)
            throw new IndexOutOfBoundsException("Cell index out of bounds: (" + r + "," + c + ")");
    }

    public void setCell(int r, int c, String value) { 
        checkBounds(r,c);
        data[r][c] = value; 
    }
    public String getCell(int r, int c) { 
        checkBounds(r,c);
        return data[r][c]; 
    }

    public void insertRow(int atIndex) {
        if (atIndex < 0 || atIndex > rows) throw new IndexOutOfBoundsException("insertRow index out of range");
        String[][] nd = new String[rows+1][cols];
        for (int i=0;i<atIndex;i++) nd[i] = data[i];
        nd[atIndex] = new String[cols]; // empty row
        for (int i=atIndex;i<rows;i++) nd[i+1] = data[i];
        data = nd; rows++;
    }

    public void deleteRow(int idx) {
        if (idx < 0 || idx >= rows) throw new IndexOutOfBoundsException("deleteRow index out of range");
        String[][] nd = new String[rows-1][cols];
        for (int i=0, j=0;i<rows;i++){
            if (i==idx) continue;
            nd[j++] = data[i];
        }
        data = nd; rows--;
    }

    public void insertCol(int atIndex) {
        if (atIndex < 0 || atIndex > cols) throw new IndexOutOfBoundsException("insertCol index out of range");
        String[][] nd = new String[rows][cols+1];
        for (int r=0;r<rows;r++) {
            for (int c=0;c<atIndex;c++) nd[r][c] = data[r][c];
            nd[r][atIndex] = null;
            for (int c=atIndex;c<cols;c++) nd[r][c+1] = data[r][c];
        }
        data = nd; cols++;
    }

    public void deleteCol(int idx) {
        if (idx < 0 || idx >= cols) throw new IndexOutOfBoundsException("deleteCol index out of range");
        String[][] nd = new String[rows][cols-1];
        for (int r=0;r<rows;r++){
            for (int c=0, j=0;c<cols;c++){
                if (c==idx) continue;
                nd[r][j++] = data[r][c];
            }
        }
        data = nd; cols--;
    }

    public void resize(int newRows, int newCols) throws TableException {
        checkLimit(newRows, newCols);
        String[][] nd = new String[newRows][newCols];
        for (int r=0;r<Math.min(rows, newRows);r++)
            for (int c=0;c<Math.min(cols, newCols);c++)
                nd[r][c] = data[r][c];
        rows = newRows; cols = newCols; data = nd;
    }

    public void populateFromHashtable(Hashtable<Point, String> ht) {
        for (Map.Entry<Point,String> e : ht.entrySet()) {
            Point p = e.getKey();
            if (p.x >= 0 && p.x < rows && p.y >= 0 && p.y < cols) data[p.x][p.y] = e.getValue();
            else System.out.println("Skipping out-of-bounds entry: " + p);
        }
    }

    public void printTable() {
        System.out.println("Table ("+rows+"x"+cols+") Font:"+font+" FG:"+foreground+" BG:"+background);
        for (int r=0;r<rows;r++){
            for (int c=0;c<cols;c++){
                String v = data[r][c];
                System.out.print((v==null? "": v) + "\t");
            }
            System.out.println();
        }
    }

    // Featurable
    public void setForeground(String color){ this.foreground = color; }
    public void setBackground(String color){ this.background = color; }
    public void setFont(String fontName, int size, boolean bold, boolean italic){
        this.font = fontName + "-" + size + (bold? "-B": "") + (italic? "-I":"");
    }
}

public class DataTableApp {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        DataTable table = null;
        try {
            System.out.print("Create table -> rows: ");
            int r = Integer.parseInt(sc.nextLine());
            System.out.print("cols: ");
            int c = Integer.parseInt(sc.nextLine());
            table = new DataTable(r, c);
        } catch (Exception ex) {
            System.out.println("Error creating table: " + ex.getMessage());
            return;
        }

        while (true) {
            System.out.println("\nOptions: print | set r c value | get r c | insertRow idx | deleteRow idx | insertCol idx | deleteCol idx | resize r c | theme | populate | exit");
            System.out.print("> ");
            String line = sc.nextLine().trim();
            if (line.equalsIgnoreCase("exit")) break;
            try {
                if (line.equalsIgnoreCase("print")) table.printTable();
                else if (line.startsWith("set ")) {
                    String[] parts = line.split(" ",4);
                    if (parts.length < 3) { System.out.println("Usage: set r c value"); continue; }
                    int rr = Integer.parseInt(parts[1]);
                    int cc = Integer.parseInt(parts[2]);
                    String val = parts.length>=4? parts[3] : "";
                    table.setCell(rr, cc, val);
                } else if (line.startsWith("get ")) {
                    String[] p = line.split(" ");
                    if (p.length < 3) { System.out.println("Usage: get r c"); continue; }
                    System.out.println(table.getCell(Integer.parseInt(p[1]), Integer.parseInt(p[2])));
                } else if (line.startsWith("insertRow ")) table.insertRow(Integer.parseInt(line.split(" ")[1]));
                else if (line.startsWith("deleteRow ")) table.deleteRow(Integer.parseInt(line.split(" ")[1]));
                else if (line.startsWith("insertCol ")) table.insertCol(Integer.parseInt(line.split(" ")[1]));
                else if (line.startsWith("deleteCol ")) table.deleteCol(Integer.parseInt(line.split(" ")[1]));
                else if (line.startsWith("resize ")) {
                    String[] p = line.split(" ");
                    table.resize(Integer.parseInt(p[1]), Integer.parseInt(p[2]));
                } else if (line.startsWith("theme")) {
                    System.out.print("fg: "); String fg = sc.nextLine();
                    System.out.print("bg: "); String bg = sc.nextLine();
                    System.out.print("font name: "); String fn = sc.nextLine();
                    System.out.print("size: "); int sz = Integer.parseInt(sc.nextLine());
                    System.out.print("bold? (y/n): "); boolean b = sc.nextLine().startsWith("y");
                    System.out.print("italic? (y/n): "); boolean it = sc.nextLine().startsWith("y");
                    table.setForeground(fg); table.setBackground(bg); table.setFont(fn, sz, b, it);
                } else if (line.startsWith("populate")) {
                    Hashtable<Point,String> ht = new Hashtable<>();
                    System.out.println("Enter pairs like r c value (empty line to stop):");
                    while (true) {
                        String ln = sc.nextLine().trim();
                        if (ln.isEmpty()) break;
                        String[] parts = ln.split(" ",3);
                        int rr = Integer.parseInt(parts[0]), cc = Integer.parseInt(parts[1]);
                        String val = parts.length>2? parts[2] : "";
                        ht.put(new Point(rr,cc), val);
                    }
                    table.populateFromHashtable(ht);
                } else System.out.println("Unknown command");
            } catch (Exception ex) {
                System.out.println("Error: " + ex.getMessage());
            }
        }
        System.out.println("Exiting DataTableApp.");
    }
}
