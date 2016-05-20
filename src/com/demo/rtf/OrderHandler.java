package com.demo.rtf;
import java.util.ArrayList;

class OrderHandler {
    private static ArrayList<Order> orderList = new ArrayList<Order>();
    private static OrderHandler orderHandler = null;
    private OrderHandler(){}

    public static OrderHandler getInstance(){
        if(orderHandler==null){
            orderHandler = new OrderHandler();
        }
        return orderHandler;
    }

    public void addOrder(Order order){
        order.orderId = getOrderId();
        orderList.add(order);
    }

    public void deleteOrder(int id){
        orderList.remove(id);
    }

    public void showAll(){
        for(int i=0;i<orderList.size();i++){
            System.out.println("------------------------------------------------------------------");
            System.out.println("Order id                : "+orderList.get(i).orderId);
            System.out.println("User                    : "+orderList.get(i).user);
            System.out.println("Product                 :"+orderList.get(i).item);
            System.out.println("Quantity                : "+orderList.get(i).qty);
            System.out.println("Order Time              : "+orderList.get(i).placeTime);
            System.out.println("------------------------------------------------------------------\n\n");
        }
    }

    private int getOrderId(){
        int id = 1;
        if(orderList.size() != 0){
            id = orderList.get(orderList.size()-1).orderId + 1;
        }
        return id;
    }
}
