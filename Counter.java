import java.util.concurrent.atomic.AtomicInteger;

public class Counter {
    AtomicInteger ai;
    public Counter(int intialValue){
        ai = new AtomicInteger(intialValue);
    }

    public void countLines(String str){        
            String[] lines = str.split("\r\n|\r|\n");
            this.ai.addAndGet(lines.length);
        
    }

    public int get(){
        return ai.get();
    }
}
