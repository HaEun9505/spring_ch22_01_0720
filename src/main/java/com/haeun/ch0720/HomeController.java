package com.haeun.ch0720;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
//스프링 컨테이너 설정 파일
/**
 * Handles requests for the application home page.
 */

import com.haeun.ch0720.dao.TicketDao;
import com.haeun.ch0720.dto.TicketDto;
@Controller
public class HomeController {
	
	private TicketDao dao;
	
	
	@Autowired
	public void setDao(TicketDao dao) {
		this.dao = dao;
	}

	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		
		return "home";
	}
	
	@RequestMapping(value = "/buy_ticket")
	public String buy_ticket() {
		
		return "buy_ticket";
	}
	
	@RequestMapping(value = "/buy_ticket_card")
	//model에 객체를 실어서 전달
	public String buy_ticket_card(Model model, TicketDto dto) {
		
		dao.buyTicket(dto);
		
		model.addAttribute("ticketInfo", dto);
		
		return "buy_ticket_end";
	}
}
