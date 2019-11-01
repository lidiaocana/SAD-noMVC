import java.io.*;
import java.util.*;

public class EditableBufferedReader extends BufferedReader {
    // Variables
    private Line line;

    // Constructor
    public EditableBufferedReader(Reader in) {
        super(in);
        line = new Line();
    }
    

    // Metodes
    // setRaw: passa la consola de mode cooked a mode raw.
    public void setRaw() {
        String[] cmd = { "/bin/sh", "-c", "stty -echo raw </dev/tty" }; // Comana per canviar la consola a mode Raw
        Process p;
        try {
            p = Runtime.getRuntime().exec(cmd);
            if (p.waitFor() == 1) {
                p.destroy();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // unsetRaw: passa la consola de mode raw a mode cooked.
    public void unsetRaw() {
        String[] cmd = { "/bin/sh", "-c", "stty echo cooked </dev/tty" }; // Comana per canviar la consola a mode Cooked
        Process p;
        try {
            p = Runtime.getRuntime().exec(cmd);
            if (p.waitFor() == 1) {
                p.destroy();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // read: llegeix el seguent caracter o la seguent tecla de cursor.
    public int read() {
        int numRead = 6666;
        try {
            int n = super.read();
            if (n != Dictionary.ESC) { // Si entra es un caracter normal o borrar(Backspace)
                if (n == Dictionary.BS) {
                    numRead = Dictionary.xBS;
                } else {
                    numRead = n;
                }
            } else { // Si entra detectem que es una sequencia de ESC
                n = super.read(); // El CSI el podem obviar i fer condicions del qe hi ha darrere de ESC+CSI
                n = super.read();
                switch (n) {
                case Dictionary.DEL1:
                    int n1 = super.read();
                    if (n1 == Dictionary.DEL2) {
                        numRead = Dictionary.xDEL;
                    }
                    break;
                case Dictionary.INSERT1:
                    numRead = Dictionary.xINSERT;
                    break;
                case Dictionary.RIGHT:
                    numRead = Dictionary.xRIGHT;
                    break;
                case Dictionary.LEFT:
                    numRead = Dictionary.xLEFT;
                    break;
                case Dictionary.HOME:
                    numRead = Dictionary.xHOME;
                    break;
                case Dictionary.END:
                    numRead = Dictionary.xEND;
                    break;
                default:
                    System.out.println("Invalid Input!");
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return numRead;
    }

    public String readLine() {
        this.setRaw();
        int llegit = this.read();
        while (llegit != Dictionary.ENTER_1 && llegit != Dictionary.ENTER_2) {
            switch (llegit) {
            case Dictionary.xBS:
                if (line.getPosCursor() > 0) {
                    System.out.print("\u001b[1D"); // Moure cursor a lesquerra
                    System.out.print("\u001b[1P"); // Per borrar el caracter de lesquerra del cursor
                    line.backspace();
                }
                break;
            case Dictionary.xDEL:
                if (line.getPosCursor() < line.getLength()) {
                    System.out.print("\u001b[1P"); // Borrar el caracter del cursor
                    line.delete();
                }
                break;
            case Dictionary.xEND:
                int moure = line.getLength() - line.getPosCursor();
                System.out.print("\u001b[" + moure + "C");
                line.moveEnd();
                break;
            case Dictionary.xHOME:
                System.out.print("\u001b[" + line.getPosCursor() + "D");
                line.moveHome();
                break;
            case Dictionary.xLEFT:
                if (line.getPosCursor() != 0) {
                    System.out.print("\u001b[1D");
                    line.moveLeft();
                }
                break;
            case Dictionary.xRIGHT:
                if (line.getPosCursor() < line.getLength()) {
                    System.out.print("\u001b[1C");
                    line.moveRight();
                }
                break;
            case Dictionary.xINSERT:
                line.insertMode();
                break;
            default:
                if (line.getMode()) { // Aqui es on diferenciem el mode insercio a lhora descriure per pantalla
                    line.insertChar((char) llegit);
                    System.out.print("\u001b[1@" + (char) llegit); // @ crea un espai en blanc on esta el cursor i
                                                                   // despres escrivim
                                                                   // el char
                } else {
                    line.replaceChar((char) llegit);
                    System.out.print((char) llegit);
                }
                break;
            }
            llegit = this.read();
        }
        this.unsetRaw();
        return line.getLine().toString();
    }
}