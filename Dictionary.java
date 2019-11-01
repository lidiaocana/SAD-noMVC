interface Dictionary {

    //Codificacions per a les sequencies d'escape en sistema MACOS trobades utilitzant BufferedReader

    //Totes les sequencies d'escape comencen per ESC => ^[ o per ESC+CSI => ^[[

    public final static int ESC = 27;            //ESC => ^[
    public final static int CSI = 91;           //CSI => [ 

    public final static int ENTER_1 = 10;
    public final static int ENTER_2 = 13;

    public final static int BS = 127;
    
    //Funcions amb ESC + CSI

    public final static int DEL1 = 51;          // delete (fn+borrar en mac) => ^[[3~
    public final static int DEL2 = 126;  
    public final static int RIGHT = 67;         // right => ^[[C
    public final static int LEFT = 68;          // left => ^[[D
    public final static int HOME = 72;          // (fn+esquerra) => ^[[H
    public final static int END = 70;           // (fn+dreta) => ^[[F
    public final static int INSERT1 = 65;       // insert (MAC no te tecla insert, fem la tecla de pujar) => ^[[A

    //Conversions de sequencies d'escape

    public final static int xDEL = 1000;          
    public final static int xRIGHT = 1001;         
    public final static int xLEFT = 1002;          
    public final static int xHOME = 1003;         
    public final static int xEND = 1004;   
    public final static int xINSERT = 1005; 
    
    public final static int xBS = 1006;
}