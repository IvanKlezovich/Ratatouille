package org.example.orderservice.service;

import lombok.RequiredArgsConstructor;
import org.example.orderservice.dto.DishesDto;
import org.example.orderservice.dto.OrderDto;
import org.example.orderservice.model.Dishes;
import org.example.orderservice.model.Order;
import org.example.orderservice.repository.OrderRepository;
import org.example.orderservice.util.DishesMapper;
import org.example.orderservice.util.OrderMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;

    public List<OrderDto> getAllOrders(){
        List<Order> orders = orderRepository.findAll();
        return orders.stream().map(OrderMapper.INSTANCE::toDto)
                .collect(Collectors.toList());
    }

    public void deleteOrder(int orderId){
        orderRepository.deleteById(orderId);
    }

    public void deleteDishFromOrders(int dishId) {
        List<Order> orders = orderRepository.findAll();
        for (Order order : orders) {
            List<Dishes> dishesToRemove = order.getDishes().stream()
                    .filter(dish -> dish.getId() == dishId)
                    .toList();

            if (!dishesToRemove.isEmpty()) {
                double costToDeduct = dishesToRemove.stream()
                        .mapToDouble(dish -> dish.getPrice() * dish.getCount())
                        .sum();

                order.getDishes().removeAll(dishesToRemove);
                updateOrderCost(order, -costToDeduct);
                orderRepository.save(order);
            }
        }
    }

    public void addDishToOrder(int idOrder, DishesDto dishDto){
        Order order = orderRepository.findById(idOrder).orElseThrow(() -> new RuntimeException("Заказ не найден"));
        Dishes dish = DishesMapper.INSTANCE.fromDto(dishDto);
        order.getDishes().add(dish);
        double costToAdd = dish.getPrice() * dish.getCount();
        updateOrderCost(order, costToAdd);
        orderRepository.save(order);
    }

    private void updateOrderCost(Order order, double costChange){
        double updatedCost = order.getOrderCost() + costChange;
        order.setOrderCost(updatedCost);
    }

}
