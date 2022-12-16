import org.junit.Test;
import reflect.Foo;

public class ReflectTest {

    @Test
    public void classTest() {
        Class fooClass1 = Foo.class;

        try {
            Foo foo = (Foo) fooClass1.newInstance();
            foo.print();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void getClassTest() {
        Foo f = new Foo();
        Class<? extends Foo> fooClass2 = f.getClass();

        try {
            Foo foo = (Foo) fooClass2.newInstance();
            foo.print();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void classLoaderTest() {
        Class fooClass3 = null;
        try {
            fooClass3 = ClassLoader.getSystemClassLoader().loadClass("reflect.Foo");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            Foo foo = (Foo) fooClass3.newInstance();
            foo.print();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void forNameTest() {
        Class fooClass4 = null;
        try {
            fooClass4 = Class.forName("反射.Foo");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            // 注意: 如果反射的这个类,没有构造方法,那么也是可以被new出来的
            // 因为Java的类没有构造方法的时候,也会为类添加一个默认的空构造方法
            // 记得这一点,代码审计时遇到可控Class.forName但是不可控newInstance()的情况
            // 那么就可以去找那种没有设置构造方法的工具类,进行实例化
            Foo foo = (Foo) fooClass4.newInstance();
            foo.print();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void stringJNITest() {
        try {
            Class<?> stringArray = Class.forName("[Ljava.lang.String;");
            Class<String[]> test = String[].class;
            System.out.println(stringArray == test);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void booleanJNITest() {
        try {
            Class<?> booleanArray = Class.forName("[Ljava.lang.Boolean;");
            Class<Boolean[]> test = Boolean[].class;
            System.out.println(booleanArray == test);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void byteJNITest() {
        try {
            Class<?> byteArray = Class.forName("[B");
            Class<byte[]> test = byte[].class;
            System.out.println(byteArray == test);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void charJNITest() {
        try {
            Class<?> charArray = Class.forName("[C");
            Class<char[]> test = char[].class;
            System.out.println(charArray == test);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void shortJNITest() {
        try {
            Class<?> shortArray = Class.forName("[S");
            Class<short[]> test = short[].class;
            System.out.println(shortArray == test);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void intJNITest() {
        try {
            Class<?> intArray = Class.forName("[I");
            Class<int[]> test = int[].class;
            System.out.println(intArray == test);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void longJNITest() {
        try {
            Class<?> longArray = Class.forName("[J");
            Class<long[]> test = long[].class;
            System.out.println(longArray == test);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void floatJNITest() {
        try {
            Class<?> floatArray = Class.forName("[F");
            Class<float[]> test = float[].class;
            System.out.println(floatArray == test);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void doubleJNITest() {
        try {
            Class<?> doubleArray = Class.forName("[D");
            Class<double[]> test = double[].class;
            System.out.println(doubleArray == test);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }



}
