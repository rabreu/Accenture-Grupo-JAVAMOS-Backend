package com.accenture.javamos.service;

import com.accenture.javamos.configuration.PagarmeConfig;
import com.accenture.javamos.controller.exception.UnauthorizedException;
import com.accenture.javamos.converter.ticket.TicketConverter;
import com.accenture.javamos.dto.PaymentDTO;
import com.accenture.javamos.dto.TicketDTO;
import com.accenture.javamos.repository.TicketRepository;
import java.util.ArrayList;
import java.util.Collection;
import me.pagar.model.Billing;
import me.pagar.model.Customer;
import me.pagar.model.Item;
import me.pagar.model.PagarMe;
import me.pagar.model.PagarMeException;
import me.pagar.model.Transaction;
import me.pagar.model.Transaction.PaymentMethod;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PagarmeService {

  @Autowired
  private ModelMapper mapper;

  @Autowired
  TicketRepository ticketRepository;

  @Autowired
  TicketConverter ticketConverter;

  @Autowired
  public PagarmeService(@Autowired PagarmeConfig pagarmeConfig) {
    String clientId = pagarmeConfig.getClientId();
    PagarMe.init(clientId);
  }

  public PaymentDTO confirm(PaymentDTO payment)
    throws PagarMeException, IllegalArgumentException, UnauthorizedException {
    Transaction transaction = new Transaction();
    Customer c = mapper.map(payment.getCustomer(), Customer.class);
    Billing b = mapper.map(payment.getBilling(), Billing.class);

    transaction.setCustomer(c);
    transaction.setBilling(b);
    transaction.setCardHolderName(payment.getCardHolderName());
    transaction.setCardExpirationDate(payment.getCardExpirationDate());
    transaction.setCardNumber(payment.getCardNumber());
    transaction.setCardCvv(payment.getCardCvv());
    transaction.setPaymentMethod(PaymentMethod.CREDIT_CARD);
    Integer amount = (int) (
      payment.getTicket().getFlightOrder().getFlight().getTotalPrice() * 100
    );
    transaction.setAmount(amount);
    Collection<Item> items = new ArrayList<>();
    Item item = new Item();
    item.setId(payment.getTicket().getId());
    item.setQuantity(1);
    item.setTangible(Boolean.TRUE);
    item.setTitle(payment.getTicket().getFlightOrder().getFlight().getId());
    item.setUnitPrice(amount);
    transaction.setItems(items);

    transaction.save();

    TicketDTO ticketDTO = payment.getTicket();
    ticketDTO.setStatus("completed");

    ticketRepository.save(ticketConverter.toTicket().convert(ticketDTO));

    return payment;
  }
}
