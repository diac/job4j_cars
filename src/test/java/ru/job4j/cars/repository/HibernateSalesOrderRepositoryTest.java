package ru.job4j.cars.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.job4j.cars.config.DataSourceConfig;
import ru.job4j.cars.model.Post;
import ru.job4j.cars.model.SalesOrder;
import ru.job4j.cars.model.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = {
        DataSourceConfig.class,
        HibernateCrudRepository.class,
        HibernatePostRepository.class,
        HibernateSalesOrderRepository.class,
        HibernateCarRepository.class,
        HibernatePostSearchResultRepository.class,
        HibernateUserRepository.class
})
public class HibernateSalesOrderRepositoryTest {

    @Autowired
    private SalesOrderRepository salesOrderRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void whenCreate() {
        SalesOrder salesOrder = makeSalesOrder();
        salesOrderRepository.add(salesOrder);
        SalesOrder salesOrderInDb = salesOrderRepository.findById(salesOrder.getId())
                .orElse(new SalesOrder());
        assertThat(salesOrderInDb).isEqualTo(salesOrder);
    }

    @Test
    public void whenFindAll() {
        SalesOrder salesOrder = makeSalesOrder();
        salesOrderRepository.add(salesOrder);
        List<SalesOrder> salesOrders = salesOrderRepository.findAll();
        assertThat(salesOrders.contains(salesOrder)).isTrue();
    }

    @Test
    public void whenUpdate() {
        SalesOrder salesOrder = makeSalesOrder();
        salesOrderRepository.add(salesOrder);
        User user = salesOrder.getPreviousOwner();
        salesOrder.setPreviousOwner(salesOrder.getNewOwner());
        salesOrder.setNewOwner(user);
        salesOrderRepository.update(salesOrder);
        SalesOrder salesOrderInDb = salesOrderRepository.findById(salesOrder.getId())
                .orElse(new SalesOrder());
        assertThat(salesOrderInDb).isEqualTo(salesOrder);
    }

    @Test
    public void whenDelete() {
        SalesOrder salesOrder = makeSalesOrder();
        salesOrderRepository.add(salesOrder);
        int salesOrderId = salesOrder.getId();
        salesOrderRepository.delete(salesOrder);
        Optional<SalesOrder> salesOrderInDb = salesOrderRepository.findById(salesOrderId);
        assertThat(salesOrderInDb).isEmpty();
    }

    @Test
    public void whenDeleteById() {
        SalesOrder salesOrder = makeSalesOrder();
        salesOrderRepository.add(salesOrder);
        int salesOrderId = salesOrder.getId();
        salesOrderRepository.delete(salesOrderId);
        Optional<SalesOrder> salesOrderInDb = salesOrderRepository.findById(salesOrderId);
        assertThat(salesOrderInDb).isEmpty();
    }

    private SalesOrder makeSalesOrder() {
        String value = String.valueOf(System.currentTimeMillis());
        User user1 = new User(0, value, value, new ArrayList<>(), null);
        User user2 = new User(0, value + "_2", value + "_2", new ArrayList<>(), null);
        userRepository.add(user1);
        userRepository.add(user2);
        Post post = new Post(
                0,
                value,
                LocalDateTime.now(),
                user1,
                new byte[]{1, 2, 3},
                true,
                0,
                null,
                new ArrayList<>(),
                new HashSet<>(),
                null
        );
        postRepository.add(post);
        return new SalesOrder(0, post, user1, user2, LocalDateTime.now());
    }
}