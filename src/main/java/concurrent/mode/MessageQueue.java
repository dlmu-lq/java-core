package concurrent.mode;

/**
 * 异步模式，生产者消费者
 *  消息队列类，内部维护有界list，先进先出，空时阻塞消费线程，满时阻塞生产线程，使用wait，notifyAll控制线程间通信；
 */
public class MessageQueue {
}

// 生产消费的内容，内容id，用来联系生产和消费的键，内容，用来传递的内容；
class Message{

}
