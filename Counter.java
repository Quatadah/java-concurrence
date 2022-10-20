import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

public class Counter {

    private AtomicInteger ai;

    public Counter(int intialValue){
        ai = new AtomicInteger(intialValue);
    }

    public void countLines(String str){        
            String[] lines = str.split("\r\n|\r|\n");
            synchronized(this){
                this.ai.addAndGet(lines.length -2);  
            }  
    }

    public int get(){
        return ai.get();
    }
}
