package com.devsuperior.dsmeta.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.repositories.SaleRepository;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

@Service
public class SmsService {

	@Value("${twilio.sid}")
	private String twilioSid;

	@Value("${twilio.key}")
	private String twilioKey;

	@Value("${twilio.phone.from}")
	private String twilioPhoneFrom;

	@Value("${twilio.phone.to}")
	private String twilioPhoneTo;

	@Autowired
	private SaleRepository saleRepository;

	private String msg;

	public void sendSms(Long saleId) {
		Sale sale = getIdSales(saleId);
		msg = getSellerName(sale);

		Twilio.init(twilioSid, twilioKey);

		PhoneNumber to = new PhoneNumber(twilioPhoneTo);
		PhoneNumber from = new PhoneNumber(twilioPhoneFrom);

		Message message = Message.creator(to, from, msg).create();
		System.out.println(message.getSid());
	}

	private String getSellerName(Sale sale) {
		msg = "Vendedor " + sale.getSellerName() + " foi destaque em "
				+  getDestaqueDoMes(sale)  +  " com um total de R$ " + getAmount(sale);
		return msg;
	}

	private String getDestaqueDoMes(Sale sale) {
		String date = sale.getDate().getMonthValue() + "/" + sale.getDate().getYear();
		return date;
	}
	
	private String getAmount(Sale sale) {
		return String.format("%.2f", sale.getAmount());
	}

	private Sale getIdSales(Long saleId) {
		Sale sale = saleRepository.findById(saleId).get();
		return sale;
	}
}