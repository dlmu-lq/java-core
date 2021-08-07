//package concurrent.mode;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.io.IOException;
//import java.net.http.HttpClient;
//import java.net.http.HttpRequest;
//import java.net.http.HttpResponse;
//
//public class GuardedSynchronized {
//
//    private static final Logger log = LoggerFactory.getLogger(GuardedSynchronized.class);
//
//    public static void main(String[] args) {
//                guardedObject.complete(download());
//            } catch (IOException e) {
//                e.printStackTrace();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        GuardedObject guardedObject = new GuardedObject();
//        new Thread(()->{
//            log.info("获取结果");
//            log.info("结果：" + guardedObject.get(2000));
//        }, "t1").start();
//
//        new Thread(()->{
//            log.info("开始下载");
//            try {
////                guardedObject.complete(download());
//            } catch (IOException e) {
//                e.printStackTrace();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }, "t2").start();
//    }
//
//    private static Object download() throws IOException, InterruptedException {
//        return HttpClient.newBuilder().build().send(
//                HttpRequest.newBuilder().GET().uri(URI.create("http://www.baidu.com")).build(),
//                HttpResponse.BodyHandlers.ofString()
//        ).body();
//    }
//}
//
//class GuardedObject{
//    private Object response;
//
//    public Object get(long timeout){
//        synchronized (this){
//            // 开始等待时间
//            long begin = System.currentTimeMillis();
//            long passed = 0;
//            while(response == null){
//                long shouldWait = timeout - passed;
//                if(shouldWait <= 0){
//                    break;
//                }
//                try {
//                    this.wait(shouldWait);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                passed = System.currentTimeMillis() - begin;
//            }
//            return response;
//        }
//    }
//
//    public void complete(Object response){
//        synchronized (this){
//            this.response = response;
//            this.notifyAll();
//        }
//    }
//}