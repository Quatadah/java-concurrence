import java.util.concurrent.Callable;
import java.net.Socket;
import java.io.*;


class Work implements Callable<String>{

    private Socket socket ;
    private Counter counter;
    Work(Socket s, Counter c){
        this.socket = s;
        this.counter = c;
    }

    public String getContent(String Request){
        String content = "<h1> Helloooooo </h1>";
        String respond = "HTTP/1.1 200 OK\n"+
                        "Content-Length" + content.length() + "\n" +
                        "Content-Type: text/html\n"+
                        "Connection: close\n" + "\r\n" +
                        content +"\r\n";
        return respond;
    }


    @Override
    public String call() throws Exception {
        //Thread.sleep(1000);
        //return the thread name executing this callable task
        System.out.println("Thread" + Thread.currentThread().getName() +"je demarre.\n");

        InputStream is = socket.getInputStream();
        byte bytes[] = new byte[1024];
        
        is.read(bytes);

        String request = new String(bytes);  
        counter.countLines(request);      

        OutputStream os = socket.getOutputStream();

        os.write(getContent(request).getBytes());

        System.out.print("le client demande : "+ request);

        socket.close();

        return Thread.currentThread().getName();
    }
}