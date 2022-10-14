import java.net.*;
import java.util.concurrent.*;
import java.util.Queue;
import java.io.IOException;


class Multiserv{    
    
    class MainThread implements Runnable{
        private Thread main;
        private ServerSocket serverSocket;       
        private ServerSocket toClose; 
        public MainThread(Thread main, ServerSocket toClose) throws IOException{
            this.main = main;
            this.serverSocket = new ServerSocket(5000);            
            this.toClose = toClose;
        }
        public void run(){
            System.out.println("Je suis admin");
            try (Socket socket = serverSocket.accept()) {
            } catch (IOException e) {                
                e.printStackTrace();
            }
            System.out.println("Try done");
            System.out.println(main);
            try {
                toClose.close();
            } catch (IOException e) {                
                e.printStackTrace();
            }
            main.interrupt();
        }                
    }

    private Counter counter;
    public Multiserv(Counter c){
        this.counter = c;
    }

    public void start() throws Exception{
        ServerSocket serverSocket = new ServerSocket(8080);
        //ServerSocket turnOff = new ServerSocket(5000);
        ExecutorService pool = Executors.newFixedThreadPool(3);
        Queue<Future<String>> resultList = new LinkedBlockingQueue<>();

        System.out.println(Thread.currentThread());

        Thread admin = new Thread(new MainThread(Thread.currentThread(), serverSocket));
        admin.start();

    
        System.out.println("Attend une nouvelle connexion ...");
        while(!Thread.interrupted()){           
            if (!serverSocket.isClosed()){
                try{
                    Socket socket = serverSocket.accept();
                    Future<String> result = pool.submit(new Work(socket, this.counter));
                    resultList.add(result);
                }catch(SocketException e){                    
                }
                
                
            }
        }

        Future<String> result;
        while((result=resultList.poll()) != null) {
            System.out.println("J ai un résultat " + result.get());
            System.out.println("J ai fini get ");
        }
        System.out.println("J ai eu tous les résultats ");
        pool.shutdown();
        
        System.out.println("Le nombre de ligne est : " + counter.get());
    }
}
