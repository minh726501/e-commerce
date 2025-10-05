package bqminh.e_commerce.service;

import bqminh.e_commerce.dto.request.AddToCartRequest;
import bqminh.e_commerce.dto.request.UpdateCartRequest;
import bqminh.e_commerce.dto.response.CartDetailResponse;
import bqminh.e_commerce.dto.response.CartResponse;
import bqminh.e_commerce.enity.Cart;
import bqminh.e_commerce.enity.CartDetail;
import bqminh.e_commerce.enity.Product;
import bqminh.e_commerce.enity.User;
import bqminh.e_commerce.repository.CartDetailRepository;
import bqminh.e_commerce.repository.CartRepository;
import bqminh.e_commerce.repository.ProductRepository;
import bqminh.e_commerce.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CartService {
    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final CartDetailRepository cartDetailRepository;

    public CartService(CartRepository cartRepository, UserRepository userRepository, ProductRepository productRepository, CartDetailRepository cartDetailRepository) {
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.cartDetailRepository = cartDetailRepository;
    }

    public CartResponse mapToCartResponse(Cart cart) {
        CartResponse response = new CartResponse();
        response.setUsername(cart.getUser().getUsername());
        List<CartDetailResponse> list = new ArrayList<>();
        double totalPrice = 0;
        for (CartDetail cartDetail : cart.getCartDetails()) {
            CartDetailResponse detailResponse = new CartDetailResponse();
            detailResponse.setProductName(cartDetail.getProduct().getName());
            detailResponse.setQuantity(cartDetail.getQuantity());
            detailResponse.setPrice(cartDetail.getProduct().getPrice());
            detailResponse.setSubTotal(cartDetail.getQuantity() * cartDetail.getProduct().getPrice());
            totalPrice += detailResponse.getSubTotal();
            list.add(detailResponse);
        }
        response.setCartDetails(list);
        response.setTotalItem(list.size());
        response.setTotalPrice(totalPrice);
        return response;

    }

    public CartResponse getCartByUserId(long userId) {
        Optional<User> getUserDB = userRepository.findById(userId);
        if (getUserDB.isEmpty()) {
            throw new RuntimeException("user khong ton tai");
        }
        CartResponse response=new CartResponse();
        Cart cart = cartRepository.findByUser(getUserDB.get())
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setUser(getUserDB.get());
                    newCart.setCartDetails(new ArrayList<>());
                    return cartRepository.save(newCart);
                });

    return mapToCartResponse(cart);

    }
    public CartResponse addToCart(AddToCartRequest request){
        Optional<User>getUserDB=userRepository.findById(request.getUserId());
        if (getUserDB.isEmpty()){
            throw new RuntimeException("User khong ton tai");
        }
        Optional<Product>getProductDB=productRepository.findById(request.getProductId());
        if (getProductDB.isEmpty()){
            throw new RuntimeException("Product khong ton tai");
        }
        // Tìm giỏ hàng, nếu chưa có thì tạo mới
        Cart cart=cartRepository.findByUser(getUserDB.get())
                .orElseGet(()->{
            Cart newCart=new Cart();
            newCart.setUser(getUserDB.get());
           return cartRepository.save(newCart);
        });

        // Kiểm tra sản phẩm đã có trong giỏ chưa
        CartDetail cartDetail= cartDetailRepository.findByCartAndProduct(cart, getProductDB.get())
                .orElseGet(()->{
            CartDetail cd=new CartDetail();
            cd.setCart(cart);
            cd.setQuantity(0);
            cd.setProduct(getProductDB.get());
            cart.getCartDetails().add(cd);
           return cartDetailRepository.save(cd);
        });
        cartDetail.setQuantity(cartDetail.getQuantity()+ request.getQuantity());
        cartDetailRepository.save(cartDetail);
        Product saveProduct=getProductDB.get();
        saveProduct.setStock(saveProduct.getStock()- request.getQuantity());
        productRepository.save(saveProduct);
        return mapToCartResponse(cart);
    }
    public CartResponse updateCart(UpdateCartRequest update) {
        User user = userRepository.findById(update.getUserId())
                .orElseThrow(() -> {
                    throw new RuntimeException("User khong ton tai");
                });
        Product product = productRepository.findById(update.getProductId())
                .orElseThrow(() -> {
                    throw new RuntimeException("Product khong ton tai");
                });
        Cart cart = cartRepository.findByUser(user)
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setUser(user);
                    newCart.setCartDetails(new ArrayList<>());
                    return cartRepository.save(newCart);
                });
        CartDetail cartDetail = cartDetailRepository.findByCartAndProduct(cart, product)
                .orElseGet(() -> {
                    CartDetail newCartDetail = new CartDetail();
                    newCartDetail.setQuantity(update.getQuantity());
                    newCartDetail.setProduct(product);
                    newCartDetail.setCart(cart);
                    cart.getCartDetails().add(newCartDetail);
                    return cartDetailRepository.save(newCartDetail);
                });
        // Nếu quantity <= 0 → xoá sản phẩm khỏi giỏ
        if (update.getQuantity() <= 0) {
            cart.getCartDetails().remove(cartDetail);
            cartDetailRepository.delete(cartDetail);
        } else {
            cartDetail.setQuantity(update.getQuantity());
            cartDetailRepository.save(cartDetail);
        }
        return mapToCartResponse(cart);
    }

}
