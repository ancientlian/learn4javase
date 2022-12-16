package dynamicLoading;

public class OfficeBetter {
    public static void main(String[] args) {

        try {
            // 动态加载类，在运行时加载
            Class c = Class.forName("dynamicLoading." + args[0]);

            // 通过类类型，创建该类对象
            OfficeAble oa = (OfficeAble) c.newInstance();

            // 运行对应的方法
            oa.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
