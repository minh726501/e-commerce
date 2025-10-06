package bqminh.e_commerce.service;

import bqminh.e_commerce.dto.request.CreateOrderRequest;
import bqminh.e_commerce.dto.request.UpdateOrderStatusRequest;
import bqminh.e_commerce.dto.response.OrderDetailResponse;
import bqminh.e_commerce.dto.response.OrderResponse;
import bqminh.e_commerce.enity.*;
import bqminh.e_commerce.enity.enums.OrderStatus;
import bqminh.e_commerce.repository.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final CartDetailRepository cartDetailRepository;
    private final OrderDetailRepository orderDetailRepository;

    public OrderService(OrderRepository orderRepository, UserRepository userRepository, CartRepository cartRepository, CartDetailRepository cartDetailRepository,OrderDetailRepository orderDetailRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.cartRepository = cartRepository;
        this.cartDetailRepository = cartDetailRepository;
        this.orderDetailRepository=orderDetailRepository;
    }
    public OrderResponse mapToOrderResponse(Order order){
        OrderResponse response=new OrderResponse();
        response.setStatus(OrderStatus.PENDING.name());
        List<OrderDetailResponse>responseList=new ArrayList<>();
        double total=0;
        for (OrderDetail orderDetail:order.getOrderDetails()) {
            OrderDetailResponse orderDetailResponse=new OrderDetailResponse();
            orderDetailResponse.setPrice(orderDetail.getUnitPrice());
            orderDetailResponse.setProductName(orderDetail.getProductName());
            orderDetailResponse.setQuantity(orderDetail.getQuantity());
            orderDetailResponse.setSubTotal(orderDetail.getUnitPrice()*orderDetail.getQuantity());
            total+=orderDetail.getTotalPrice();
            responseList.add(orderDetailResponse);
        }
        response.setTotalPrice(total);
        response.setOrderDetails(responseList);
        return  response;

    }

    public OrderResponse checkout(CreateOrderRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> {
                    throw new RuntimeException("User khong ton tai");
                });
        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(()->{
                    throw new RuntimeException("Cart trong");
                });
        if (cart.getCartDetails()==null){
            throw new RuntimeException("Gio hang trong");
        }
        Order order=new Order();
        order.setStatus(OrderStatus.PENDING);
        order.setUser(user);
        orderRepository.save(order); // luu trc de co orderId
        List<OrderDetail>orderDetailList=new ArrayList<>();
        double total=0;
        for (CartDetail cartDetail:cart.getCartDetails()){
            OrderDetail orderDetail=new OrderDetail();
            orderDetail.setOrder(order); // giờ order đã có id
            orderDetail.setProductName(cartDetail.getProduct().getName());
            orderDetail.setUnitPrice(cartDetail.getProduct().getPrice());
            orderDetail.setQuantity(cartDetail.getQuantity());
            orderDetail.setProduct(cartDetail.getProduct());
            orderDetail.setTotalPrice(cartDetail.getProduct().getPrice()*cartDetail.getQuantity());
            total+=cartDetail.getQuantity()*cartDetail.getProduct().getPrice();
            orderDetailList.add(orderDetail);
            orderDetailRepository.save(orderDetail);
            cartDetailRepository.delete(cartDetail);
        }
        order.setTotalPrice(total);
        order.setOrderDetails(orderDetailList);
        orderRepository.save(order);
        cartRepository.delete(cart);
        return mapToOrderResponse(order);
    }
    public List<OrderResponse> getOrderByUserId(long id){
        User user=userRepository.findById(id)
                .orElseThrow(()->{
                    throw new RuntimeException("User khong ton tai");
                });
        List<Order> orders= orderRepository.findAllByUser(user)
                .orElseThrow(()->{
                    throw new RuntimeException("User chua dat hang");
                });
        List<OrderResponse>responses=new ArrayList<>();
        for (Order order:orders ){
            OrderResponse response=mapToOrderResponse(order);
            responses.add(response);
        }
        return responses;
    }
    public OrderResponse updateOrderStatus(UpdateOrderStatusRequest status, long orderId){
        Order order=orderRepository.findById(orderId)
                .orElseThrow(()->{
                    throw new RuntimeException("Order khong ton tai");
                });
        order.setStatus(status.getStatus());
        orderRepository.save(order);
        return mapToOrderResponse(order);

    }
}
