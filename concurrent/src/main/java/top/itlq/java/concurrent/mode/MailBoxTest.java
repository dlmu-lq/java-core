package top.itlq.java.concurrent.mode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 保护性暂停同步模式，每一个任务都有一个生产线程，一个消费线程
 */
public class MailBoxTest {
    
    private static final Logger log = LoggerFactory.getLogger(MailBoxTest.class);

    public static void main(String[] args) {
        for(int i=0;i<3;i++){
            new Person(i).start();
        }
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        MailBox.getIds().forEach(integer -> {
            new PostMan(integer, "mail content " + integer).start();
        });
    }
}

class Person extends Thread{

    private static final Logger log = LoggerFactory.getLogger(Person.class);

    private final GuardedObject guardedObject;
    private final int id;

    public Person(int id){
        this.guardedObject = MailBox.createGuardedObject();
        this.id = id;
    }

    @Override
    public void run() {
        log.info("person-" + id + " 收信...");
        Object object = guardedObject.get(3000);
        log.info("person-" + id + " 收到信：" + object);
    }
}

class PostMan extends Thread{
    
    private static final Logger log = LoggerFactory.getLogger(PostMan.class);

    private final String mail;
    private final int id;

    public PostMan(int id, String mail){
        this.id = id;
        this.mail = mail;
    }

    @Override
    public void run() {
        log.info("postman-" + id + " 送信...");
        MailBox.getGuardedObjectById(id).complete(mail);
    }
}

class MailBox{

    // {id->保护对象}
    private static final Map<Integer, GuardedObject> map = new ConcurrentHashMap<>();

    //
    private static final AtomicInteger id = new AtomicInteger(0);

    public static GuardedObject createGuardedObject(){
        GuardedObject guardedObject = new GuardedObject();
        map.put(id.getAndIncrement(), guardedObject);
        return guardedObject;
    }

    // 在获得内容线程中创建时加入map，取出时便是放入内容线程，下次便不再使用，获取，之间移除
    public static GuardedObject getGuardedObjectById(int id){
        return map.remove(id);
    }

    public static Set<Integer> getIds(){
        return map.keySet();
    }
}
