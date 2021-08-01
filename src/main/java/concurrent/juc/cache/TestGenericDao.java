package concurrent.juc.cache;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 使用读写锁实现访问数据库的缓存
 * 还需考虑：
 *  读多写少；
 *  未考虑缓存容量；
 *  缓存过期；
 *  只适合单机；
 *  并发性低，多个表用一把锁；
 *  更新太简单，清空了所有key；
 *
 */
public class TestGenericDao {
    public static void main(String[] args) {
        GenericDao dao = new GenericDaoCached();
        System.out.println(dao.queryOne(String.class, "select * from user"));
        System.out.println(dao.queryOne(String.class, "select * from user"));
        System.out.println(dao.queryOne(String.class, "select * from user"));
    }
}

class GenericDaoCached extends GenericDao{

    private GenericDao genericDao = new GenericDao();
    private Map<SqlPair, Object> map = new HashMap<>();
    private ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();
    private ReentrantReadWriteLock.WriteLock writeLock = reentrantReadWriteLock.writeLock();
    private ReentrantReadWriteLock.ReadLock readLock = reentrantReadWriteLock.readLock();

    @Override
    public <T> T queryOne(Class<T> type, String sql, Object... args) {
        SqlPair sqlPair = new SqlPair(sql, args);
        T value;
        readLock.lock();
        try {
            // 先去缓存中找，找到返回
            value = (T) map.get(sqlPair);
            if(value != null){
                return value;
            }
        }finally {
            readLock.unlock();
        }

        writeLock.lock();
        try {
            value = (T) map.get(sqlPair);
            if(value == null){
                //  缓存中没有，去数据库找
                value = genericDao.queryOne(type, sql, args);
                map.put(sqlPair, value);
            }
        }finally {
            writeLock.unlock();
        }
        return value;
    }

    @Override
    public <T> void updateOne(Class<T> type, String sql, Object... args) {
        writeLock.lock();
        try {
            // 必须先清
            genericDao.updateOne(type, sql, args);
            // 清空缓存
            map.clear();
        }finally {
            writeLock.unlock();
        }
    }

    class SqlPair{
        private String sql;
        private Object[] args;

        public SqlPair(String sql, Object[] args) {
            this.sql = sql;
            this.args = args;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            SqlPair sqlPair = (SqlPair) o;
            return Objects.equals(sql, sqlPair.sql) &&
                    Arrays.equals(args, sqlPair.args);
        }

        @Override
        public int hashCode() {
            int result = Objects.hash(sql);
            result = 31 * result + Arrays.hashCode(args);
            return result;
        }

        public String getSql() {
            return sql;
        }

        public void setSql(String sql) {
            this.sql = sql;
        }

        public Object[] getArgs() {
            return args;
        }

        public void setArgs(Object[] args) {
            this.args = args;
        }
    }
}


