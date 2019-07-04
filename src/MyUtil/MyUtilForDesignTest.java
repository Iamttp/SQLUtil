package MyUtil;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.*;

/**
 * 1. 测试类必须用@RunWith注释，并且将Parameterized.class作为参数
 */
@RunWith(value = Parameterized.class)
public class MyUtilForDesignTest {
    /**
     * 2. 必须声明测试中所使用的所有的实例变量
     */
    String in;
    String out;

    public MyUtilForDesignTest(String in, String out) {
        super();
        this.in = in;
        this.out = out;
    }

    /**
     * 3. 提供一个@parameters注释的方法
     */
    @Parameterized.Parameters
    public static Collection getTestParameters() {
        return Arrays.asList(new Object[][]{
                {"A->C,C->A,B->A,B->C,D->A,D->C,BD->A", "[A->C, C->A, B->C, D->C]"},
                {"A->B,B->C,AB->C", "[A->B, B->C]"},
                {"A->BC,CD->E,B->D,E->A", "[A->B,A->C,CD->E,B->D,E->A]"},
                {"A->C,AC->D","[A->C, A->D]"},
                {"AB->DE,B->DE,C->C","[B->D, B->E]"},
                {"A->B,C->D,E->E","[A->B, C->D]"}
        });
    }

    @Test
    public void getRes() {
        assertEquals(MyUtilForDesign.getRes(in.split(","), -1).replaceAll(" ",""), out.replaceAll(" ",""));
    }
}