package stream;

import java.util.ArrayList;
import java.util.List;

public class CartService {
    // 初始化购物车
    private static List<Sku> cartSkuList = new ArrayList<>();

    static {
        cartSkuList.add(new Sku(2, "无人机", 1000.00, 10, 1000.00, SkuCategoryEnum.ELECTRONICS));
        cartSkuList.add(new Sku(1, "VR一体机", 2100.00, 10, 2100.00, SkuCategoryEnum.ELECTRONICS));
        cartSkuList.add(new Sku(4, "牛仔裤", 60.00, 10, 60.00, SkuCategoryEnum.CLOTHING));
        cartSkuList.add(new Sku(13, "衬衫", 120.00, 10, 120.00, SkuCategoryEnum.CLOTHING));
        cartSkuList.add(new Sku(121, "Java编程思想", 100.00, 10, 100.00, SkuCategoryEnum.BOOKS));
        cartSkuList.add(new Sku(3, "程序化广告", 80.00, 10, 80.00, SkuCategoryEnum.BOOKS));
    }

    public static List<Sku> getCartSkuList() {
        return cartSkuList;
    }

}
