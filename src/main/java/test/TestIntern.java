package test;

public class TestIntern {
    public static void main(String[] args) {
        String str1 = new StringBuilder("方法").append("区").toString();
        // jdk6及之前：false, jdk7及之后，true（前提：之前没将“方法区”三个字放在常量池中）；
        System.out.println((str1.intern() == str1));

        String str2 = new StringBuilder("ja").append("va").toString();
        // jdk6及之前：false, jdk7及之后，false,因为之前已将“java”放在常量池中，所以前者返回的不是str2在堆中的引用，而是java第一次出现时在堆中的引用）；
        System.out.println((str2.intern() == str2));
    }
}
