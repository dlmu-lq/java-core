package concurrent.mode;

/**
 * 两阶段终止，外部控制另一个线程的终止，并做一些终止后的清理等操作
 *  可以使用interrupt控制；
 *  也可使用volatile修饰状态变化标记（如果内部有sleep等timed_waiting操作，也可结合调用interrupt使用）来控制；
 *  同步模式之Balking：保证方法只运行一次，涉及读和写时，需要使用synchronized
 *      单例模式
 */
public class TwoPhaseTermination {

}
