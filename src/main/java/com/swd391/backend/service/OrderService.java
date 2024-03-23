package com.swd391.backend.service;

import com.swd391.backend.dao.*;
import com.swd391.backend.entity.*;
import com.swd391.backend.request.CreateOrder;
import com.swd391.backend.service.Interface.IOrderService;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

@Service
public class OrderService implements IOrderService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OrderDetailRepository detailRepository;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private JavaMailSender sender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    private ScheduledExecutorService scheduler;
    private ScheduledFuture<?> taskFuture;

    @Override
    public Order CreateOrderCart(CreateOrder orders, String username) {
        Order order = orderRepository.findOrdersByUserAndStatus(userRepository.findByUsername(username), 0);
        Course course = null;
        if(order != null){
            course = courseRepository.findByCourseId(orders.getCourseID());
            if(!detailRepository.existsOrderDetailByOrderAndCourse(order, course)){
                order.setTotal(order.getTotal() + course.getPrice());
                detailRepository.save(OrderDetail.builder()
                        .price(course.getPrice())
                        .course(course)
                        .order(order)
                        .build());
                return orderRepository.save(order);
            }else{
                throw new RuntimeException();
            }

        }
        else{
            Date date = new Date();
            order = orderRepository.save(
                    Order.builder()
                            .orderId(GenerateOrderID(6))
                            .createdAt(date)
                            .total(0)
                            .status(0)
                            .user(userRepository.findByUsername(username))
                            .build()
            );
            course = courseRepository.findByCourseId(orders.getCourseID());
            order = orderRepository.findById(order.getOrderId()).get();
            order.setTotal(order.getTotal() + course.getPrice());
            detailRepository.save(OrderDetail.builder()
                    .price(course.getPrice())
                    .course(course)
                    .order(order)
                    .build());
            return orderRepository.save(order);
        }

    }

    @Override
    public List<Order> GetOrderCar(String username) {
        return orderRepository.findOrderByUser(userRepository.findByUsername(username));

    }

    @Override
    public List<OrderDetail> GetOrderDetail(String orderID, String s) {
        Order order = orderRepository.findById(orderID).orElse(null);
        return detailRepository.findAllOrderDetailByOrder(order);
    }

    @Override
    public Object PayOrder(String orderID, String s) {
        if(userRepository.existsByUsername(s)){
            Order existing = orderRepository.findById(orderID).get();
            existing.setStatus(1);

            return orderRepository.save(existing);
        }else {
            throw new NoSuchElementException();
        }
    }

    @Override
    public void DeleteOrder(String orderID, String s){
        Order order= orderRepository.findById(orderID).get();
        if(order.getStatus() != 1){
            orderRepository.deleteOrderByOrderId(orderID);
        }

    }

    @Override
    public ResponseEntity<?> HandleOrderPayment(String orderID, int courseID, String token){
        List<Transaction> transactions = transactionRepository.findAll();
        //get all transactions description
        List<String> transactionDescriptions = new ArrayList<>();
        for (Transaction transaction: transactions)
        {
            transactionDescriptions.add(transaction.getDescription());
        }
        for(String ordercode : transactionDescriptions)
        {
            if(ordercode.contains(orderID))
            {
                Date date = new Date();
                Course course = courseRepository.findByCourseId(courseID);
//                Order order = orderRepository.findById(orderID).get();
                Order order = orderRepository.save(
                        Order.builder()
                                .orderId(orderID)
                                .createdAt(date)
                                .total(course.getPrice())
                                .status(0)
                                .user(userRepository.findByUsername(jwtService.extractUsername(token))
                                ).build()
                );
                orderRepository.save(order);
                OrderDetail orderDetail = new OrderDetail();
                orderDetail.setPrice(course.getPrice());
                orderDetail.setCourse(course);
                orderDetail.setOrder(order);
                orderDetailRepository.save(orderDetail);
                order.setStatus(1);
                orderRepository.save(order);
                User user = userRepository.findByUsername(jwtService.extractUsername(token));
                user.getCourses().add(course);
                userRepository.save(user);
                scheduler = Executors.newSingleThreadScheduledExecutor();
                long delay = 60L * 1000L;;
                taskFuture = scheduler.schedule(() -> SendMailPaymentSuccess(order, user, course), delay, TimeUnit.MILLISECONDS);
                return ResponseEntity.ok("success");
            }
        }
        return ResponseEntity.ok("failed");
    }

    private void SendMailPaymentSuccess(Order order, User user, Course course){
        try {
            MimeMessage mimeMessage = sender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setFrom(fromEmail);
            helper.setTo(user.getEmail());
            helper.setSubject("[Ani Test Lab] - Thank You for Your Purchase - Welcome to " + course.getCourseName());
            helper.setText("Hi " + user.getFullName() + ",\n"
                    + "\nThank for your purchasing our course. We hope you have a nice learning with " + course.getCourseName()
                    + "\n Total bill: " + order.getTotal()
                    + "\nFrom Ani Test Lab."

            );
            sender.send(mimeMessage);
            cancelScheduledTask();
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    public void cancelScheduledTask() {
        if (taskFuture != null) {
            taskFuture.cancel(false);
        }
    }
    @Override
    public ResponseEntity<?> ListEnrollCourse(String token){
        User user = userRepository.findByUsername(jwtService.extractUsername(token));
        return ResponseEntity.ok(user.getCourses());
    }

    public String GenerateOrderID(int length){
        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            char randomChar = (char) (random.nextInt(26) + 'A');
            stringBuilder.append(randomChar);
        }

        return stringBuilder.toString();
    }
}
