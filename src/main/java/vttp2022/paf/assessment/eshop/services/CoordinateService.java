package vttp2022.paf.assessment.eshop.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vttp2022.paf.assessment.eshop.models.Order;
import vttp2022.paf.assessment.eshop.respositories.LineItemRepository;
import vttp2022.paf.assessment.eshop.respositories.OrderRepository;

@Service
public class CoordinateService {
    
    @Autowired
    private OrderRepository orderRepo;

    @Autowired
    private LineItemRepository lineItemRepo;

    @Transactional
    public String insertOrderAndLineItem(Order order){
        String result = orderRepo.insertOrder(order);
        lineItemRepo.insertIntoLineItems(order);
        return result;
    }
}
