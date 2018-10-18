package cn.stormleo.domain;


// 存放购物车中的 物品信息
public class CartItem {
    private Product product; // 物品详情
    private int num; // 物品数量
    private double total;


    public void setTotal(double total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "CartItem{" +
                "product=" + product +
                ", num=" + num +
                ", total=" + total +
                '}';
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public double getTotal() {
        return product.getShop_price()*num;
    }



    public CartItem() {

    }

    public CartItem(Product product, int num, double total) {

        this.product = product;
        this.num = num;
        this.total = total;
    }
}
