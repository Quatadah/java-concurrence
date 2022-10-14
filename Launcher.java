
public class Launcher{
    public static void main(String[] args) throws Exception {        
        Multiserv serveur = new Multiserv(new Counter(0));
        serveur.start();
    }
}