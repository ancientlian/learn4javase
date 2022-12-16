
import com.alibaba.fastjson2.JSON;
import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;
import stream.CartService;
import stream.Order;
import stream.SkuCategoryEnum;
import stream.Sku;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class StreamTest {
    List<Sku> list;

    @Before
    public void init() {
        list = CartService.getCartSkuList();
    }

    /**
     * •	filter使用：过滤不符合断言判断的数据
     */
    @Test
    public void filterTest() {
        list.stream()
                // 判断是否符合一个断言（商品类型是否为书籍），不符合则过滤元素
                .filter(sku -> SkuCategoryEnum.BOOKS.equals(sku.getSkuCategory()))
                // 打印终端操作结果
                .forEach(item -> System.out.println(JSON.toJSONString(item)));
    }

    /**
     * •	map使用：将一个元素转换为另一个元素
     */
    @Test
    public void mapTest() {
        list.stream()
                // 将一个元素转换为另一个数据类型的元素
                .map(Sku::getSkuName)
                // 打印终端操作结果
                .forEach(item -> System.out.println(JSON.toJSONString(item)));
    }

    /**
     * •	flatMap（扁平化）使用：将一个对象转换为一个流的操作
     */
    @Test
    public void flatMap() {
        list.stream()
                // 这里将商品名称切分为一个字符流，最终输出
                .flatMap(sku -> Arrays.stream(sku.getSkuName().split("")))
                // 打印终端操作结果
                .forEach(item -> System.out.println(JSON.toJSONString(item)));
    }

    /**
     * •	peek: 对流中元素进行遍历操作，与forEach类似，但不会销毁流元素
     */
    @Test
    public void peekTest() {
        list.stream()
                // 对一个流中的所有元素进行处理，但与forEach不同之处在于，peek是一个中间操作，
                // 操作完成还能被后续使用。forEach是一个终端操作，处理完之后流就不能被操作了。
                .peek(sku -> System.out.println(sku.getSkuName()))
                // 打印终端操作结果
                .forEach(item -> System.out.println(JSON.toJSONString(item)));
    }

    /**
     * •	sort：对流中元素进行排序，可选择自然排序或指定排序规则
     */
    @Test
    public void sortTest() {
        list.stream()
                .peek(sku -> System.out.println(sku.getSkuName()))
                // 根据Sku的价格进行升序排列
                .sorted(Comparator.comparing(Sku::getTotalPrice))
                // 打印终端操作结果
                .forEach(item -> System.out.println(JSON.toJSONString(item)));
    }

    /**
     * •	distinct：对流元素进行去重，有状态操作（针对所有元素进行排序）
     */
    @Test
    public void distinctTest() {
        list.stream()
                .map(sku -> sku.getSkuCategory())
                .distinct()
                // 打印终端操作结果
                .forEach(item -> System.out.println(JSON.toJSONString(item)));
    }

    /**
     * •	skip：跳过前N条记录，有状态操作
     * 对商品的价格进行排序，跳过前三个（从小到大排序，跳过最小的三个）
     */
    @Test
    public void skipTest() {
        list.stream()
                .sorted(Comparator.comparing(Sku::getTotalPrice))
                // 对价格排序之后跳过前三条
                .skip(3)
                // 打印终端操作结果
                .forEach(item -> System.out.println(JSON.toJSONString(item)));
    }

    /**
     * •	limit：截断前N条记录，有状态操作
     */
    @Test
    // 对商品价格进行排序，取前三个（从小到大，取top3）
    public void limitTests() {
        list.stream()
                .sorted(Comparator.comparing(Sku::getTotalPrice))
                // 对价格排序之后取前三条
                .limit(3)
                // 打印终端操作结果
                .forEach(item -> System.out.println(JSON.toJSONString(item)));
    }


    /*****************************终端操作实例******************/

    /**
     * •	allMatch: 检测所有元素是否满足断言，如果都满足返回true，有一个不满足返回false
     */
    @Test
    public void allMatchTest() {
        boolean isMatch = list.stream()
                // 打印出部门商品名称即结束，参考打印结果
                .peek(sku -> System.out.println(sku.getSkuName()))
                // allMatch是短路操作
                .allMatch(sku -> sku.getTotalPrice() > 100);
        System.out.println(isMatch);
    }

    /**
     * •	anyMatch: 只有有元素满足断言判断就返回true，否则返回false (存在至少一个满足条件的元素即是true)
     */
    @Test
    public void anyMatchTest() {
        boolean isMatch = list.stream()
                .peek(sku -> System.out.println(sku.getSkuName()))
                .anyMatch(sku -> sku.getTotalPrice() > 100);
        System.out.println(isMatch);
    }

    /**
     * •	noneMatch: 只有所有元素都不满足断言判断才返回true，否则返回false
     */
    @Test
    public void noneMatchTest() {
        boolean isMatch = list.stream()
                .peek(sku -> System.out.println(sku.getSkuName()))
                .noneMatch(sku -> sku.getTotalPrice() > 10_000);
        System.out.println(isMatch);
    }

    /**
     * •	findFirst：找到第一个元素
     */
    @Test
    public void findFirstTest() {
        Optional<Sku> optional = list.stream()
                .peek(sku -> System.out.println(sku.getSkuName()))
                .findFirst();
        System.out.println(JSON.toJSONString(optional.get()));
    }

    /**
     * •	findAny：找到任意一个元素，只要有元素就返回
     */
    @Test
    public void findAnyTest() {
        Optional<Sku> optional = list.stream()
                .peek(sku -> System.out.println(sku.getSkuName()))
                .findAny();
        System.out.println(JSON.toJSONString(optional.get()));
    }

    @Test
    public void maxTest() {
        OptionalDouble optionalDouble = list.stream()
                // 提取出价格 数据类型为double  mapToDouble将元素映射为double元素，返回一个doubleStream流
                .mapToDouble(Sku::getTotalPrice)
                // 提取最大值
                .max();
        System.out.println(optionalDouble.getAsDouble());
    }

    @Test
    public void minTest() {
        OptionalDouble optionalDouble = list.stream()
                // 提取出价格 数据类型为double  mapToDouble将元素映射为double元素，返回一个doubleStream流
                .mapToDouble(Sku::getTotalPrice)
                // 提取最小值
                .min();
        System.out.println(optionalDouble.getAsDouble());
    }

    @Test
    public void countTest() {
        long count = list.stream()
                // 提取出价格 数据类型为double  mapToDouble将元素映射为double元素，返回一个doubleStream流
                .mapToDouble(Sku::getTotalPrice)
                // 提取总数
                .count();
        System.out.println(count);
    }

    /**
     * 集合收集
     */
    @Test
    public void toList() {
        List<Sku> list = CartService.getCartSkuList();
        List<Sku> result = list.stream()
                .filter(sku -> sku.getTotalPrice() > 100)
                .collect(Collectors.toList());
        System.out.println(JSON.toJSONString(result));
    }

    /**
     * 集合分组
     */
    @Test
    public void group() {
        List<Sku> list = CartService.getCartSkuList();
        // key=分组条件  value=元素集合  即Map<分组条件，结果集合>
        Map<Enum, List<Sku>> result = list.stream()
                .collect(Collectors.groupingBy(sku -> sku.getSkuCategory()));
        System.out.println(JSON.toJSONString(result));
    }

    /**
     * 集合分区
     */
    @Test
    public void partition() {
        List<Sku> list = CartService.getCartSkuList();
        Map<Boolean, List<Sku>> partition = list.stream()
                .collect(Collectors.partitioningBy(sku -> sku.getTotalPrice() > 100));
        System.out.println(JSON.toJSONString(partition));
    }

    /**
     * reduce 案例1：
     * 计算一批商品的总价格
     */
    @Test
    public void reduceTest() {

        /*
         * 准备一批订单数据
         */
        List<Order> list = Lists.newArrayList();
        list.add(new Order(1, 2, 15.12));
        list.add(new Order(2, 5, 257.23));
        list.add(new Order(3, 3, 23331.12));

        /*
         * 传统方式
         * 1. 计算商品数量
         * 2. 计算消费总金额
         * <p>
         * 以下展示Stream的reduce方式
         * 思想：分治法
         * <p>
         *         U reduce(U identity,                                 初始基点,此处就是订单中属性都是0
         *                  BiFunction<U, ? super T, U> accumulator,    计算逻辑，定义两个元素如何进行操作
         *                  BinaryOperator<U> combiner);                并行执行时多个部分结果的合并方式
         * <p>
         *
         * 汇总商品数量和总金额
         */
        Order order = list.stream()
                //.parallel()     // 并行方式
                .reduce(
                        // 参数1：初始化值
                        new Order(0, 0, 0.0),
                        // 参数2：Stream中两个元素的计算逻辑
                        (Order order1, Order order2) -> {
                            System.out.println("执行 计算逻辑 方法！！！");
                            // 计算两个订单商品数量和，消费金额之和
                            int productCount = order1.getProductCount() + order2.getProductCount();
                            double totalAmount = order1.getTotalAmount() + order2.getTotalAmount();
                            // 返回计算结果
                            return new Order(0, productCount, totalAmount);
                        },
                        // 参数3：并行情况下，多个并行结果如何合并
                        (Order order1, Order order2) -> {
                            System.out.println("执行 合并 方法！！！");
                            // 计算两个订单商品数量和，消费金额之和
                            int productCount = order1.getProductCount() + order2.getProductCount();
                            double totalAmount = order1.getTotalAmount() + order2.getTotalAmount();
                            // 返回计算结果
                            return new Order(0, productCount, totalAmount);
                        });
        System.out.println(JSON.toJSONString(order));
    }


    @Test
    public void reduceTest2() {
        Optional accResult = Stream.of(6, 7, 8, 9, 10)
                .reduce((acc, item) -> {
                    System.out.println("acc : " + acc);
                    acc += item;
                    System.out.println("item: " + item);
                    System.out.println("acc+ : " + acc);
                    System.out.println("--------");
                    return acc;
                });
        System.out.println(accResult);
    }

    @Test
    public void reduceTest3() {
        int accResult = Stream.of(6, 7, 8, 9, 10)
                .reduce(200, (acc, item) -> {
                    System.out.println("acc : " + acc);
                    acc += item;
                    System.out.println("item: " + item);
                    System.out.println("acc+ : " + acc);
                    System.out.println("--------");
                    return acc;
                });
        System.out.println(accResult);
    }

    @Test
    public void reduceTest4() {
        ArrayList<Integer> accResult_ = Stream.of(2, 3, 4)
                .reduce(Lists.newArrayList(1),
                        (acc, item) -> {
                            acc.add(item);
                            System.out.println("item: " + item);
                            System.out.println("acc+ : " + acc);
                            System.out.println("BiFunction");
                            return acc;
                        }, (acc, item) -> {
                            System.out.println("BinaryOperator");
                            acc.addAll(item);
                            System.out.println("item: " + item);
                            System.out.println("acc+ : " + acc);
                            System.out.println("--------");
                            return acc;
                        }
                );
        System.out.println("accResult_: " + accResult_);
    }


    @Test
    public void streamWay() {
        AtomicReference<Double> money = new AtomicReference<>(Double.valueOf(0.0));
        List<String> resultSkuNameList = CartService.getCartSkuList()
                // 获取集合流
                .stream()
                /**1. 打印商品信息*/
                .peek(sku -> System.out.println(JSON.toJSONString(sku)))
                /**2. 过滤掉所有的图书类商品*/
                .filter(sku -> !SkuCategoryEnum.BOOKS.equals(sku.getSkuCategory()))
                /**3. 价格进行排序，默认是从小到大，调用reversed进行翻转排序即从大到小*/
                .sorted(Comparator.comparing(Sku::getTotalPrice).reversed())
                /**4. 取top2*/
                .limit(2)
                /**累加金额*/
                .peek(sku -> money.set(money.get() + sku.getTotalPrice()))
                /**获取商品名称*/
                .map(sku -> sku.getSkuName())
                .collect(Collectors.toList());
        System.out.println("商品总价:" + money.get());
        System.out.println("商品名列表:" + JSON.toJSONString(resultSkuNameList));
    }

}
