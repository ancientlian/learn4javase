package reflect;


import org.apache.commons.io.IOUtils;

import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;

public class ExecCmdTest {

    @SuppressWarnings("unchecked")
    public static void main(String[] args) {
        try {
            // 获取Runtime类对象
            Class runtimeClass = Class.forName("java.lang.Runtime");

            // 获取构造方法
            Constructor runtimeConstructor = runtimeClass.getDeclaredConstructor();
            runtimeConstructor.setAccessible(true);

            // 创建Runtime类实例 相当于 Runtime r = new Runtime();
            Object runtimeInstance = runtimeConstructor.newInstance();

            // 获取Runtime的exec(String cmd)方法
            Method runtimeMethod = runtimeClass.getMethod("exec", String.class);

            // 调用exec方法 等于 r.exec(cmd); cmd参数输入要执行的命令
            Process p = (Process) runtimeMethod.invoke(runtimeInstance, "whoami");

            // 获取命令执行结果
            InputStream results = p.getInputStream();

            // 输出命令执行结果
            System.out.println(IOUtils.toString(results, StandardCharsets.UTF_8));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void noReflect() {
        // 输出命令执行结果
        try {
            System.out.println(
                    IOUtils.toString(
                            Runtime.getRuntime().exec("whoami").getInputStream(), StandardCharsets.UTF_8)
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
