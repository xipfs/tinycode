package net.xipfs.javac;

import java.lang.reflect.Method;

/**
 * description: MemoryJavaCompilerTest <br>
 *
 * @author xie hui <br>
 * @version 1.0 <br>
 * @date 2020/9/18 11:30 <br>
 */
public class MemoryJavaCompilerTest {
    private final static MemoryJavaCompiler compiler = new MemoryJavaCompiler();
    public static void main(String[] args) throws Exception{
        final String source = "public final class Solution {\n"
                + "public static String greeting(String name) {\n"
                + "\treturn \"Hello \" + name;\n" + "}\n}\n";
        final Method greeting = compiler.compileStaticMethod("greeting", "Solution", source);
        final Object result = greeting.invoke(null, "soulmachine");
        if("Hello soulmachine".equals(result.toString())){
            System.out.println("ok");
        }
    }
}
