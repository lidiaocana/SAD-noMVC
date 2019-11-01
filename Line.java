public class Line {
    private int posCursor;
    private StringBuilder line;
    private Boolean modeInsert;

    public Line() {
        this.line = new StringBuilder();
        this.posCursor = 0;
        this.modeInsert = true;
    }

    public StringBuilder getLine() {
        return this.line;
    }

    public int getPosCursor() {
        return this.posCursor;
    }

    public int getLength() {
        return this.line.length();
    }

    public Boolean getMode() {
        return this.modeInsert;
    }

    public void insertMode() {
        this.modeInsert = !this.modeInsert;
    }

    public void insertChar(char newChar) { // Nomes es cridara si modeInsert == True
        this.line = this.line.insert(this.posCursor, newChar);
        this.posCursor++;
    }

    public void replaceChar(char newChar) { // Si modeInsert == False
        String str = "";
        str = str + newChar;
        this.line.replace(this.posCursor, this.posCursor + 1, str);
        this.posCursor++;
    }

    public void backspace() { // Mirar si calen condicions
        int borrar = this.posCursor - 1;
        this.line.deleteCharAt(borrar);
        this.posCursor--;
    }

    public void delete() {
        if (this.posCursor < this.line.length()) {
            this.line.deleteCharAt(this.posCursor);
        }
    }

    public void moveRight() {
        if (this.posCursor < line.length()) {
            this.posCursor++;
        }
    }

    public void moveLeft() {
        if (this.posCursor != 0) {
            this.posCursor--;
        }
    }

    public void moveHome() {
        this.posCursor = 0;
    }

    public void moveEnd() {
        this.posCursor = line.length();
    }
}