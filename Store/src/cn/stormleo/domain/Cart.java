package cn.stormleo.domain;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

// 购物车对象
public class Cart {
    private Map<String ,CartItem> map=new HashMap<String,CartItem>();
    private double total; // 商品总价


    public Cart() {
    }

    // 添加商品到购物车
    public void addItemToCart(CartItem cartItem){
        // 获取到商品的 pid
        String pid=cartItem.getProduct().getPid();
        if(map.containsKey(pid)){
            CartItem oldItem = map.get(pid);
            oldItem.setNum(oldItem.getNum()+cartItem.getNum());
        }else{

           map.put(pid,cartItem);
        }
    }


    // 从购物车中移除选中的物品
    public void removeItem(String pid){
        map.remove(pid);
    }


    // 清空购物车
    public void clear(){
        map.clear();
    }

    public Map<String, CartItem> getMap() {
        return map;
    }

    public void setMap(Map<String, CartItem> map) {
        this.map = map;
    }

    // 获取到购物车总价
    public double getTotal() {
        total =0;
        Collection<CartItem> values = map.values();
        for(CartItem cartItem:values){
            total+=cartItem.getTotal();
        }
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public Cart(Map<String, CartItem> map, double total) {
        this.map = map;
        this.total = total;
    }


    public Collection<CartItem> getCartItems(){
        return map.values();
    }
}
